import java.util.Date

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import com.joyrec.util.log.impl.slf4j.ClassSlf4j
import com.timzaak.di.{ActionDI, DI}
import com.timzaak.schema.GraphQLContext
import org.json4s._
import pdi.jwt.{Jwt, JwtAlgorithm}
import sangria.execution._
import sangria.parser.QueryParser
import sangria.renderer.SchemaRenderer
import ws.very.util.json.JsonHelperWithDoubleMode

import scala.util.{Failure, Success}

object Server extends App with JsonHelperWithDoubleMode with DI with ClassSlf4j {

  val rejectComplexQueries = QueryReducer.rejectComplexQueries[Any](100, (c, ctx) ⇒
    new IllegalArgumentException(s"Too complex query"))

  val route: Route =
    (post & path("graphql") & optionalHeaderValueByName("auth")) { auth =>

      implicit val serialization = native.Serialization
      import JsonExtractors._
      import de.heikoseeberger.akkahttpjson4s.Json4sSupport._
      import sangria.marshalling.json4s.native._

      entity(as[JValue]) { requestJson ⇒
        val JString(query: String) = requestJson \ "query"

        val strOption(operation: Option[String]) = requestJson \ "operationName"

        val userId = auth.flatMap { t =>
          Jwt.decode(t, jwtSecretKey, Seq(JwtAlgorithm.HS256)).map(_.toLong).toOption
        }

        val variables = requestJson \ "variables" match {
          case JNull | JNothing => JObject()
          case other => other
        }
        val time = new Date().getTime
        QueryParser.parse(query) match {
          case Success(queryAst) ⇒
            complete(Executor.execute(
              graphQLSchema,
              queryAst,
              GraphQLContext(userId, this: ActionDI),
              variables = variables,
              queryReducers = Nil,
              operationName = operation,
              exceptionHandler = {
                case (_, e) =>
                  HandledException(e.getMessage)
              }
            ).map { result =>
              OK → result
            }.recover {
              case error: QueryAnalysisError ⇒
                BadRequest → error.resolveError
              case error: ErrorWithResolver ⇒
                InternalServerError → error.resolveError
            }.map { result =>
              debug("cost:" + (new Date().getTime - time))
              result
            })
          case Failure(error) ⇒
            complete(BadRequest, "error" -> error.getMessage)
        }
      }
    } ~
      get {
        path("graphiql.html") {
          getFromResource("graphiql.html")
        } ~
          path("graph_schema") {
            complete(SchemaRenderer.renderSchema(graphQLSchema))
          }
      }


  val port = sys.props.get("http.port").fold(8080)(_.toInt)
  val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", port)
  bindingFuture.onComplete {
    case Success(binding) ⇒
      debug(s"Server is listening on localhost:$port")
    case Failure(e) ⇒
      error(s"Binding failed with ${e.getMessage}")
      system.terminate()
  }
  scala.sys.addShutdownHook {
    system.terminate()
    error(s"closed at ${new Date()}")
  }

}

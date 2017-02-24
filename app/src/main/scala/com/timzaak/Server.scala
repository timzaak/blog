package com.timzaak

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import com.timzaak.repo.CharacterRepo
import com.timzaak.schema.SchemaDefinition
import org.json4s._
import sangria.execution.deferred.DeferredResolver
import sangria.execution.{ErrorWithResolver, Executor, QueryAnalysisError, QueryReducer}
import sangria.parser.QueryParser
import ws.very.util.json.JsonHelperWithDoubleMode

import scala.util.{Failure, Success}

object Server extends App with JsonHelperWithDoubleMode {
  implicit val system = ActorSystem("server")
  implicit val materializer = ActorMaterializer()

  import system.dispatcher

  val rejectComplexQueries = QueryReducer.rejectComplexQueries[Any](100, (c, ctx) ⇒
    new IllegalArgumentException(s"Too complex query"))

  val route: Route =
    (post & path("graphql")) {
      implicit val serialization = native.Serialization
      import JsonExtractors._
      import de.heikoseeberger.akkahttpjson4s.Json4sSupport._
      import sangria.marshalling.json4s.native._

      entity(as[JValue]) { requestJson ⇒
        val JString(query) = requestJson \ "query"

        val strOption(operation) = requestJson \ "operationName"

        val variables = requestJson \ "variables" match {
          case JNull => JObject()
          case other =>
            other
        }

        QueryParser.parse(query) match {
          case Success(queryAst) ⇒
            complete(Executor.execute(SchemaDefinition.StarWarsSchema, queryAst, new CharacterRepo,
              variables = variables,
              operationName = operation,
              deferredResolver = DeferredResolver.fetchers(SchemaDefinition.characters),
              maxQueryDepth = Some(5),
              queryReducers = rejectComplexQueries :: Nil)
              .map(OK → _)
              .recover {
                case error: QueryAnalysisError ⇒ BadRequest → error.resolveError
                case error: ErrorWithResolver ⇒ InternalServerError → error.resolveError
              })
          case Failure(error) ⇒
            complete(BadRequest, "error" -> error.getMessage)
        }
      }
    } ~
      get {
        getFromResource("graphiql.html")
      }

  Http().bindAndHandle(route, "0.0.0.0", sys.props.get("http.port").fold(8080)(_.toInt))
}

import java.util.Date

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import com.joyrec.util.log.impl.slf4j.ClassSlf4j
import com.timzaak.di.{ActionDI, ConfigDI, DI}
import com.timzaak.schema.GraphQLContext
import org.json4s._
import sangria.execution._
import sangria.parser.QueryParser
import sangria.renderer.SchemaRenderer
import very.util.config.WithConf
import very.util.security.JwtAuthDecode
import ws.very.util.json.JsonHelperWithDoubleMode

import scala.util.{Failure, Success}

trait ConfigLoad extends WithConf {}

/*
参看 《scala 程序设计》282：执行顺序
scala类初始化 线性化算法
1.当前实例的具体类型会被放到线性化后的首个元素位置处
2.按照该实例父类型的顺序从右到左的放置节点，针对每个福类型执行线性化算法,
  并将执行结果合并。（我们暂且不对 AnyRef 和 Any 类型进行处理。）
3.按照从左到右的顺序，对类型节点进行检查，如果类型节点在该类型节点右边出现过，
  那么便将该类型移除。
4.在类型线性化层次结构末尾处添加 AnyRef 和 Any 类型。
  如果是对价值类型进行线性化算法，请使用 AnyVal 类型替代 AnyRef 类型。
 */

object Server
    extends App
    with ConfigDI
    with JsonHelperWithDoubleMode
    with DI
    with ClassSlf4j
    with JwtAuthDecode {

  override def secretKey:String = jwtSecretKey

  val rejectComplexQueries = QueryReducer.rejectComplexQueries[Any](
    1000,
    (c, ctx) => new IllegalArgumentException(s"Too complex query")
  )

  val route: Route =
    (post & path("graphql") & optionalHeaderValueByName("auth")) { auth =>
      implicit val serialization = native.Serialization
      import JsonExtractors._
      import de.heikoseeberger.akkahttpjson4s.Json4sSupport._
      import sangria.marshalling.json4s.native._

      entity(as[JValue]) { requestJson =>
        val JString(query: String) = requestJson \ "query"

        val strOption(operation: Option[String]) = requestJson \ "operationName"

        val userId = auth.flatMap { t =>
          jwtDecode(t).map(_.toLong)
        }

        val variables = requestJson \ "variables" match {
          case JNull | JNothing => JObject()
          case other            => other
        }
        val time = new Date().getTime
        QueryParser.parse(query) match {
          case Success(queryAst) =>
            complete(
              Executor
                .execute(
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
                )
                .map { result =>
                  OK -> result
                }
                .recover {
                  case error: QueryAnalysisError =>
                    BadRequest -> error.resolveError
                  case error: ErrorWithResolver =>
                    InternalServerError -> error.resolveError
                }
                .map { result =>
                  debug("cost:" + (new Date().getTime - time))
                  result
                }
            )
          case Failure(error) =>
            complete(BadRequest, "error" -> error.getMessage)
        }
      }
    } ~
      get {
        path("status") {
          complete(OK)
        } ~
        path("graphiql.html") {
          getFromResource("graphiql.html")
        } ~
          path("graph_schema") {
            complete(SchemaRenderer.renderSchema(graphQLSchema))
          }
      }

  val port          = sys.props.get("http.port").fold(9000)(_.toInt)
  val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", port)
  bindingFuture.onComplete {
    case Success(binding) =>
      debug(s"Server is listening on localhost:$port")
    case Failure(e) =>
      error(s"Binding failed with ${e.getMessage}")
      system.terminate()
  }
  scala.sys.addShutdownHook {
    system.terminate()
    info(s"closed at ${new Date()}")
  }

}

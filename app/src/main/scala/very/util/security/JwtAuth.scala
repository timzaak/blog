package very.util.security

import java.time.LocalDateTime

import org.json4s.JsonAST.JString
import pdi.jwt.{ JwtAlgorithm, JwtJson4s }
import ws.very.util.json.JsonHelperWithDoubleMode

trait JwtAuthDecode extends JsonHelperWithDoubleMode {
  protected def secretKey: String

  def jwtDecode(token: String) =
    JwtJson4s
      .decodeJson(token, secretKey, Seq(JwtAlgorithm.HS256))
      .toOption
      .flatMap { v =>
        v \ "u" match {
          case JString(userId) => Some(userId)
          case _               => None
        }
      }
}

trait JwtAuthEncode extends JsonHelperWithDoubleMode {
  protected def secretKey: String

  protected def claim(userId: String) =
    ("u", userId) ~ ("nbf", LocalDateTime.now().toString)

  protected def jwtEncode(userId: String) =
    JwtJson4s.encode(claim(userId), secretKey, JwtAlgorithm.HS256)
}

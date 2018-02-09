package very.util.wechat

import com.timzaak.di.WithTestConf
import org.scalatest.{FreeSpec, Matchers}
import weixin.popular.api.TokenAPI
import ws.very.util.config.ConfigurationImplicit

// sbt "testOnly very.util.wechat.WeChatSpec"
class WeChatSpec extends FreeSpec with Matchers with WithTestConf with ConfigurationImplicit{
  "token" in {
    val token = TokenAPI.token(conf.get[String]("wechat.appID"),conf.get[String]("wechat.appsecret"))
    val access_token = token.getAccess_token
    println(access_token)
    access_token shouldNot be(null)

  }
}

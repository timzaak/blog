package very.util

import com.typesafe.config.{ Config, ConfigFactory }
import org.scalatest._
import very.util.flag.ConfigFeatureFlag

class FeatureFlagsSpec extends FreeSpec with Matchers {
  val config = ConfigFactory.parseString(s"""
       |a=["0","1"]
       |b {
       |  c = "c"
       |}
       |d=true
     """.stripMargin)

  val featureFlags = new ConfigFeatureFlag {
    override protected def getConfig: Config = config
  }

  "Config Feature Flags" - {
    "variable" - {
      "can not get String in Array" in {
        assertThrows[Exception] {
          featureFlags.variable("a.0")
        }
      }
      "can get string" - {
        featureFlags.variable("b.c") shouldBe "c"
      }
    }
    "isEnable" - {
      "can get real boolean" in {
        assert(featureFlags.isEnable("d"))
      }
    }
  }
}

package very.util.alisms

import com.aliyun.mns.client.CloudTopic
import com.aliyun.mns.model.TopicMessage

import scala.util.Success

trait AliMockSmsClient extends AliSmsClient{
  protected def topic: CloudTopic = null

  protected def captchaSignName: S =null

  protected def captchaTemplateCode: S = null

  override def sendSms(signName: S, templateCode: S, params: (S, S)*)(phoneNumbers: S*) = {
    Success("MockMessageId")
  }
}

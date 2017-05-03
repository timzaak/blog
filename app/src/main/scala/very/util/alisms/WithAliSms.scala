package very.util.alisms

import com.aliyun.mns.client.CloudTopic
import com.aliyun.mns.model.{BatchSmsAttributes, MessageAttributes, RawTopicMessage}

import scala.util.Try

trait WithAliSms {

  protected def topic: CloudTopic

  protected def captchaSignName: S

  protected def captchaTemplateCode: S

  protected def sendSms(signName: S, templateCode: S, params: (S, S)*)(phoneNumbers: S*) = {
    val msg = new RawTopicMessage
    msg.setMessageBody("sms-message")
    val messageAttributes = new MessageAttributes
    val batchSmsAttributes = new BatchSmsAttributes
    batchSmsAttributes.setFreeSignName(signName)
    batchSmsAttributes.setTemplateCode(templateCode)
    val smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams()
    params.foreach { case (key, value) => smsReceiverParams.setParam(key, value) }
    phoneNumbers.foreach(batchSmsAttributes.addSmsReceiver(_, smsReceiverParams))
    messageAttributes.setBatchSmsAttributes(batchSmsAttributes)

    Try {
      topic.publishMessage(msg, messageAttributes).getMessageId
    }
  }

  protected def sendCaptchaSms(params: (S, S)*)(phoneNumbers: S*) = {
    sendSms(captchaSignName, captchaTemplateCode, params: _*)(phoneNumbers: _*)
  }


}

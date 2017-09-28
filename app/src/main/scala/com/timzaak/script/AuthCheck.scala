package com.timzaak.script

import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.JsonAST.JValue
import org.pico.hashids.Hashids
import ws.very.util.json.JsonHelperWithDoubleMode

import scalaj.http.Http
import ws.very.util.security.MD5

object AuthCheck extends App with JsonHelperWithDoubleMode{
  val hashIds = Hashids.reference("+WX2bv6ctTAh0G",8)
  val projectId = 10396
  val projectUid = hashIds.encode(projectId)
  val timestamp = System.currentTimeMillis()
  val token = "3b266b3a10e9627de1acaf39801aa410"
  //val userId = 15495
  val userId = 67882
  val userUid = hashIds.encode(userId)

  val organizationId = 9765
  val organizationUid = hashIds.encode(organizationId)
  val email = "zhangshuoyuan@growingio.com"


  def getSign(projectId: Int, optUserId: Option[Int], ts: Long): String = {
    val source = Seq(token, projectId, optUserId.getOrElse(""), ts).filter(_ != "").mkString(":")
    MD5(source)
  }

  def req(url:String, sourceId:Int, userId:Int=10086) = {
    val  timestamp = System.currentTimeMillis()

    Http(s"http://127.0.0.1:9001/internal/$url").headers(
      "X-User-Id"-> userId.toString,
      "X-Timestamp" -> timestamp.toString,
      "Authorization" -> getSign(sourceId, Some(userId),timestamp)
    ).timeout(1000*10, 60*1000*2)
  }



  def memberAdd(email:String) = {
    req(s"projects/${projectUid}/members",projectId).postData(
      toJson((("name"->"张朔源")~("email"->email)~("department"->"部门")~("msg"->"备注")~("role"->"管理员")))
    ).asString
  }

  //println(memberAdd(email))

  def transferCreatorRole = {
    req(s"projects/${projectUid}/users/${userUid}/transfer_creator_role",projectId).put("").asString
  }
  //println(transferCreatorRole)

  def transferOrganizationsCreatorRole  = {
    req(s"organizations/${organizationUid}/users/${userUid}/tranbsfer_creator_role", organizationId).put("").asString
  }
  //println(transferOrganizationsCreatorRole)

  def deleteUserFromProjects = {
    req(s"projects/${projectUid}/users/${userUid}", projectId).method("DELETE").asString
  }
  //println(deleteUserFromProjects)

  def updateOrg(status:Int) = {
    req(s"organizations/${organizationUid}", organizationId).put(
      toJson(("allowResourcePerm"->status))
    ).asString
  }
  //println(updateOrg(0))

  def changeProjectDisableStatus() = {
    req(s"projects/${projectUid}", projectId).put(
      toJson("disabled"->false)
    ).asString

  }


  def changeProjectSetting = {
    req(s"projects/${projectUid}/settings", projectId).put(
      toJson(("backfillImpl"->false)~("exportData"->false))
    ).asString
  }

  println(changeProjectSetting)
}

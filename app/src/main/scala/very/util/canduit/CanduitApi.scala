package very.util.canduit

import ws.very.util.json.JsonHelperWithDoubleMode

trait CanduitApi extends JsonHelperWithDoubleMode{
  def canduitHost: S

  def token:S


  //def req(route,params,data)
}

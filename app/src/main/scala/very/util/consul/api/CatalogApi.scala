package very.util.consul.api

import very.util.consul.entity._

import scalaj.http.HttpResponse

trait CatalogApi extends ConsulApi { api =>

  private[consul] trait Catalog {
    private def req(key: S) = api.req(s"catalog/$key")

    def register(catalogPutRequest: CatalogRegisterRequest) = {
      req("register").putData(catalogPutRequest).opResult
    }

    def deregister(catalogDeregisterRequest: CatalogDeregisterRequest) = {
      req("deregister").putData(catalogDeregisterRequest).opResult
    }

    def listDatacenters = {
      req("datacenters").extract[List[String]]
    }

    def listNodes(
        catalogNodesGetRequest: CatalogNodesGetRequest
    ): Either[HttpResponse[String], List[Node]] = {
      req("nodes").params(catalogNodesGetRequest).extract[List[Node]]
    }

    def listNodes: Either[HttpResponse[String], List[Node]] = {
      listNodes(CatalogNodesGetRequest())
    }

    def listServices(
        catalogServicesGetRequest: CatalogServicesGetRequest
    ): Either[HttpResponse[String], Map[S, List[S]]] = {
      req("services").params(catalogServicesGetRequest).extract[Map[S, List[S]]]
    }

    def listServices: Either[HttpResponse[String], Map[S, List[S]]] = {
      listServices(CatalogServicesGetRequest())
    }
    def listNodesForService(
        service: S
    ): Either[HttpResponse[String], List[CatalogNodes4ServiceResponse]] =
      listNodesForService(CatalogNodesForServiceGetRequest(service))

    def listNodesForService(
        c: CatalogNodesForServiceGetRequest
    ): Either[HttpResponse[String], List[CatalogNodes4ServiceResponse]] = {
      req(s"service/${c.service}").params(c).extract[List[CatalogNodes4ServiceResponse]]
    }

    def listServicesForNode(c: CatalogServicesForNodeGetRequest) = {
      req(s"nodes/${c.node}").params(c).extract[CatalogServices4NodeResponse]
    }
  }

  object Catalog extends Catalog

}

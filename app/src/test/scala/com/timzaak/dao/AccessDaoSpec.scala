package com.timzaak.dao

import com.timzaak.TestSpec
import com.timzaak.dao.generate.EntityGenerate
import com.timzaak.di.{ DaoDI, WithTestConf }
import org.scalatest._
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import very.util.security.Permission

import scala.concurrent.duration._
import scala.concurrent.Await

class AccessDaoSpec
    extends TestSpec
    with DaoDI
    with BeforeAndAfter
    with EntityGenerate
    with GeneratorDrivenPropertyChecks {
  before {
    accessDao._deleteAll
  }

  "accessDao" - {
    "can insert data" in {

      forAll(permissionGen, resourceGen, GroupIdGen) { (permission, resouce, groupId) =>
        val result = accessDao.setGroupPermission(groupId, resouce, permission).flatMap { i =>
          accessDao.getGroupPermission(resouce, groupId)
        }
        result.futureValue shouldBe defined
      }
    }
    "permission could join" in {
      Permission(1)
    }
  }

}

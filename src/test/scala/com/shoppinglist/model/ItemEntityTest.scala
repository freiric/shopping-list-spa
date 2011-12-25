/*
 * Copyright 2008 WorldWide Conferencing, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package com.shoppinglist {
  package model {

    //  import org.junit.Assert._
    import javax.persistence.EntityManagerFactory    
    import org.apache.log4j.Logger
    import org.scalatest.Spec
    import org.scalatest.BeforeAndAfter
    import org.junit.runner.RunWith
    import org.scalatest.junit.JUnitRunner
    import org.h2.tools.Server
    import collection.JavaConversions._

    @RunWith(classOf[JUnitRunner])
    class ItemEntityTest extends Spec with BeforeAndAfter {
      var emf: EntityManagerFactory = _
      var server: Server = null
      before {
        server = Server.createTcpServer("-tcpAllowOthers").start();
      }

      after {
        Model.shutdownEMF
        server.stop()
      }

      describe("Writing to the database") {
        val item = new Item
        val itemDao  : IItemDao = new ItemDao()
        it("preparing a data set") {
          item.setName("Chuck")
        }

        it("writing to the database") {
          itemDao.addAnItem(item)
        }

        it("reading from the database") {
          val retrieved = itemDao.findAllItem()
          println("Found " + retrieved.head.getName())
        }

        it("cleaning  up the database") {
          val em = Model.openEM()

          // clean up
          em.getTransaction().begin()

          em.remove(em.getReference(classOf[Item], item.getId()))

          em.getTransaction().commit()

          em.close()
        }
      }

    }
  }
}


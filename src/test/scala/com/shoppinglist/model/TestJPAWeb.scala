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
    import javax.persistence._
    import org.apache.log4j.Logger
    import org.scalatest.Spec
    import org.scalatest.BeforeAndAfter
    import org.junit.runner.RunWith
    import org.scalatest.junit.JUnitRunner
    import org.h2.tools.Server

    @RunWith(classOf[JUnitRunner])
    class TestJPAWeb extends Spec with BeforeAndAfter {
      var emf: EntityManagerFactory = _
      var server: Server = null
      before {
        server = Server.createTcpServer("-tcpAllowOthers").start();
        def initEMF() = {
          try {
            emf = Persistence.createEntityManagerFactory("jpaweb")
          } catch {
            case e: Exception => {
              def printAndDescend(ex: Throwable): Unit = {
                println(e.getMessage())
                if (ex.getCause() != null) {
                  printAndDescend(ex.getCause())
                }
              }
              printAndDescend(e)
            }
          }
        }
        initEMF()
      }

      after {
        emf.close()
        server.stop()
      }

      describe("Writing to the database") {
        val item = new Item
        it("preparing a data set") {
          var em = emf.createEntityManager()

          item.setName ("Chuck")
        }

        it("writing to the database") {
          var em = emf.createEntityManager()

          val tx = em.getTransaction()

          tx.begin()

          em.persist(item)
          tx.commit()
          em.close()

          // Re-open and query
          em = emf.createEntityManager()

          val retrieved = em.createNamedQuery("findAllItems").getResultList().asInstanceOf[java.util.List[Item]]

          //          assertEquals(1, retrieved.size())
          //          assertEquals(Genre.Mystery, retrieved.get(0).genre)

          println("Found " + retrieved.get(0).getName())

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


package com.shoppinglist.model
import javax.persistence.EntityManager
import collection.JavaConversions._
import scala.collection.mutable.Buffer
import java.util.{ List => JList }

class ItemDao extends IItemDao {
  def findAllItem(): JList[Item] = {
    val em = Model.openEM()
    val tx = em.getTransaction()
    tx.begin()
    val itemList : JList[Item] = em.createNamedQuery("findAllItems").getResultList().asInstanceOf[java.util.List[Item]]
    em.flush()
    tx.commit()
    itemList//    la
  }

  def addAnItem(item: Item) {
    val em = Model.openEM()
    val tx = em.getTransaction()
    tx.begin()
    em.persist(item)
    em.flush()
    tx.commit()

  }
}
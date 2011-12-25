package com.shoppinglist.model
import java.util.{ List => JList }

trait IItemDao {
  def findAllItem(): JList[Item]
  def addAnItem(item: Item)

}
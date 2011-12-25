package com.shoppinglist.model
import org.scalatest.BeforeAndAfter
import org.scalatest.Spec
import javax.measure.Measure
import javax.measure.quantity.Dimensionless
import javax.measure.quantity.Mass

class ItemQuantityTest extends Spec with BeforeAndAfter {
  describe("display a measure without quantity") {
    val item = new Item
    val m: Measure[Dimensionless] = Measure.valueOf(3.6, javax.measure.unit.Unit.ONE)
    println(m.intValue(Dimensionless.UNIT))
    println(Dimensionless.UNIT.toSI())
  }
  describe("display a measure with quantity"){
    val item = new Item
    val m: Measure[Mass] = Measure.valueOf(3, javax.measure.unit.SI.GRAM)
    println(m)
  }
}
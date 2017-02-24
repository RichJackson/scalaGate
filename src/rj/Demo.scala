package rj

/**
  * Created by rich on 24/02/17.
  */
import java.io.File
import GateRunner._
import gate.Gate

object Demo {
  def main (args: Array[String]){
    Gate.setGateHome(new File("/home/rich/GATE_Developer_8.3"))
    Gate.init

    testGateAnonymous
    testGateImplicit
  }

  def testGateImplicit(): Unit ={
    println("The man's name was David".run)
  }

  def testGateAnonymous: Unit ={
    val xml = xmlProcessor("The man's name was David")
    println(xml)
  }
}
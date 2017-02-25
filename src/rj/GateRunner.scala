package rj

import java.io.File
import java.util.concurrent.LinkedBlockingQueue

import gate.{Factory, Gate, Resource}
import gate.creole.{ANNIEConstants, ConditionalSerialAnalyserController, SerialAnalyserController}
import gate.util.persistence.PersistenceManager



/**
  * Created by rich on 24/02/17.
  */

object GateRunner  {
  Gate.getCreoleRegister().registerDirectories(new File(
    Gate.getPluginsHome(), "ANNIE").toURI().toURL())

  var annieController: ConditionalSerialAnalyserController = PersistenceManager.loadObjectFromFile(
    new File(new File(Gate.getPluginsHome, ANNIEConstants.PLUGIN_DIR), ANNIEConstants.DEFAULT_FILE))
    .asInstanceOf[ConditionalSerialAnalyserController]
  annieController.setCorpus(Factory.newCorpus("corpus"))

  val queue = new LinkedBlockingQueue[ConditionalSerialAnalyserController](3)
  queue.put(annieController)

  for (x <- 1 to 2) {
    annieController = Factory.duplicate(annieController).asInstanceOf[ConditionalSerialAnalyserController]
    annieController.setCorpus(Factory.newCorpus("corpus"))
    queue.put(annieController)
  }


  def runApp (s:String): String ={
    val doc = Factory.newDocument(s)
    val controller =queue.take()
    controller.getCorpus().add(doc)
    controller.execute()
    controller.getCorpus.clear()
    val returnStr = doc.toXml()
    Factory.deleteResource(doc)
    queue.put(controller)
    return returnStr
  }




  implicit class runAppImplicit(s: String) {
    def run: String = {
      val doc = Factory.newDocument(s)
      val controller = queue.take()
      controller.getCorpus().add(doc)
      controller.execute()
      controller.getCorpus.clear()
      val returnStr = doc.toXml()
      Factory.deleteResource(doc)
      queue.put(controller)
      return returnStr
    }

  }


  var xmlProcessor = (s:String) => runApp(s)

}

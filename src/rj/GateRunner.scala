package rj

import java.io.File

import gate.{Factory, Gate}
import gate.creole.{ANNIEConstants, ConditionalSerialAnalyserController, SerialAnalyserController}
import gate.util.persistence.PersistenceManager

/**
  * Created by rich on 24/02/17.
  */

object GateRunner {
  Gate.getCreoleRegister().registerDirectories(new File(
     Gate.getPluginsHome(), "ANNIE").toURI().toURL())

  val annieController: ConditionalSerialAnalyserController = PersistenceManager.loadObjectFromFile(
    new File(new File(Gate.getPluginsHome, ANNIEConstants.PLUGIN_DIR), ANNIEConstants.DEFAULT_FILE))
    .asInstanceOf[ConditionalSerialAnalyserController]

  annieController.setCorpus(Factory.newCorpus("corpus"))


  implicit class runAppImplicit (s: String){
    def run: String = {
      var doc = Factory.newDocument(s)
      annieController.getCorpus().add(doc)
      annieController.execute()
      annieController.getCorpus.clear()
      var returnStr = doc.toXml()
      Factory.deleteResource(doc)
      return returnStr
    }

  }

   def runApp (s:String): String ={
    val doc = Factory.newDocument(s)
    annieController.getCorpus().add(doc)
    annieController.execute()
    annieController.getCorpus.clear()
    var returnStr = doc.toXml()
    Factory.deleteResource(doc)
    return returnStr
  }

  var xmlProcessor = (s:String) => runApp(s)

}
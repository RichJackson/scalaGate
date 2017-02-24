import gate.*;
import gate.creole.*;
import gate.persist.PersistenceException;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;

import java.io.File;
import java.io.IOException;

/**
 * Created by rich on 24/02/17.
 */
public class Gatetest {

    public static void main(String[] args) throws GateException, IOException {
        Gate.setGateHome(new File("/home/rich/GATE_Developer_8.3"));
        Gate.init();
        // load the ANNIE plugin
        ConditionalSerialAnalyserController annieController = (ConditionalSerialAnalyserController)
                PersistenceManager.loadObjectFromFile(new File(new File(
                        Gate.getPluginsHome(), ANNIEConstants.PLUGIN_DIR),
                        ANNIEConstants.DEFAULT_FILE));
        // Tell ANNIE’s controller about the corpus you want to run on
        Corpus corpus = Factory.newCorpus("corpus");
        annieController.setCorpus(corpus);
        // Run ANNIE
        Document doc = Factory.newDocument("My Name is David");
        corpus.add(doc);
        annieController.execute();
        System.out.print(doc.toXml());
    }
}

package nz.org.geonet.wfs;


import org.apache.log4j.Logger;
import org.junit.Test;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.util.IOUtils;


/**
 * test send push notifications to UA
 */
public class QuakePushTest extends FunctionalTestCase {
    private static Logger logger = Logger.getLogger(QuakePushTest.class);

    @Override
    protected String getConfigResources() {
        return "mule-push-test-config.xml";
    }

    @Override
    public int getTestTimeoutSecs() {
        return 60 * 60;
    }

    @Override
    protected void doSetUp() throws Exception {

    }

    @Override
    protected void doTearDown() throws Exception {

    }

    @Test
    public void testSendPush() throws Exception {
        MuleClient muleClient = new MuleClient(muleContext);
        String srcData = IOUtils.getResourceAsString("src/test/resources/event/quake.xml",
                getClass());
        //logger.info("srcData " + srcData);
        muleClient.dispatch("vm://sqs-quake.in", srcData, null);
        waitForIt(5);
        //assertTableEquals(expected, "event");
    }

    protected void waitForIt(int secondsToWait) {

        // TODO we - need to wait to give the MULE file spooling a
        // chance to do it's thing - is there a better way?
        long t0, t1;

        t0 = System.currentTimeMillis();

        do {
            t1 = System.currentTimeMillis();
        } while (t1 - t0 < (secondsToWait * 1000));
    }
}

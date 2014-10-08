package lifemonitor.application.lifelab.lifemonitor;

import junit.framework.TestCase;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by cacciatore on 08/10/14.
 *
 * Used to test the application on a test REST url and database.
 *
 * @author Celia Cacciatore
 */
public abstract class AbstractTestCase extends TestCase {

    protected URL RESTurlTest;

    /**
     * Sets up the REST url to test on the test database.
     */
    protected void setUp() {
        // TODO: modify REST url
        String url = "toto"; // To modify REST url, please modify this variable
        try {
            this.RESTurlTest = new URL(url);
        } catch (MalformedURLException e) {
            fail("Test Rest URL may be wrong : " + url);
        }
    }
}

package lifemonitor.application.test.controllers;

import android.test.AndroidTestCase;

/**
 * Used to test the application on a test REST url and database.
 *
 * @author Celia and Jonathan on 08/10/14.
 */
public abstract class AbstractTestCase extends AndroidTestCase {

    protected static final String TEST_REST_URL = "http://glefer.fr:9000/app_dev.php";

    /**
     * Sets up the REST url to test on the test database.
     */
    protected void setUp() {
    }
}

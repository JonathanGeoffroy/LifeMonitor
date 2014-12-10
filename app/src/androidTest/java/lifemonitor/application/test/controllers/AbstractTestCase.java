package lifemonitor.application.test.controllers;

import android.test.AndroidTestCase;

import lifemonitor.application.helper.rest.RESTHelper;

/**
 * Used to test the application on a test REST url and database.
 *
 * @author Celia and Jonathan on 08/10/14.
 */
public abstract class AbstractTestCase extends AndroidTestCase {

    protected static final String TEST_REST_URL = "http://demo9892644.mockable.io";

    /**
     * Sets up the REST url to test on the test database.
     */
    protected void setUp() {
        RESTHelper.setRESTUrl(TEST_REST_URL);
    }
}

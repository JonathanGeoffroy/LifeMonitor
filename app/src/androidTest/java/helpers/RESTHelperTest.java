package helpers;

import java.util.ArrayList;
import java.util.List;

import lifemonitor.application.lifelab.lifemonitor.AbstractTestCase;

/**
 * Created by cacciatore on 22/10/14.
 */
public class RESTHelperTest extends AbstractTestCase{

    public void testJacksonOnEmpty() throws Exception {
        RESTHelper<String> restHelper = new RESTHelper<String>();

        assertEquals(new ArrayList<String>(), restHelper.parseResult("", String.class));
    }
}

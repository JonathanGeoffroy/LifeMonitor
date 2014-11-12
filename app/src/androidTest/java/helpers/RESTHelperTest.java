package helpers;

import java.util.ArrayList;

import helpers.rest.RESTHelper;
import lifemonitor.application.lifelab.lifemonitor.AbstractTestCase;


public class RESTHelperTest extends AbstractTestCase{

    public void testJacksonOnEmpty() throws Exception {
        RESTHelper restHelper ;
        System.out.println("test");
        RESTHelper.setRESTUrl("coucou");

       assertEquals(RESTHelper.RESTUrl,"couco" );

    }
}

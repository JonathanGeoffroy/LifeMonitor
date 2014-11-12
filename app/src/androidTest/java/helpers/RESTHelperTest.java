package helpers;


import helpers.rest.RESTHelper;
import lifemonitor.application.lifelab.lifemonitor.AbstractTestCase;


public class RESTHelperTest extends AbstractTestCase{

    public void testJacksonOnEmpty() throws Exception {
        System.out.println("test");
        RESTHelper.setRESTUrl("coucou");

       assertEquals(RESTHelper.getRESTUrl(),"coucou" );
       //assertEquals(true, false);

    }
}

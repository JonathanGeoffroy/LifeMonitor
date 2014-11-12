package lifemonitor.application.test.helpers;


import lifemonitor.application.helper.rest.RESTHelper;
import lifemonitor.application.test.controllers.AbstractTestCase;


public class RESTHelperTest extends AbstractTestCase{

    public void testJacksonOnEmpty() throws Exception {
        System.out.println("test");
        RESTHelper.setRESTUrl("coucou");

       assertEquals(RESTHelper.getRESTUrl(),"coucou" );
       assertEquals(true, true);

    }
}

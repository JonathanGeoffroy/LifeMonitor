package lifemonitor.application.test.helpers;


import lifemonitor.application.helper.rest.RESTHelper;
import lifemonitor.application.test.controllers.AbstractTestCase;


public class RESTHelperTest extends AbstractTestCase{
    /**
     * AweSome Test to Test the tests
     * @throws Exception
     */
    public void testJacksonOnEmpty() throws Exception {
        System.out.println("test");
        RESTHelper.setRESTUrl("coucou");

       assertEquals(RESTHelper.getRESTUrl(),"coucou" );
       assertEquals(true, true);

    }
}

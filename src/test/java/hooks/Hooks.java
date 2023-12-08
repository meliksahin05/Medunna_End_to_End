package hooks;

import io.cucumber.java.Before;
import static baseurls.MedunnaBaseUrl.setUp;

public class Hooks {

    @Before("@api")
    public static void beforeApi(){

        setUp();

    }
}

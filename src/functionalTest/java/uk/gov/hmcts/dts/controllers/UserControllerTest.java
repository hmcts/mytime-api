package uk.gov.hmcts.dts.controllers;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.hmcts.dts.controllers.util.FunctionalTestBase;

@ExtendWith(SpringExtension.class)
public class UserControllerTest extends FunctionalTestBase {

    private static final String PATH = "/User";
}

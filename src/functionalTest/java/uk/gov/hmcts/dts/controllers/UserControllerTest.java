package uk.gov.hmcts.dts.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.hmcts.dts.controllers.util.FunctionalTestBase;
import uk.gov.hmcts.dts.mytime.models.UserModel;

@ExtendWith(SpringExtension.class)
@SuppressWarnings("PMD.TooManyMethods")
class UserControllerTest extends FunctionalTestBase {
    private static final String PATH = "/user";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    private static final Integer ID = 97;
    private static final String FORENAME = "Functional";
    private static final String SURNAME = "Test";
    private static final LocalDateTime DATE_JOINED = LocalDateTime.now();
    private static final Double HOURS = 37.0;
    private static final Integer BONUS_ENTITLEMENT = 0;
    private static final Integer MANAGER_ID = 1;
    private static final UserModel USER_MODEL = new UserModel(
        ID,
        FORENAME,
        SURNAME,
        DATE_JOINED,
        HOURS,
        BONUS_ENTITLEMENT,
        MANAGER_ID
    );
    @BeforeAll
    static void setup() {
        OBJECT_MAPPER.findAndRegisterModules();
    }

    @Test
    void shouldSaveNewUser() throws JsonProcessingException {
        String jsonInput = OBJECT_MAPPER.writeValueAsString(USER_MODEL);
        var response = doPutRequest(PATH + "/save", jsonInput);
        assertThat(response.statusCode())
            .isEqualTo(CREATED.value());

        UserModel savedUser = response.getBody().as(UserModel.class);
        assertThat(savedUser.getForeName()).isEqualTo(FORENAME);

    }
    @Test
    void shouldGetUserByID(){
        var path  = PATH + "/" + 2;
        var response = doGetRequest(path);
        assertThat(response.statusCode())
            .isEqualTo(OK.value());

        UserModel user = response.getBody().as(UserModel.class);
        assertThat(user.getForeName()).isEqualTo(FORENAME);
    }
}

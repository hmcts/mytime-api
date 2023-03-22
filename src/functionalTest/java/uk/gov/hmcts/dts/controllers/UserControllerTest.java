package uk.gov.hmcts.dts.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.hmcts.dts.controllers.util.FunctionalTestBase;
import uk.gov.hmcts.dts.mytime.models.UserModel;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
@SuppressWarnings("PMD.TooManyMethods")
class UserControllerTest extends FunctionalTestBase {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    private static final String PATH = "/user/";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Integer ID = RANDOM.nextInt(1999, 2999);
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

        // create new user
        var response = saveUser();
        assertThat(response.statusCode()).isEqualTo(CREATED.value());

        // check saved user is the same
        var returnedUser = response.getBody().as(UserModel.class);
        assertThat(returnedUser.getForeName()).isEqualTo(FORENAME);

        // tidy up
        response = deleteUser(returnedUser);
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    void shouldGetUserByID() throws JsonProcessingException {

        // save user to db to retrieve
        var response = saveUser();
        assertThat(response.statusCode()).isEqualTo(CREATED.value());

        // get saved user by ID
        UserModel returnedUser = response.getBody().as(UserModel.class);
        var path  = PATH + returnedUser.getId();
        response = doGetRequest(path);
        assertThat(response.statusCode())
            .isEqualTo(OK.value());

        // check returned user object
        assertThat(returnedUser.getForeName()).isEqualTo(USER_MODEL.getForeName());

        // tidy up
        response = deleteUser(returnedUser);
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    void shouldUpdateUser() throws JsonProcessingException {

        // save user to db to update
        var response = saveUser();
        assertThat(response.statusCode()).isEqualTo(CREATED.value());

        // get returned saved user and update property
        UserModel updatedUser = response.getBody().as(UserModel.class);
        updatedUser.setForeName("Updated Forename");
        var path = PATH + "update";
        var jsonObj = OBJECT_MAPPER.writeValueAsString(updatedUser);
        response = doPatchRequest(path, jsonObj);
        var returnedUser = response.getBody().as(UserModel.class);
        assertThat(returnedUser.getForeName()).isEqualTo(updatedUser.getForeName());

        // tidy up
        response = deleteUser(returnedUser);
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    void shouldDeleteUser() throws JsonProcessingException {

        // save user to db to delete
        var response = saveUser();
        var savedUser = response.getBody().as(UserModel.class);
        assertThat(response.statusCode()).isEqualTo(CREATED.value());

        // tidy up
        response = deleteUser(savedUser);
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    private Response saveUser() throws JsonProcessingException {
        var path = PATH + "save";
        return doPutRequest(path,
                            OBJECT_MAPPER.writeValueAsString(USER_MODEL));
    }

    private Response deleteUser(UserModel savedUser) throws JsonProcessingException {
        var path = PATH + "delete";
        return doDeleteRequest(path,
                               OBJECT_MAPPER.writeValueAsString(savedUser));
    }
}

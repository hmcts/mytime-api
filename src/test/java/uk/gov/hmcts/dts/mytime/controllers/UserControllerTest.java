package uk.gov.hmcts.dts.mytime.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.gov.hmcts.dts.mytime.entities.UserEntity;
import uk.gov.hmcts.dts.mytime.models.UserModel;
import uk.gov.hmcts.dts.mytime.services.UserService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@ContextConfiguration(classes = UserController.class)
class UserControllerTest {

    private static final String BASE_URL = "/user";

    private static final UserEntity USER_ENTITY = new UserEntity(
        1,
        "TestSteve",
        "TestNewman",
        LocalDateTime.now(ZoneOffset.UTC),
        12.0,
        2,
        1
    );

    private static final UserModel USER_MODEL = new UserModel(USER_ENTITY);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @MockBean
    private UserService userService;

    @Autowired
    private transient MockMvc mockMvc;

    @BeforeAll
    static void setup() {
        OBJECT_MAPPER.findAndRegisterModules();
    }

    // region get by id

    @Test
    void shouldReturnUserObject() throws Exception {

        when(userService.getUserById(1)).thenReturn(USER_MODEL);

        MvcResult mvcResult = mockMvc.perform(get(BASE_URL + "/1"))
            .andExpect(status().isOk())
            .andReturn();

        UserModel returnedModel = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(),
                                                          UserModel.class);
        assertThat(returnedModel.getId()).isEqualTo(USER_MODEL.getId());
    }

    // endregion
    // region save user
    @Test
    void shouldReturnBadRequest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(BASE_URL + "/save"))
            .andExpect(status().isBadRequest())
            .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(400);
    }

    @Test
    void shouldSaveAndReturnOk() throws Exception {
        String requestJson = OBJECT_MAPPER.writeValueAsString(USER_MODEL);

        MvcResult mvcResult = mockMvc.perform(put(BASE_URL + "/save")
                            .content(requestJson)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful()).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(CREATED.value());
    }

    @Test
    void shouldNotSaveAndReturn400() throws Exception {
        String requestJson = OBJECT_MAPPER.writeValueAsString("");

        MvcResult mvcResult = mockMvc.perform(put(BASE_URL + "/save")
                            .content(requestJson)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError()).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(400);
    }
    // endregion
    // region update user

    @Test
    void shouldUpdateAndReturnOk() throws Exception {
        String requestJson = OBJECT_MAPPER.writeValueAsString(USER_MODEL);

        MvcResult mvcResult = mockMvc.perform(patch(BASE_URL + "/update")
                                                  .content(requestJson)
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .accept(MediaType.APPLICATION_JSON))
                                                        .andExpect(status()
                                                        .is2xxSuccessful()).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(CREATED.value());
    }

    @Test
    void shouldNotUpdateAndReturn400() throws Exception {
        String requestJson = OBJECT_MAPPER.writeValueAsString("");

        MvcResult mvcResult = mockMvc.perform(patch(BASE_URL + "/update")
                                                  .content(requestJson)
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .accept(MediaType.APPLICATION_JSON))
                                                        .andExpect(status()
                                                        .is4xxClientError()).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(400);
    }
    // endregion

    // region delete user
    @Test
    void shouldDeleteAndReturnOk() throws Exception {
        String requestJson = OBJECT_MAPPER.writeValueAsString(USER_MODEL);

        MvcResult mvcResult = mockMvc.perform(delete(BASE_URL + "/delete")
            .content(requestJson)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful()).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
    }

    @Test
    void shouldTryToDeleteAndReturn400() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete(BASE_URL + "/0"))
            .andExpect(status().is4xxClientError())
            .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(405);
    }
    // endregion
}

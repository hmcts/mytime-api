package uk.gov.hmcts.dts.rsecheck.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.gov.hmcts.dts.mytime.controllers.UserController;
import uk.gov.hmcts.dts.mytime.entities.UserEntity;
import uk.gov.hmcts.dts.mytime.models.UserModel;
import uk.gov.hmcts.dts.mytime.services.UserService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = UserController.class)
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
class UserControllerTest {

    private static final String BASE_URL = "/User";

    private static final UserEntity userEnt = new UserEntity(
        1,
        "TestSteve",
        "TestNewman",
        LocalDateTime.now(ZoneOffset.UTC),
        12.0,
        2,
        1
    );

    private static final UserModel userMod = new UserModel(Optional.of(userEnt));
    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private UserService userService;

    @Autowired
    private transient MockMvc mockMvc;

    // region get by id
    @Test
    void getByIdShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(BASE_URL + "/0"))
            .andExpect(status().isBadRequest())
            .andReturn();
    }

    @Test
    void shouldReturnUserObject() throws Exception {

        when(userService.getById(1)).thenReturn(userMod);

        MvcResult mvcResult = mockMvc.perform(get(BASE_URL + "/1"))
            .andExpect(status().isOk())
            .andReturn();

        objectMapper.registerModule(new JavaTimeModule());
        UserModel returnedModel = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserModel.class);
        assertThat(returnedModel.getId()).isEqualTo(userMod.getId());
    }

    // endregion
    // region save user
    @Test
    void shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(BASE_URL + "/saveUser"))
            .andExpect(status().isBadRequest())
            .andReturn();
    }

    @Test
    void shouldSaveAndReturnOk() throws Exception {

        objectMapper.registerModule(new JavaTimeModule());

        String requestJson = objectMapper.writeValueAsString(userMod);

        mockMvc.perform(put(BASE_URL + "/saveUser")
                            .content(requestJson)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldNotSaveAndReturn500() throws Exception {

        objectMapper.registerModule(new JavaTimeModule());

        String requestJson = objectMapper.writeValueAsString(userMod);

        mockMvc.perform(put(BASE_URL + "/saveUser")
                            .content(requestJson)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)).andExpect(status().is5xxServerError());
    }
    // endregion
    // region update user
    // endpoint is using the same methods as save
    // endregion

    // region delete user
    @Test
    void shouldDeleteAndReturnOk() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete(BASE_URL + "/1"))
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    void shouldTryToDeleteAndReturn400() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete(BASE_URL + "/0"))
            .andExpect(status().is4xxClientError())
            .andReturn();
    }
    // endregion
}

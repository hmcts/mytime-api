package uk.gov.hmcts.dts.mytime.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.dts.mytime.entities.UserEntity;
import uk.gov.hmcts.dts.mytime.exceptions.NotFoundException;
import uk.gov.hmcts.dts.mytime.models.UserModel;
import uk.gov.hmcts.dts.mytime.repository.UserRepo;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final Integer EMPLOYEE_ID = 1;

    private static final UserEntity USER_ENTITY = new UserEntity(
        1,
        "TestSteve",
        "TestNewman",
        LocalDateTime.now(ZoneOffset.UTC),
        12.0,
        2,
        1
    );

    private static final UserModel USER_MODEL = new UserModel(Optional.of(USER_ENTITY));

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldGetUserById() {
        when(userRepo.existsById(EMPLOYEE_ID)).thenReturn(Boolean.TRUE);
        when(userRepo.findById(EMPLOYEE_ID)).thenReturn(Optional.of(USER_ENTITY));

        UserModel res = userService.getById(EMPLOYEE_ID);

        assertThat(res.getId()).isEqualTo(EMPLOYEE_ID);
    }

    @Test
    void shouldThrowNotFoundExceptionGetById() {
        when(userRepo.existsById(EMPLOYEE_ID)).thenReturn(Boolean.FALSE);

        assertThatThrownBy(() -> userService.getById(EMPLOYEE_ID))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("No user found");
    }

    @Test
    void shouldSaveUser() {
        when(userRepo.save(USER_ENTITY)).thenReturn(USER_ENTITY);

        UserModel res = userService.saveUser(USER_MODEL);

        assertThat(res.getId()).isEqualTo(EMPLOYEE_ID);
    }

    @Test
    void shouldThrowNotFoundExceptionDelete() {
        when(userRepo.existsById(EMPLOYEE_ID)).thenReturn(Boolean.FALSE);

        assertThatThrownBy(() -> userService.deleteUser(EMPLOYEE_ID))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("No user found to delete");
    }
}

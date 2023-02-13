package uk.gov.hmcts.dts.mytime.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.dts.mytime.exceptions.NotFoundException;
import uk.gov.hmcts.dts.mytime.models.TeamUsers;
import uk.gov.hmcts.dts.mytime.models.UserModel;
import uk.gov.hmcts.dts.mytime.repository.TeamNamesRepository;
import uk.gov.hmcts.dts.mytime.repository.TeamUsersRepository;
import uk.gov.hmcts.dts.mytime.repository.UserRepo;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamUsersServiceTest {
    private static final Integer ID = 11;
    private static final Integer TEAM_ID = 1;
    private static final Integer USER_ID = 2;
    private static final Integer TEAM_ID2 = 3;
    private static final Integer USER_ID2 = 4;
    private static final String TEAM_USERS_MESSAGE = "Team User does not match";
    private static final String EXCEPTION_MESSAGE = "Exception does not match";

    private static final TeamUsers TEAM_USERS = new TeamUsers();
    private static final UserModel USER_MODEL = new UserModel();
    private static final uk.gov.hmcts.dts.mytime.entities.TeamUsers ENTITY =
        new uk.gov.hmcts.dts.mytime.entities.TeamUsers();
    private static final uk.gov.hmcts.dts.mytime.entities.TeamUsers ENTITY2 =
        new uk.gov.hmcts.dts.mytime.entities.TeamUsers();
    private static final uk.gov.hmcts.dts.mytime.entities.TeamNames TEAM_NAMES_ENTITY =
        new uk.gov.hmcts.dts.mytime.entities.TeamNames();
    private static final uk.gov.hmcts.dts.mytime.entities.UserEntity USER_ENTITY =
        new uk.gov.hmcts.dts.mytime.entities.UserEntity();

    private static final String TEAM_MESSAGE = "Team and User with ID '" + ID + "' does not exist";

    @Mock
    private TeamUsersRepository teamUsersRepository;

    @InjectMocks
    private TeamUsersService teamUsersService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private TeamNamesRepository teamNamesRepository;

    @BeforeAll
    static void setUp() {
        USER_MODEL.setId(USER_ID);

        TEAM_USERS.setId(ID);
        TEAM_USERS.setTeamId(TEAM_ID);
        TEAM_USERS.setUserId(USER_ID);

        ENTITY.setId(ID);
        ENTITY.setTeamId(TEAM_ID);
        ENTITY.setUserId(USER_ID);

        ENTITY2.setTeamId(TEAM_ID2);
        ENTITY2.setUserId(USER_ID2);
    }

    @Test
    void shouldCreateTeamUser() {
        when(userRepo.findById(ENTITY.getUserId())).thenReturn(Optional.of(USER_ENTITY));
        when(teamNamesRepository.findById(TEAM_ID)).thenReturn(Optional.of(TEAM_NAMES_ENTITY));
        when(teamUsersRepository.save(ENTITY)).thenReturn(ENTITY);

        TeamUsers result = teamUsersService.createTeamUser(TEAM_USERS);
        assertThat(result.getTeamId())
            .as(TEAM_USERS_MESSAGE)
            .isEqualTo(TEAM_ID);
    }

    @Test
    void shouldThrowNotFoundExceptionTeamNotExists() {
        when(userRepo.findById(ENTITY.getUserId())).thenReturn(Optional.of(USER_ENTITY));
        when(teamNamesRepository.findById(TEAM_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> teamUsersService.createTeamUser(TEAM_USERS))
            .as(EXCEPTION_MESSAGE)
            .isInstanceOf(NotFoundException.class)
            .hasMessage("Team with ID '" + ENTITY.getTeamId() + "' does not exist");

        verifyNoMoreInteractions(teamUsersRepository);
    }

    @Test
    void shouldThrowNotFoundExceptionCreateTeamNames() {
        when(userRepo.findById(ENTITY.getUserId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> teamUsersService.createTeamUser(TEAM_USERS))
            .as(EXCEPTION_MESSAGE)
            .isInstanceOf(NotFoundException.class)
            .hasMessage("User with ID '" + ENTITY.getUserId() + "' does not exist");

        verifyNoMoreInteractions(teamUsersRepository);
    }

    @Test
    void shouldGetTeamUserById() {
        when(teamUsersRepository.findById(ID)).thenReturn(Optional.of(ENTITY));

        assertThat(teamUsersService.getTeamUserById(ID))
            .as(TEAM_USERS_MESSAGE)
            .isNotNull()
            .extracting(TeamUsers::getId)
            .isEqualTo(TEAM_USERS.getId());
    }

    @Test
    void shouldDeleteTeamName() {
        when(teamUsersRepository.findById(ID)).thenReturn(Optional.of(ENTITY));

        assertThatCode(() -> teamUsersService.deleteTeamUserById(ID))
            .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionDeleteTeamName() {
        when(teamUsersRepository.findById(ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> teamUsersService.deleteTeamUserById(ID))
            .as(EXCEPTION_MESSAGE)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(TEAM_MESSAGE);
    }

    @Test
    void shouldThrowExceptionIfGetTeamUserNotFound() {
        when(teamUsersRepository.findById(ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> teamUsersService.getTeamUserById(ID))
            .as(EXCEPTION_MESSAGE)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(TEAM_MESSAGE);
    }

    @Test
    void shouldGetAllTeamUsers() {
        when(teamUsersRepository.findAll()).thenReturn(List.of(ENTITY, ENTITY2));

        assertThat(teamUsersService.getAllTeamUsers())
            .as(TEAM_USERS_MESSAGE)
            .hasSize(2);
    }

    @Test
    void shouldGetNoTeamUsers() {
        when(teamUsersRepository.findAll()).thenReturn(emptyList());

        assertThat(teamUsersService.getAllTeamUsers())
            .as(TEAM_USERS_MESSAGE)
            .isEmpty();
    }
}

package uk.gov.hmcts.dts.mytime.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.dts.mytime.exceptions.DuplicatedItemException;
import uk.gov.hmcts.dts.mytime.exceptions.NotFoundException;
import uk.gov.hmcts.dts.mytime.models.TeamNames;
import uk.gov.hmcts.dts.mytime.repository.TeamNamesRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamNamesServiceTest {
    private static final Integer ID = 1;
    private static final Integer ID2 = 2;
    private static final String TEAM_NAMES_MESSAGE = "Team Name does not match";
    private static final String TEAM_NAME = "Team Name 1";
    private static final String TEAM_NAME2 = "Team Name 2";
    private static final String EXCEPTION_MESSAGE = "Exception does not match";

    private static final TeamNames TEAM_NAMES = new TeamNames();
    private static final uk.gov.hmcts.dts.mytime.entities.TeamNames ENTITY =
        new uk.gov.hmcts.dts.mytime.entities.TeamNames();
    private static final uk.gov.hmcts.dts.mytime.entities.TeamNames ENTITY2 =
        new uk.gov.hmcts.dts.mytime.entities.TeamNames();

    @Mock
    private TeamNamesRepository teamNamesRepository;

    @InjectMocks
    private TeamNamesService teamNamesService;

    @BeforeAll
    static void setUp() {
        TEAM_NAMES.setTeamName(TEAM_NAME);
        TEAM_NAMES.setId(ID);
        TEAM_NAMES.setParentTeamId(ID2);

        ENTITY.setTeamName(TEAM_NAME);
        ENTITY.setId(ID);
        ENTITY.setParentTeamId(ID2);

        ENTITY2.setTeamName(TEAM_NAME2);
        ENTITY2.setId(ID2);
        ENTITY2.setParentTeamId(null);
    }

    @Test
    void shouldCreateTeamNames() {
        when(teamNamesRepository.save(ENTITY)).thenReturn(ENTITY);
        when(teamNamesRepository.findByTeamName(TEAM_NAME)).thenReturn(emptyList());

        TeamNames result = teamNamesService.createTeam(TEAM_NAMES);
        assertThat(result.getTeamName())
            .as(TEAM_NAMES_MESSAGE)
            .isEqualTo(TEAM_NAME);
    }

    @Test
    void shouldThrowExceptionCreateTeamNames() {
        when(teamNamesRepository.findByTeamName(TEAM_NAME)).thenReturn(List.of(ENTITY));

        assertThatThrownBy(() -> teamNamesService.createTeam(TEAM_NAMES))
            .as(EXCEPTION_MESSAGE)
            .isInstanceOf(DuplicatedItemException.class)
            .hasMessage("Team Name already exists");

        verifyNoMoreInteractions(teamNamesRepository);
    }

    @Test
    void shouldGetTeamNameById() {
        when(teamNamesRepository.findById(ID)).thenReturn(Optional.of(ENTITY));

        assertThat(teamNamesService.getTeamNameById(ID))
            .as(TEAM_NAMES_MESSAGE)
            .isNotNull()
            .extracting(TeamNames::getTeamName)
            .isEqualTo(TEAM_NAME);
    }

    @Test
    void shouldGetParentTeamNameById() {
        when(teamNamesRepository.findByParentTeamId(ENTITY.getId())).thenReturn(Optional.of(ENTITY2));

        assertThat(teamNamesService.getParentTeamNameById(ENTITY.getId()))
            .as(TEAM_NAMES_MESSAGE)
            .isNotNull()
            .extracting(TeamNames::getTeamName)
            .isEqualTo(TEAM_NAME2);
    }

    @Test
    void shouldDeleteTeamName() {
        when(teamNamesRepository.findById(ID)).thenReturn(Optional.of(ENTITY));

        assertThatCode(() -> teamNamesService.deleteTeamById(ID))
            .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionIfGetLeaveRequestNotFound() {
        when(teamNamesRepository.findById(ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> teamNamesService.getTeamNameById(ID))
            .as(EXCEPTION_MESSAGE)
            .isInstanceOf(NotFoundException.class)
            .hasMessage("Team with ID '1' does not exist");
    }

    @Test
    void shouldGetAllTeamNames() {
        when(teamNamesRepository.findAll()).thenReturn(List.of(ENTITY, ENTITY2));

        assertThat(teamNamesService.getAllTeamNames())
            .as(TEAM_NAMES_MESSAGE)
            .hasSize(2);
    }

    @Test
    void shouldGetNoTeamNames() {
        when(teamNamesRepository.findAll()).thenReturn(emptyList());

        assertThat(teamNamesService.getAllTeamNames())
            .as(TEAM_NAMES_MESSAGE)
            .isEmpty();
    }
}

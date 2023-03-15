package uk.gov.hmcts.dts.mytime.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.dts.mytime.exceptions.DuplicatedItemException;
import uk.gov.hmcts.dts.mytime.exceptions.NotFoundException;
import uk.gov.hmcts.dts.mytime.models.TeamNames;
import uk.gov.hmcts.dts.mytime.repository.TeamNamesRepository;

import java.util.List;

@Service
public class TeamNamesService {
    @Autowired
    private TeamNamesRepository teamNamesRepository;
    private static final String TEAM_MESSAGE = "Team with ID '%s' does not exist";

    @Transactional
    public TeamNames createTeam(TeamNames teamNames) {
        uk.gov.hmcts.dts.mytime.entities.TeamNames newTeamNamesEntity =
            new uk.gov.hmcts.dts.mytime.entities.TeamNames(teamNames.getTeamName(), teamNames.getParentTeamId());

        if (teamNamesRepository.findByTeamName(teamNames.getTeamName()).stream()
            .anyMatch(r -> r.equals(newTeamNamesEntity))) {
            throw new DuplicatedItemException("Team Name already exists");
        }

        return new TeamNames(teamNamesRepository.save(newTeamNamesEntity));
    }

    @Transactional
    public TeamNames updateTeam(TeamNames teamNames) {
        // Check if team being updated actually exists
        if (teamNamesRepository.findById(teamNames.getId()).stream()
            .noneMatch(r -> r.getId().equals(teamNames.getId()))) {
            throw new NotFoundException(String.format(TEAM_MESSAGE, teamNames.getId()));
        }

        uk.gov.hmcts.dts.mytime.entities.TeamNames newTeamNamesEntity =
            new uk.gov.hmcts.dts.mytime.entities.TeamNames(teamNames.getId(), teamNames.getParentTeamId(),
                                                           teamNames.getTeamName());

        return new TeamNames(teamNamesRepository.save(newTeamNamesEntity));
    }

    public TeamNames getTeamNameById(Integer id) {
        return teamNamesRepository.findById(id)
            .map(TeamNames::new)
            .orElseThrow(() -> new NotFoundException(String.format(TEAM_MESSAGE, id)));
    }

    public TeamNames getParentTeamNameById(Integer id) {
        TeamNames tn = teamNamesRepository.findById(id)
            .map(TeamNames::new)
            .orElseThrow(() -> new NotFoundException(String.format(TEAM_MESSAGE, id)));

        if (tn.getParentTeamId() == null) {
            throw new NotFoundException(
                String.format("'%s' does not have a parent team.", tn.getTeamName()));
        }

        return teamNamesRepository.findById(tn.getParentTeamId())
            .map(TeamNames::new)
            .orElseThrow(() -> new NotFoundException(
                String.format("Parent Team with ID '%s' does not exist", tn.getParentTeamId())));
    }

    @Transactional
    public void deleteTeamById(Integer id) {
        teamNamesRepository.findById(id)
            .ifPresentOrElse(
                o -> teamNamesRepository.deleteById(id),
                () -> {
                    throw new NotFoundException(String.format(TEAM_MESSAGE, id));
                });
    }

    public List<TeamNames> getAllTeamNames() {
        return teamNamesRepository.findAll()
            .stream()
            .map(TeamNames::new)
            .toList();
    }
}

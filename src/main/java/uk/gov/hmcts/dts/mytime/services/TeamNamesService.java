package uk.gov.hmcts.dts.mytime.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.dts.mytime.exceptions.DuplicatedItemException;
import uk.gov.hmcts.dts.mytime.exceptions.NotFoundException;
import uk.gov.hmcts.dts.mytime.models.TeamNames;
import uk.gov.hmcts.dts.mytime.repository.TeamNamesRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamNamesService {
    @Autowired
    private TeamNamesRepository teamNamesRepository;

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

    public TeamNames getTeamNameById(Integer id) {
        return teamNamesRepository.findById(id)
            .map(TeamNames::new)
            .orElseThrow(() -> new NotFoundException(String.format("Team with ID '%s' does not exist", id)));
    }

    public TeamNames getParentTeamNameById(Integer id) {
        return teamNamesRepository.findByParentTeamId(id)
            .map(TeamNames::new)
            .orElseThrow(() -> new NotFoundException(
                String.format("Parent Team with ID '%s' does not exist", id)));
    }

    public void deleteTeamById(Integer id) {
        teamNamesRepository.findById(id)
            .ifPresentOrElse(
                o -> teamNamesRepository.deleteById(id),
                () -> {
                    throw new NotFoundException(String.format("Team with ID '%s' does not exist", id));
                });
    }

    public List<TeamNames> getAllTeamNames() {
        return teamNamesRepository.findAll()
            .stream()
            .map(TeamNames::new)
            .collect(Collectors.toList());
    }
}

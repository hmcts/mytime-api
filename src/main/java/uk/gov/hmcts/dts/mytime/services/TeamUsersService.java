package uk.gov.hmcts.dts.mytime.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.dts.mytime.exceptions.DuplicatedItemException;
import uk.gov.hmcts.dts.mytime.exceptions.NotFoundException;
import uk.gov.hmcts.dts.mytime.models.TeamUsers;
import uk.gov.hmcts.dts.mytime.repository.TeamNamesRepository;
import uk.gov.hmcts.dts.mytime.repository.TeamUsersRepository;
import uk.gov.hmcts.dts.mytime.repository.UserRepo;

import java.util.List;

@Service
public class TeamUsersService {
    @Autowired
    private TeamUsersRepository teamUsersRepository;
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private TeamNamesRepository teamNamesRepository;

    @Transactional
    public TeamUsers createTeamUser(TeamUsers teamUser) {
        // Check if user exists
        if (userRepository.findById(teamUser.getUserId()).stream()
            .noneMatch(r -> r.getId().equals(teamUser.getUserId()))) {
            throw new NotFoundException(String.format("User with ID '%s' does not exist", teamUser.getUserId()));
        }

        // Check if team exists
        if (teamNamesRepository.findById(teamUser.getTeamId()).stream()
            .noneMatch(r -> r.getId().equals(teamUser.getTeamId()))) {
            throw new NotFoundException(String.format("Team with ID '%s' does not exist", teamUser.getTeamId()));
        }

        uk.gov.hmcts.dts.mytime.entities.TeamUsers newTeamUsersEntity = new uk.gov.hmcts.dts.mytime.entities.TeamUsers(
            teamUser.getTeamId(), teamUser.getUserId());

        // Check if user already member of team
        if (teamUsersRepository.findByTeamIdAndUserId(teamUser.getTeamId(), teamUser.getUserId()).stream()
                .anyMatch(r -> r.equals(newTeamUsersEntity))) {
            throw new DuplicatedItemException("User is already a member of this team.");
        }

        return new TeamUsers(teamUsersRepository.save(newTeamUsersEntity));
    }

    public TeamUsers getTeamUserById(Integer id) {
        return teamUsersRepository.findById(id)
                .map(TeamUsers::new)
                .orElseThrow(
                        () -> new NotFoundException(String.format(
                                "Team and User with ID '%s' does not exist", id)));
    }

    public void deleteTeamUserById(Integer id) {
        teamUsersRepository.findById(id)
                .ifPresentOrElse(
                        o -> teamUsersRepository.deleteById(id),
                        () -> {
                            throw new NotFoundException(String.format(
                                    "Team and User with ID '%s' does not exist",
                                    id));
                        });
    }

    public List<TeamUsers> getAllTeamUsers() {
        return teamUsersRepository.findAll()
                .stream()
                .map(TeamUsers::new)
                .toList();
    }
}

package uk.gov.hmcts.dts.mytime.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.dts.mytime.models.TeamUsers;
import uk.gov.hmcts.dts.mytime.services.TeamUsersService;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/team-users")
@Validated
public class TeamUsersController {
    @Autowired
    private TeamUsersService teamUsersService;

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamUsers> createTeamUsers(@Valid @RequestBody TeamUsers teamUsers) {
        return created(URI.create(StringUtils.EMPTY))
            .body(teamUsersService.createTeamUser(teamUsers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamUsers> getTeamUsers(
        @Valid @Min(value = 1, message = "Team user ID must be greater than zero") @PathVariable Integer id
    ) {
        return ok(teamUsersService.getTeamUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteTeamUsers(
        @Valid @Min(value = 1, message = "Team User ID must be greater than zero") @PathVariable Integer id
    ) {
        teamUsersService.deleteTeamUserById(id);
        return ok(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TeamUsers>> getAllTeamUsers() {
        return ok(teamUsersService.getAllTeamUsers());
    }
}

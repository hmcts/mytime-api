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
import uk.gov.hmcts.dts.mytime.models.TeamNames;
import uk.gov.hmcts.dts.mytime.services.TeamNamesService;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/team-names")
@Validated
public class TeamNamesController {
    @Autowired
    private TeamNamesService teamNamesService;

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamNames> createTeamNames(@Valid @RequestBody TeamNames teamNames) {
        return created(URI.create(StringUtils.EMPTY))
            .body(teamNamesService.createTeam(teamNames));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamNames> getTeamName(
        @Valid @Min(value = 1, message = "Team Name ID must be greater than zero") @PathVariable Integer id
    ) {
        return ok(teamNamesService.getTeamNameById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteTeamNames(
        @Valid @Min(value = 1, message = "Leave request ID must be greater than zero") @PathVariable Integer id
    ) {
        teamNamesService.deleteTeamById(id);
        return ok(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TeamNames>> getAllTeamNames() {
        return ok(teamNamesService.getAllTeamNames());
    }

    @GetMapping("/parent/{id}")
    public ResponseEntity<TeamNames> getParentTeamName(
        @Valid @Min(value = 1, message = "Parent Team Name ID must be greater than zero") @PathVariable Integer id
    ) {
        return ok(teamNamesService.getParentTeamNameById(id));
    }
}

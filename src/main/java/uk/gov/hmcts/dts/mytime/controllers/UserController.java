package uk.gov.hmcts.dts.mytime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.dts.mytime.models.UserModel;
import uk.gov.hmcts.dts.mytime.services.UserService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(
    path = "/User",
    produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable int id) {

        if (id == 0)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        UserModel user = userService.getById(id);

        if (user == null)
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ok(user);
    }

}

package uk.gov.hmcts.dts.mytime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.dts.mytime.models.UserModel;
import uk.gov.hmcts.dts.mytime.services.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(
    path = "/User",
    produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @Validated
    public ResponseEntity<UserModel> getUserById(@PathVariable @Valid @Min(value = 1,
        message = "Invalid user ID") int id) {

        return ok(userService.getById(id));
    }

    @PutMapping(path = "/saveUser")
    public HttpStatus saveUser(@RequestBody @Valid UserModel userModel) {

        userService.saveUser(userModel);

        return HttpStatus.CREATED;
    }

    @PatchMapping(path = "/updateUser")
    public HttpStatus updateUser(@RequestBody @Valid UserModel userModel) {

        userService.saveUser(userModel);

        return HttpStatus.OK;
    }

    @DeleteMapping(path = "/delete/{id}")
    @Validated
    public HttpStatus deleteUser(@PathVariable @Valid @Min(value = 1, message = "Invalid user ID") int id) {

        userService.deleteUser(id);

        return HttpStatus.OK;
    }
}

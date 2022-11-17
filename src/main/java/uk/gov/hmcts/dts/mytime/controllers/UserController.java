package uk.gov.hmcts.dts.mytime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/User/{Id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable @Validated int Id ) {
        UserModel user = userService.getById(Id);
        return ok(user);
    }

    @PutMapping(path = "/saveUser")
    public ResponseEntity<UserModel> saveUser(@RequestBody @Validated UserModel userModel){
        //todo is there a clever way to handle errors?
        return ok(userService.saveUser(userModel));
    }

    @PatchMapping(path = "/updateUser")
    public ResponseEntity<UserModel> updateUser(@RequestBody @Validated UserModel userModel){
        //todo is there a clever way to handle errors?
        return ok(userService.saveUser(userModel));
    }

    @DeleteMapping("/deleteUser/")
    public ResponseEntity deleteUser(@PathVariable @Validated int Id) {

        if (userService.deleteUser(Id))
        {
            return ok(null);
        }
        else
        {
            return (ResponseEntity) ResponseEntity.status(HttpStatus.EXPECTATION_FAILED);
        }
    }

}

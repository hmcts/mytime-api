package uk.gov.hmcts.dts.mytime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.dts.mytime.exceptions.UserException;
import uk.gov.hmcts.dts.mytime.helpers.ErrorResponse;
import uk.gov.hmcts.dts.mytime.models.UserModel;
import uk.gov.hmcts.dts.mytime.services.UserService;

import javax.validation.Valid;

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
    public ResponseEntity<UserModel> getUserById(@PathVariable int id) throws UserException {

        if (id == 0) {
            throw new UserException(400, "Please provide a vaild ID");
        }

        UserModel user = userService.getById(id);

        return ok(user);
    }

    @PutMapping(path = "/saveUser")
    public HttpStatus saveUser(@RequestBody @Valid UserModel userModel) throws UserException {

        userService.saveUser(userModel);

        return HttpStatus.CREATED;
    }

    @PatchMapping(path = "/updateUser")
    public HttpStatus updateUser(@RequestBody @Valid UserModel userModel) throws UserException {

        userService.saveUser(userModel);

        return HttpStatus.OK;
    }

    @DeleteMapping(value = "/delete/{id}")
    public HttpStatus deleteUser(@PathVariable int id) throws UserException {

        if (id == 0) {
            throw new UserException(400, "Please provide a vaild ID");
        }

        userService.deleteUser(id);

        return HttpStatus.OK;
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(UserException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
            ex.getHttpResponseCode(), ex.getMessage());
        var code = errorResponse.getErrorCode();
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(errorResponse.getErrorCode()));
    }
}

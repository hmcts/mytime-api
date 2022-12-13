package uk.gov.hmcts.dts.mytime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.support.FeignHttpClientProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{Id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable int Id) throws UserException {

        if (Id == 0) {
            throw new UserException(400, "Please provide a vaild ID");
        }

        UserModel user = userService.getById(Id);

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

    @DeleteMapping("/{Id}")
    public HttpStatus deleteUser(@PathVariable @Validated int Id) throws UserException {

        if (Id == 0) {
            throw new UserException(400, "Please provide a vaild ID");
        }

        userService.deleteUser(Id);

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

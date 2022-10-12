package uk.gov.hmcts.dts.mytime.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class UserController {

    @GetMapping("/User/{ID}")
    public ResponseEntity<String> getUserById(@PathVariable int ID ) {
        return ok("User ID is " + ID);
    }

}

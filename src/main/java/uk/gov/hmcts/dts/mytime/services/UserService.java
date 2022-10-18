package uk.gov.hmcts.dts.mytime.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import uk.gov.hmcts.dts.mytime.models.User;
import uk.gov.hmcts.dts.mytime.repository.UserRepo;
import uk.gov.hmcts.dts.mytime.exceptions.NotFoundException;

@Service
@Slf4j
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo)
    {
        this.userRepo = userRepo;
    }

    public User getById(int Id)
    {
        log.info("Performing query for user by id {}", Id);
        User user = userRepo.findById(Id)
            .orElseThrow(() -> new NotFoundException("User not found in Database: " + Id));
        log.debug("Returned from User table: {}", user.toString());
        return user;
    }
}

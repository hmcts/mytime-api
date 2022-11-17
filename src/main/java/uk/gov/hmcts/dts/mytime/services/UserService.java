package uk.gov.hmcts.dts.mytime.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import uk.gov.hmcts.dts.mytime.entities.UserEntity;
import uk.gov.hmcts.dts.mytime.models.UserModel;
import uk.gov.hmcts.dts.mytime.repository.UserRepo;
import uk.gov.hmcts.dts.mytime.exceptions.NotFoundException;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepo userRepo;
    public UserService(UserRepo userRepo)
    {
        this.userRepo = userRepo;
    }

    public UserModel getById(int Id)
    {
        log.info("Performing query for user by id {}", Id);

        UserModel user = new UserModel(Optional.ofNullable(userRepo.findById(Id)
            .orElseThrow(() -> new NotFoundException("User not found in Database: " + Id))));

        log.debug("Returned from User table: {}", user.toString());
        return user;
    }

    public UserModel saveUser(UserModel userModel)
    {
        log.info("Updating user {}", userModel.toString());

        final UserEntity userEntity = new UserEntity(userModel);

        try{
            userRepo.save(userEntity);
            log.info("User save successful");
        }
        catch(Exception e){
            log.info("Error saving {}", e.getMessage());
        }

        //todo not sure how this would normally be handled
        return new UserModel(userRepo.findById(userEntity.getId()));

    }

    public boolean deleteUser(int Id)
    {
        log.info("deleting user by id {}", Id);
        //todo what would you normally do here
        try{
            userRepo.deleteById(Id);
            return true;
        }
        catch(Exception e)
        {
            log.info("Error deleting User ID {}. Error {}.", Id, e.getMessage());
            return false;
        }
    }
}

package uk.gov.hmcts.dts.mytime.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.dts.mytime.entities.UserEntity;
import uk.gov.hmcts.dts.mytime.exceptions.UserException;
import uk.gov.hmcts.dts.mytime.models.UserModel;
import uk.gov.hmcts.dts.mytime.repository.UserRepo;

@Service
@Slf4j
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserModel getById(int id) throws UserException {

        log.info("Performing query for user by id {}", id);
        UserModel user;

        try {
            user = new UserModel(userRepo.findById(id));
            log.debug("Returned from User table: {}", user);
        } catch (Exception e) {
            throw new UserException(500, "Has been an error retrieving the user");
        }

        return user;
    }

    public void saveUser(UserModel userModel) throws UserException {
        log.info("Updating user {}", userModel.toString());

        final UserEntity userEntity = new UserEntity(userModel);

        try {
            userRepo.save(userEntity);
            log.info("User save successful");
        } catch (Exception e) {
            log.info("Error saving {}", e.getMessage());
            throw new UserException(500, "Could not save the user");
        }
    }

    public void deleteUser(int id) throws UserException {

        log.info("deleting user by id {}", id);

        try {
            userRepo.deleteById(id);
        } catch (Exception e) {
            log.info("Error deleting User ID {}. Error {}.", id, e.getMessage());
            throw new UserException(500, "Error deleting the user");
        }
    }
}

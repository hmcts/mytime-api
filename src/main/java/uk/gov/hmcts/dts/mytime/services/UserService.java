package uk.gov.hmcts.dts.mytime.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.dts.mytime.entities.UserEntity;
import uk.gov.hmcts.dts.mytime.models.UserModel;
import uk.gov.hmcts.dts.mytime.repository.UserRepo;

@Service
@Slf4j
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserModel getById(int id) {

        return new UserModel(userRepo.findById(id));
    }

    public void saveUser(UserModel userModel) {
        log.info("Updating user {}", userModel.toString());

        final UserEntity userEntity = new UserEntity(userModel);

        try {
            userRepo.save(userEntity);
            log.info("User save successful");
        } catch (Exception e) {
            log.info("Error saving {}", e.getMessage());
        }
    }

    public void deleteUser(int id) {

        userRepo.deleteById(id);
    }
}

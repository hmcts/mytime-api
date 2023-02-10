package uk.gov.hmcts.dts.mytime.services;

import org.springframework.stereotype.Service;
import uk.gov.hmcts.dts.mytime.exceptions.NotFoundException;
import uk.gov.hmcts.dts.mytime.models.UserModel;
import uk.gov.hmcts.dts.mytime.repository.UserRepo;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserModel getUserById(Integer id) {
        return userRepo.findById(id)
                .map(UserModel::new)
                .orElseThrow(() -> new NotFoundException(String.format("User with ID '%s' does not exist", id)));
    }

    public UserModel createUser(UserModel user) {
        uk.gov.hmcts.dts.mytime.entities.UserEntity newUserEntity = new uk.gov.hmcts.dts.mytime.entities.UserEntity(
                user.getForeName(), user.getSurName(),
                user.getStartDate(), user.getContractHours(),
                user.getBonusEntitlement(), user.getManagerId());

        return new UserModel(userRepo.save(newUserEntity));
    }

    public void deleteUserById(Integer id) {
        userRepo.findById(id)
                .ifPresentOrElse(
                        o -> userRepo.deleteById(id),
                        () -> {
                            throw new NotFoundException(String.format("User with ID '%s' does not exist", id));
                        });
    }
}

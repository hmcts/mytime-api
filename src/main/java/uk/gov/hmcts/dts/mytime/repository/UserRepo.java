package uk.gov.hmcts.dts.mytime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.CrudRepository;
import uk.gov.hmcts.dts.mytime.entities.UserEntity;

import java.util.Optional;

public interface UserRepo extends CrudRepository<UserEntity, Integer> {
}

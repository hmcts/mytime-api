package uk.gov.hmcts.dts.mytime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import uk.gov.hmcts.dts.mytime.entities.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findById(Integer id);
}

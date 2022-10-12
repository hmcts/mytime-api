package uk.gov.hmcts.dts.mytime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.hmcts.dts.mytime.entities.User;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer> {

}

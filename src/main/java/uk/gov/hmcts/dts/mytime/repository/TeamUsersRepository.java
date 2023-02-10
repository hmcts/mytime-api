package uk.gov.hmcts.dts.mytime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.dts.mytime.entities.TeamUsers;

import java.util.List;

@Repository
public interface TeamUsersRepository extends JpaRepository<TeamUsers, Integer> {
    List<TeamUsers> findAllByTeamId(Integer teamId);

    List<TeamUsers> findAllByUserId(Integer userId);

    List<TeamUsers> findByTeamIdAndUserId(Integer teamId, Integer userId);
}
package uk.gov.hmcts.dts.mytime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.dts.mytime.entities.TeamNames;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamNamesRepository extends JpaRepository<TeamNames, Integer> {
    List<TeamNames> findByTeamName(String teamName);

    Optional<TeamNames> findByParentTeamId(Integer id);
}
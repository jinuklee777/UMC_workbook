package umc.spring.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.spring.domain.Mission;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
//    @EntityGraph(attributePaths = {"member", "mission"})
    List<MemberMission> findAllByMemberIdAndStatus(Long memberId, MissionStatus status);

    MemberMission findByMission(Mission mission);

    @Modifying
    @Query("UPDATE MemberMission mm SET mm.status = 'COMPLETE' WHERE mm.id = :memberMissionId")
    int updateMemberMissionStatusToSuccess(@Param("memberMissionId") Long memberMissionId);
}

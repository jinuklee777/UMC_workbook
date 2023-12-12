package umc.spring.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long> {

//    @Query("SELECT m FROM Mission m INNER JOIN m.memberMissionList mm WHERE mm IN :memberMissionList")
//    Page<Mission> findAllByMemberMission(@Param("memberMissionList") List<MemberMission> memberMissionList, Pageable pageable);

//    List<Mission> findAllByMemberMissionList_MemberId(Long memberId);
//
    Page<Mission> findAllByIdIn(List<Long> missionIds, PageRequest pageRequest);
//
//    Page<Mission> findAllById(Long id, PageRequest pageRequest);

}

package umc.spring.service.StoreService;

import static umc.spring.converter.MissionConverter.toMissionResponseListPage;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.converter.MissionConverter;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.Store;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.repository.MemberMissionRepository;
import umc.spring.repository.MemberRepository;
import umc.spring.repository.MissionRepository;
import umc.spring.repository.StoreRepository;
import umc.spring.web.dto.store.StoreRequestDTO;
import umc.spring.web.dto.store.StoreResponseDTO.missionResponseListDTO;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final StoreRepository storeRepository;
    private final MissionRepository missionRepository;
    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;

    public Mission addMission(StoreRequestDTO.missionDTO request) {
        Store store = storeRepository.findById(request.getStoreId()).get();
        Member member = memberRepository.findById(request.getMemberId()).get();

        Mission mission = MissionConverter.toMission(member, store, request);
        return missionRepository.save(mission);
    }

    @Transactional
    public void startMission(Long missionId, Long memberId) {
        Member member = memberRepository.findById(memberId).get();

        member.getMemberMissionList().stream()
                .filter(memberMission -> memberMission.getMission().getId().equals(missionId))
                .findFirst()
                .ifPresent(memberMission -> {
                    memberMission.setStatus("시작");
                    memberMissionRepository.save(memberMission);
                });
    }

    public missionResponseListDTO getAllMissionsForMember(Long memberId, Integer page) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new EntityNotFoundException("Member not found with ID: " + memberId));

//        List<MemberMission> allByMember = memberMissionRepository.findAllByMemberAndStatus(member, MissionStatus.CHALLENGING);

//        Page<Mission> missionPage = missionRepository.findAllByMemberMissionListIn(allByMember, PageRequest.of(page, 10));

//        return MissionConverter.toMissionResponseListPage(missionPage);

        List<MemberMission> challengingMissions = memberMissionRepository.findAllByMemberIdAndStatus(memberId, MissionStatus.CHALLENGING);
        List<Long> missionIds = challengingMissions.stream()
                .map(MemberMission::getMission)
                .map(Mission::getId)
                .collect(Collectors.toList());

        Page<Mission> missionPage = missionRepository.findAllByIdIn(missionIds, PageRequest.of(page, 10));
        return toMissionResponseListPage(missionPage);
    }

    @Transactional
    public void updateMissionStatusToSuccess(Long missionId) {
        Mission mission = missionRepository.findById(missionId).get();
        MemberMission memberMission = memberMissionRepository.findByMission(mission);
        memberMissionRepository.updateMemberMissionStatusToSuccess(memberMission.getId());
    }
}

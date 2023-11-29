package umc.spring.service.StoreService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.converter.MissionConverter;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.Store;
import umc.spring.repository.MemberMissionRepository;
import umc.spring.repository.MemberRepository;
import umc.spring.repository.MissionRepository;
import umc.spring.repository.StoreRepository;
import umc.spring.web.dto.store.StoreRequestDTO;

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
}

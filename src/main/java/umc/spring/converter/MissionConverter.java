package umc.spring.converter;

import java.util.ArrayList;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.Store;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.web.dto.store.StoreRequestDTO;
import umc.spring.web.dto.store.StoreResponseDTO;

public class MissionConverter {

    public static Mission toMission(Member member, Store store, StoreRequestDTO.missionDTO request) {
        Mission mission = Mission.builder()
                .missionSpec(request.getMissionSpec())
                .deadline(request.getDeadline())
                .reward(request.getReward())
                .store(store)
                .memberMissionList(new ArrayList<>())
                .build();

        MemberMission memberMission = MemberMission.builder()
                .member(member)
                .mission(mission)
                .build();

        mission.getMemberMissionList().add(memberMission);

        return mission;
    }

    public static StoreResponseDTO.missionResponseDTO toMissionResponse(Mission mission) {
        return StoreResponseDTO.missionResponseDTO.builder()
                .missionSpec(mission.getMissionSpec())
                .reward(mission.getReward())
                .deadline(mission.getDeadline())
                .build();
    }
}

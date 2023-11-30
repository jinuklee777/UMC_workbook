package umc.spring.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.Store;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.web.dto.store.StoreRequestDTO;
import umc.spring.web.dto.store.StoreResponseDTO;
import umc.spring.web.dto.store.StoreResponseDTO.missionResponseDTO;
import umc.spring.web.dto.store.StoreResponseDTO.missionResponseListDTO;

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


    public static StoreResponseDTO.missionResponseListDTO toMissionResponseListPage(Page<Mission> missionPage) {
        List<missionResponseDTO> missionList = missionPage.getContent().stream()
                .map(MissionConverter::toMissionResponse)
                .collect(Collectors.toList());

        missionResponseListDTO build = missionResponseListDTO.builder()
                .missionList(missionList)
                .listSize(missionList.size())
                .totalPage(missionPage.getTotalPages())
                .totalElements(missionPage.getTotalElements())
                .isFirst(missionPage.isFirst())
                .isLast(missionPage.isLast())
                .build();
        return build;
    }
}

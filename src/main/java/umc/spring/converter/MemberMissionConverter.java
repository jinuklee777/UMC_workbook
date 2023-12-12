package umc.spring.converter;

import java.util.List;
import java.util.stream.Collectors;
import umc.spring.domain.Mission;
import umc.spring.domain.mapping.MemberMission;

public class MemberMissionConverter {

    public static List<MemberMission> toMemberMissionList(List<Mission> missionList) {
        return missionList.stream()
                .map(mission ->
                        MemberMission.builder()
                                .mission(mission)
                                .build()).collect(Collectors.toList());
    }
}

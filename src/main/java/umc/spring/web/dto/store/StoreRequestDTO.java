package umc.spring.web.dto.store;

import java.time.LocalDate;
import lombok.Getter;
import umc.spring.validation.annotation.ExistStore;
import umc.spring.validation.annotation.MissionAlreadyInProgress;

public class StoreRequestDTO {

    @Getter
    public static class addDTO {

        String name;

        String address;
    }

    @Getter
    public static class reviewDTO {

        Long memberId;

        @ExistStore
        Long storeId;

        Float score;

        String text;
    }

    @Getter
    public static class missionDTO {

        Long memberId;

        LocalDate deadline;

        String missionSpec;

        int reward;

        @ExistStore
        Long storeId;
    }

    @Getter
    public static class missionStartDTO {
        Long memberId;

        @MissionAlreadyInProgress
        Long missionId;
    }
}

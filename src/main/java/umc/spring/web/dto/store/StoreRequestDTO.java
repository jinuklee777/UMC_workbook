package umc.spring.web.dto.store;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import umc.spring.validation.annotation.ExistStore;
import umc.spring.validation.annotation.MissionAlreadyInProgress;

public class StoreRequestDTO {

    @Getter
    public static class addDTO {

        @NotBlank
        String name;

        @NotBlank
        String address;
    }

    @Getter
    public static class reviewDTO {

        @NotNull
        Long memberId;

        @ExistStore
        Long storeId;

        @NotNull
        Float score;

        @NotBlank
        String title;

        @NotBlank
        String body;
    }

    @Getter
    public static class missionDTO {

        @NotNull
        Long memberId;

        @NotNull
        LocalDate deadline;

        @NotBlank
        String missionSpec;

        int reward;

        @ExistStore
        Long storeId;
    }

    @Getter
    public static class missionStartDTO {
        @NotNull
        Long memberId;

        @MissionAlreadyInProgress
        Long missionId;
    }
}

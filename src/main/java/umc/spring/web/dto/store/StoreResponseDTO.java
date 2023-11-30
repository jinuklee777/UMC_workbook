package umc.spring.web.dto.store;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StoreResponseDTO {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class missionResponseDTO implements Serializable {

        LocalDate deadline;

        String missionSpec;

        int reward;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class missionResponseListDTO{
        List<missionResponseDTO> missionList;

        Integer listSize;

        Integer totalPage;

        Long totalElements;

        Boolean isFirst;

        Boolean isLast;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewPreviewDTO {
        String nickname;

        String title;

        String body;

        LocalDate createdAt;

        Float score;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewPreViewListDTO{
        List<ReviewPreviewDTO> reviewList;

        Integer listSize;

        Integer totalPage;

        Long totalElements;

        Boolean isFirst;

        Boolean isLast;

    }
}

package umc.spring.web.dto.store;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

public class StoreResponseDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class missionResponseDTO implements Serializable {

        LocalDate deadline;

        String missionSpec;

        int reward;
    }

}

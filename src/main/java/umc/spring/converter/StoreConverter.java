package umc.spring.converter;

import java.util.ArrayList;
import umc.spring.domain.Region;
import umc.spring.domain.Store;
import umc.spring.web.dto.store.StoreRequestDTO;

public class StoreConverter {

    public static Store toStore(Region region, StoreRequestDTO.addDTO request) {

        return Store.builder()
                .region(region)
                .address(request.getAddress())
                .reviewList(new ArrayList<>())
                .build();
    }
}

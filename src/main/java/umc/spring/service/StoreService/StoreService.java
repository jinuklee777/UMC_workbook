package umc.spring.service.StoreService;

import umc.spring.domain.Region;
import umc.spring.domain.Store;
import umc.spring.web.dto.store.StoreRequestDTO;

public interface StoreService {
    Store addStore(Region region, StoreRequestDTO.addDTO request);

    void addReview(StoreRequestDTO.reviewDTO request);
}

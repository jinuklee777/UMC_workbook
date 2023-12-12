package umc.spring.service.StoreService;

import umc.spring.domain.Region;
import umc.spring.domain.Store;
import umc.spring.web.dto.store.StoreRequestDTO;
import umc.spring.web.dto.store.StoreResponseDTO;

public interface StoreService {
    Store addStore(Region region, StoreRequestDTO.addDTO request);

    void addReview(StoreRequestDTO.reviewDTO request);

    StoreResponseDTO.ReviewPreViewListDTO getReviewList(Long memberId, Integer page);

    StoreResponseDTO.ReviewPreViewListDTO getStoreReviewList(Long storeId, Integer page);
}

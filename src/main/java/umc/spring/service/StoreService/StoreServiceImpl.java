package umc.spring.service.StoreService;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.Region;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.repository.MemberRepository;
import umc.spring.repository.StoreRepository;
import umc.spring.web.dto.store.StoreRequestDTO;
import umc.spring.web.dto.store.StoreRequestDTO.addDTO;
import umc.spring.web.dto.store.StoreRequestDTO.reviewDTO;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    @Override
    public Store addStore(Region region, addDTO request) {
        Store store = StoreConverter.toStore(region, request);
        return storeRepository.save(store);
    }

    @Override
    public void addReview(reviewDTO request) {
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new NoSuchElementException("Store not found with id: " + request.getStoreId()));
        Review review = toReview(request, store);
        store.getReviewList().add(review);
        storeRepository.save(store);
    }

    public Review toReview(StoreRequestDTO.reviewDTO request, Store store) {
        return Review.builder()
                .score(request.getScore())
                .title(request.getText())
                .member(memberRepository.findById(request.getMemberId()).
                        orElseThrow(() -> new NoSuchElementException(request.getMemberId() + "번 회원은 없습니다")))
                .store(store)
                .build();
    }
}

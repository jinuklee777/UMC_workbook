package umc.spring.service.StoreService;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import umc.spring.converter.ReviewConverter;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.Member;
import umc.spring.domain.Region;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.repository.MemberRepository;
import umc.spring.repository.MissionRepository;
import umc.spring.repository.ReviewRepository;
import umc.spring.repository.StoreRepository;
import umc.spring.web.dto.store.StoreRequestDTO;
import umc.spring.web.dto.store.StoreRequestDTO.addDTO;
import umc.spring.web.dto.store.StoreRequestDTO.reviewDTO;
import umc.spring.web.dto.store.StoreResponseDTO;
import umc.spring.web.dto.store.StoreResponseDTO.ReviewPreViewListDTO;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final MissionRepository missionRepository;

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

    @Override
    public StoreResponseDTO.ReviewPreViewListDTO getReviewList(Long memberId, Integer page) {
        Member member = memberRepository.findById(memberId).get();
        Page<Review> reviewPage = reviewRepository.findAllByMember(member, PageRequest.of(page, 10));
        return ReviewConverter.reviewPreViewListDTO(reviewPage);
    }

    @Override
    public ReviewPreViewListDTO getStoreReviewList(Long storeId, Integer page) {
        Store store = storeRepository.findById(storeId).get();
        Page<Review> reviewPage = reviewRepository.findAllByStore(store, PageRequest.of(page, 10));
        return ReviewConverter.reviewPreViewListDTO(reviewPage);
    }

    public Review toReview(StoreRequestDTO.reviewDTO request, Store store) {
        return Review.builder()
                .score(request.getScore())
                .title(request.getTitle())
                .body(request.getBody())
                .member(memberRepository.findById(request.getMemberId()).
                        orElseThrow(() -> new NoSuchElementException(request.getMemberId() + "번 회원은 없습니다")))
                .store(store)
                .build();
    }
}

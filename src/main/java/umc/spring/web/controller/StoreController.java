package umc.spring.web.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MissionConverter;
import umc.spring.domain.Mission;
import umc.spring.domain.Region;
import umc.spring.domain.Store;
import umc.spring.service.RegionService.RegionService;
import umc.spring.service.StoreService.MissionService;
import umc.spring.service.StoreService.StoreService;
import umc.spring.web.dto.store.StoreRequestDTO;
import umc.spring.web.dto.store.StoreResponseDTO;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final RegionService regionService;
    private final MissionService missionService;

    // TODO: Store -> StoreResponseDTO로 바꾸기
    @PostMapping("/add")
    public ApiResponse<Store> addStore(@RequestParam String region, @RequestBody StoreRequestDTO.addDTO request) {
        Region regionEntity = regionService.findRegionByNameElseBuild(region);
        Store store = storeService.addStore(regionEntity, request);
        return ApiResponse.onSuccess(store);
    }

    @PostMapping("/review/add")
    public ApiResponse<String> addReview(@RequestBody @Valid StoreRequestDTO.reviewDTO request) {
        storeService.addReview(request);
        return ApiResponse.onSuccess("리뷰 등록 완료");
    }

    @PostMapping("/mission/add")
    public ApiResponse<StoreResponseDTO.missionResponseDTO> addMission(
            @RequestBody @Valid StoreRequestDTO.missionDTO request) {
        Mission mission = missionService.addMission(request);
        return ApiResponse.onSuccess(MissionConverter.toMissionResponse(mission));
    }

    @PostMapping("/mission/start")
    public ApiResponse<String> missionStart(@RequestBody @Valid StoreRequestDTO.missionStartDTO request) {
        Long missionId = request.getMissionId();
        Long memberId = request.getMemberId();
        missionService.startMission(missionId, memberId);
        return ApiResponse.onSuccess("미션 시작");
    }

}

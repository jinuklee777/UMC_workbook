package umc.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import umc.spring.validation.annotation.CheckPage;
import umc.spring.web.dto.store.StoreRequestDTO;
import umc.spring.web.dto.store.StoreResponseDTO;
import umc.spring.web.dto.store.StoreResponseDTO.ReviewPreViewListDTO;
import umc.spring.web.dto.store.StoreResponseDTO.missionResponseListDTO;

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

    @Transactional
    @PostMapping("/mission/start")
    public ApiResponse<String> missionStart(@RequestBody @Valid StoreRequestDTO.missionStartDTO request) {
        Long missionId = request.getMissionId();
        Long memberId = request.getMemberId();
        missionService.startMission(missionId, memberId);
        return ApiResponse.onSuccess("미션 시작");
    }

    @Operation(summary = "특정 회원의 리뷰 목록 조회 API", description = "특정 회원의 리뷰 목록을 조회합니다. Query parameter로 memberId, 페이징을 넘겨주세요.")
    @GetMapping("/reviews")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "회원의 아이디, 쿼리 파라미터 입니다!"),
            @Parameter(name = "page", description = "페이지 번호, 0번이 1 페이지 입니다."),
    })
    public ApiResponse<StoreResponseDTO.ReviewPreViewListDTO> getMyReview(@RequestParam Long memberId, @CheckPage @RequestParam Integer page) {
        ReviewPreViewListDTO reviewList = storeService.getReviewList(memberId, page);
        return ApiResponse.onSuccess(reviewList);
    }

    @Operation(summary = "특정 가게의 리뷰 목록 조회 API", description = "특정 가게의 리뷰 목록을 조회합니다. Path Variable로 storeId, 페이징을 넘겨주세요.")
    @GetMapping("/reviews/{storeId}")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "storeId", description = "가게의 아이디, path variable 입니다!"),
            @Parameter(name = "page", description = "페이지 번호, 0번이 1 페이지 입니다."),
    })
    public ApiResponse<StoreResponseDTO.ReviewPreViewListDTO> getStoreReview(@PathVariable Long storeId, @CheckPage @RequestParam Integer page) {
        ReviewPreViewListDTO reviewList = storeService.getStoreReviewList(storeId, page);
        return ApiResponse.onSuccess(reviewList);
    }

    @Operation(summary = "내가 진행중인 미션 목록 조회 API", description = "내가 진행중인 미션 목록을 조회합니다. Query parameter로 memberId, 페이징을 넘겨주세요.")
    @GetMapping("/mission")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "회원의 아이디, 쿼리 파라미터 입니다!"),
            @Parameter(name = "page", description = "페이지 번호, 0번이 1 페이지 입니다."),
    })
    public ApiResponse<StoreResponseDTO.missionResponseListDTO> getProgressMission(@RequestParam Long memberId, Integer page) {
        missionResponseListDTO missionResponseListDTO = missionService.getAllMissionsForMember(memberId, page);
        return ApiResponse.onSuccess(missionResponseListDTO);
    }

    @Operation(summary = "미션 진행완료로 바꾸는 API", description = "진행중인 미션을 진행완료로 바꿉니다. Path variable로 missionId를 넘겨주세요.")
    @PatchMapping("/mission/{missionId}")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "missionId", description = "미션의 아이디, path variable 입니다!"),
    })
    public ApiResponse<String> updateMissionStatus(@PathVariable Long missionId) {
        missionService.updateMissionStatusToSuccess(missionId);
        return ApiResponse.onSuccess("업데이트 완료.");
    }



}

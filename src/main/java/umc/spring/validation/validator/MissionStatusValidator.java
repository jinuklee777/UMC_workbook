package umc.spring.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.repository.MemberMissionRepository;
import umc.spring.validation.annotation.MissionAlreadyInProgress;

@Transactional
@RequiredArgsConstructor
public class MissionStatusValidator implements ConstraintValidator<MissionAlreadyInProgress, Long> {

    private final MemberMissionRepository memberMissionRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        try {
            MemberMission memberMission = memberMissionRepository.findById(value).orElse(null);

            if (memberMission == null) {
                // 해당 ID에 대한 MemberMission이 존재하지 않을 경우 유효성 검사 통과
                return true;
            }

            if (memberMission.getStatus() == null) {
                memberMission.setStatus("시작"); // 적절한 초기 상태로 설정
                return true;
            }

            // 해당 미션의 상태가 CHALLENGING이 아닌 경우에만 유효성 검사 통과
            return !memberMission.getStatus().equals(MissionStatus.CHALLENGING);
        } catch (Exception e) {
            // 예외 처리 (최종적으로는 로그에 기록)
            return false;
        }
    }
}

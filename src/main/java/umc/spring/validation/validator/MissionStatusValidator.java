package umc.spring.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.repository.MemberMissionRepository;
import umc.spring.validation.annotation.MissionAlreadyInProgress;

@RequiredArgsConstructor
public class MissionStatusValidator implements ConstraintValidator<MissionAlreadyInProgress, Long> {

    private final MemberMissionRepository memberMissionRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        MemberMission memberMission = memberMissionRepository.findById(value).get();
        return !memberMission.getStatus().equals(MissionStatus.CHALLENGING);
    }
}

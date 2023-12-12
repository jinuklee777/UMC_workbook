package umc.spring.service.MemberService;

import umc.spring.domain.Member;
import umc.spring.web.dto.member.MemberRequestDTO;

public interface MemberCommandService {

    Member joinMember(MemberRequestDTO.JoinDto request);
}

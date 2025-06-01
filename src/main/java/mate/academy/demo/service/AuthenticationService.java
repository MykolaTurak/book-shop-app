package mate.academy.demo.service;

import mate.academy.demo.dto.user.UserLoginRequestDto;
import mate.academy.demo.dto.user.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto authenticate(UserLoginRequestDto userLoginRequestDto);

    Long getCurrentUserId();
}

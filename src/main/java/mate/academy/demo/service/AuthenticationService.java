package mate.academy.demo.service;

import mate.academy.demo.dto.user.UserLoginRequestDto;
import mate.academy.demo.dto.user.UserLoginResponseDto;

public interface AuthenticationService {
    public UserLoginResponseDto authenticate(UserLoginRequestDto userLoginRequestDto);
}

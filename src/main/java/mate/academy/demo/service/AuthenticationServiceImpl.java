package mate.academy.demo.service;

import lombok.RequiredArgsConstructor;
import mate.academy.demo.dto.user.UserLoginRequestDto;
import mate.academy.demo.dto.user.UserLoginResponseDto;
import mate.academy.demo.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public UserLoginResponseDto authenticate(UserLoginRequestDto userLoginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequestDto.email(), userLoginRequestDto.password())
        );

        return new UserLoginResponseDto(jwtUtil.generateToken(authentication.getName()));
    }
}

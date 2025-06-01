package mate.academy.demo.service;

import lombok.RequiredArgsConstructor;
import mate.academy.demo.dto.user.UserLoginRequestDto;
import mate.academy.demo.dto.user.UserLoginResponseDto;
import mate.academy.demo.exeption.EntityNotFoundException;
import mate.academy.demo.model.User;
import mate.academy.demo.repository.UserRepisitory;
import mate.academy.demo.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepisitory userRepisitory;

    @Override
    public UserLoginResponseDto authenticate(UserLoginRequestDto userLoginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequestDto.email(), userLoginRequestDto.password())
        );

        return new UserLoginResponseDto(jwtUtil.generateToken(authentication.getName()));
    }

    @Override
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepisitory.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find user with email:" + authentication.getName()));

        return user.getId();
    }
}

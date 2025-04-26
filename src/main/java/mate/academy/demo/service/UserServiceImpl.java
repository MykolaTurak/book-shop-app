package mate.academy.demo.service;

import lombok.RequiredArgsConstructor;
import mate.academy.demo.dto.user.UserRegistrationRequestDto;
import mate.academy.demo.dto.user.UserResponseDto;
import mate.academy.demo.exeption.RegistrationException;
import mate.academy.demo.mapper.UserMapper;
import mate.academy.demo.model.User;
import mate.academy.demo.repository.UserRepisitory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepisitory userRepisitory;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepisitory.existsByEmail(userRegistrationRequestDto.email())) {
            throw new RegistrationException("Email \""
                    + userRegistrationRequestDto.email() + "\" has been already used");
        }

        User user = userMapper.toModel(userRegistrationRequestDto);
        user.setPassword(passwordEncoder.encode(userRegistrationRequestDto.password()));
        return userMapper.toDto(userRepisitory.save(user));
    }
}

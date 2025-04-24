package mate.academy.demo.service;

import lombok.RequiredArgsConstructor;
import mate.academy.demo.dto.user.UserRegistrationRequestDto;
import mate.academy.demo.dto.user.UserResponseDto;
import mate.academy.demo.exeption.RegistrationException;
import mate.academy.demo.mapper.UserMapper;
import mate.academy.demo.model.User;
import mate.academy.demo.repository.UserRepisitory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepisitory userRepisitory;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto save(UserRegistrationRequestDto userRegistrationRequestDto) {
        User user = userMapper.toModel(userRegistrationRequestDto);
        try {
            return userMapper.toDto(userRepisitory.save(user));
        } catch (Exception e) {
            throw new RegistrationException("Can't save user");
        }
    }
}

package mate.academy.demo.service;

import mate.academy.demo.dto.user.UserRegistrationRequestDto;
import mate.academy.demo.dto.user.UserResponseDto;
import mate.academy.demo.exeption.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException;
}

package mate.academy.demo.controler;

import jakarta.validation.Valid;
import mate.academy.demo.dto.user.UserRegistrationRequestDto;
import mate.academy.demo.dto.user.UserResponseDto;
import mate.academy.demo.exeption.RegistrationException;
import mate.academy.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public UserResponseDto register(@Valid @RequestBody UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }
}

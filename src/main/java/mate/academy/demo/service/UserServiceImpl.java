package mate.academy.demo.service;

import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.demo.dto.user.UserRegistrationRequestDto;
import mate.academy.demo.dto.user.UserResponseDto;
import mate.academy.demo.exeption.RegistrationException;
import mate.academy.demo.mapper.UserMapper;
import mate.academy.demo.model.Role;
import mate.academy.demo.model.RoleName;
import mate.academy.demo.model.User;
import mate.academy.demo.repository.RoleRepository;
import mate.academy.demo.repository.UserRepisitory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepisitory userRepisitory;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepisitory.existsByEmail(userRegistrationRequestDto.email())) {
            throw new RegistrationException("Email \""
                    + userRegistrationRequestDto.email() + "\" has been already used");
        }

        User user = userMapper.toModel(userRegistrationRequestDto);
        user.setPassword(passwordEncoder.encode(userRegistrationRequestDto.password()));
        Role userRole = roleRepository.findByRoleName(RoleName.USER).orElseThrow(
                () -> new RegistrationException("Can't add role for user:" + user.getUsername()));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        shoppingCartService.createEmptyCart(user);

        user.setRoles(roles);
        return userMapper.toDto(userRepisitory.save(user));
    }
}

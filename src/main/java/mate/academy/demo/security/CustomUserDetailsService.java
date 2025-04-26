package mate.academy.demo.security;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import mate.academy.demo.repository.UserRepisitory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepisitory userRepisitory;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepisitory.findByEmail(email).orElseThrow(NoSuchElementException::new);
    }
}

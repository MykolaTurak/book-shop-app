package mate.academy.demo.dto.user;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserLoginRequestDto(
        @NotBlank @Length(min = 10, max = 30) String email,
        @NotBlank @Length(min = 8, max = 20) String password
) {
}

package mate.academy.demo.dto.user;

import jakarta.validation.constraints.NotBlank;
import mate.academy.demo.annotation.FieldMatch;
import org.hibernate.validator.constraints.Length;

@FieldMatch(first = "password", second = "repeatPassword", message = "Passwords do not match")
public record UserRegistrationRequestDto(
        @NotBlank @Length(min = 10, max = 30) String email,
        @NotBlank @Length(min = 8, max = 20) String password,
        @NotBlank @Length(min = 8, max = 20) String repeatPassword,
        @NotBlank String firstName,
        @NotBlank String lastName,
        String shippingAddress
) {
}

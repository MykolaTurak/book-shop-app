package mate.academy.demo.dto.user;

import jakarta.validation.constraints.NotBlank;
import mate.academy.demo.annotation.FieldMatch;

@FieldMatch(first = "password", second = "repeatPassword", message = "Passwords do not match")
public record UserRegistrationRequestDto(
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String repeatPassword,
        @NotBlank String firstName,
        @NotBlank String lastName,
        String shippingAddress
) {
}

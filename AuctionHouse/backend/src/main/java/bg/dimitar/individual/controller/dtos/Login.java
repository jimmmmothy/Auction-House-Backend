package bg.dimitar.individual.controller.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}

package bg.dimitar.individual.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FullUser {
    private Long id;
    private String role;
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String country;
}

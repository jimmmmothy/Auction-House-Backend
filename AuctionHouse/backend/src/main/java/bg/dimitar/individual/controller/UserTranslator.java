package bg.dimitar.individual.controller;

import bg.dimitar.individual.controller.dtos.FullUser;
import bg.dimitar.individual.controller.dtos.Login;
import bg.dimitar.individual.controller.dtos.Register;
import bg.dimitar.individual.persistance.entity.UserEntity;

public class UserTranslator {
    private UserTranslator() {}

    public static UserEntity translate(Register register) {
        return UserEntity.builder()
                .email(register.getEmail())
                .role("nonAdmin")
                .firstName(register.getFirstName())
                .lastName(register.getLastName())
                .username(register.getUsername())
                .password(register.getPassword())
                .phoneNumber(register.getPhone())
                .country(register.getCountry())
                .build();
    }

    public static Register translate(UserEntity user) {
        return Register.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .password(user.getPassword())
                .phone(user.getPhoneNumber())
                .country(user.getCountry())
                .build();
    }

    public static UserEntity translate(Login login) {
        return UserEntity.builder()
                .email(login.getEmail())
                .password(login.getPassword())
                .build();
    }

    public static FullUser translateToFullDTO (UserEntity user) {
        return FullUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .country(user.getCountry())
                .build();
    }
}

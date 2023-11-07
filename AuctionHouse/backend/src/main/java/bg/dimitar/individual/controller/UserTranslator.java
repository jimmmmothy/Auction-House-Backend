package bg.dimitar.individual.controller;

import bg.dimitar.individual.controller.dtos.User;
import bg.dimitar.individual.persistance.entity.UserEntity;

public class UserTranslator {
    private UserTranslator() {}

    public static UserEntity translateToEntity(User user) {
        return UserEntity.builder()
                .email(user.getEmail())
                .role("nonAdmin")
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .password(user.getPassword())
                .phoneNumber(user.getPhone())
                .country(user.getCountry())
                .build();
    }

    public static User translateToDTO(UserEntity user) {
        return User.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .password(user.getPassword())
                .phone(user.getPhoneNumber())
                .country(user.getCountry())
                .build();
    }
}

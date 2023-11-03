package bg.dimitar.individual.persistance;

import bg.dimitar.individual.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    //Optional<UserEntity> findbyEmailAndPassword(String email, String password);
}

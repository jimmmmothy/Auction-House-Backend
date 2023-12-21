package bg.dimitar.individual.persistance;

import bg.dimitar.individual.persistance.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    List<ItemEntity> findAllByTitleContainsIgnoreCase(String search);
}

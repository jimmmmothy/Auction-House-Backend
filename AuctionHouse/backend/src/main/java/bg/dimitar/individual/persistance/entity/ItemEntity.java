package bg.dimitar.individual.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemEntity {
    private Long id;
    private String name;
    private String category;
}

package bg.dimitar.individual.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bid {
    private Long itemId;
    private Long userId;
    private double bidAmount;
}

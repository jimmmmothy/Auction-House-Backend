package bg.dimitar.individual.controller.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @NotBlank
    private String title;
    @NotBlank
    private String category;
    private double startingPrice;
    private double currentBid;
}

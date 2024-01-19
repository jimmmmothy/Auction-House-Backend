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
public class Item {
    private long id;
    @NotBlank
    private String title;
    @NotBlank
    private String category;
    private double startingPrice;
    private double currentBid;
    private String description;
    private long postedByUserId;
    private String imageURLs;
}

package bg.dimitar.individual.persistance.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "items")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "starting_price", nullable = false)
    private double startingPrice;
    @Column(name = "current_bid", nullable = false)
    private double currentBid;
    @Column(name = "posted_by_user_id")
    private Long postedByUserId;
}

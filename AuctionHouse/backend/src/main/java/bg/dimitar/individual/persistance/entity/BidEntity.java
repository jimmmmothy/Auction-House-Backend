package bg.dimitar.individual.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "bids")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BidEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "bidder_id")
    private Long bidderId;
    @Column(name = "bid_amount")
    private double bidAmount;
    @Column(name = "bid_time")
    private Date bidTime;
}

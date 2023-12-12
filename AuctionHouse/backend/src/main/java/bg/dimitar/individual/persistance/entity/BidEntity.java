package bg.dimitar.individual.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private Long id;
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "bid_amount")
    private double bidAmount;
    @Column(name = "bid_time")
    private Date bidTime;
}

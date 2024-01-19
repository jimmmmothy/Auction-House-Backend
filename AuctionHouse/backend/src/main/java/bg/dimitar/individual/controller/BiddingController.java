package bg.dimitar.individual.controller;

import bg.dimitar.individual.business.BiddingManager;
import bg.dimitar.individual.controller.dtos.Bid;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bids")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5000/", allowedHeaders = "*")
public class BiddingController {
    public final BiddingManager biddingManager;

    @GetMapping("{id}")
    public ResponseEntity<List<Bid>> getTop3Bids(@PathVariable("id") final long itemId) {
        try {
            List<Bid> response = biddingManager.getTop3Bids(itemId).stream().map(BidTranslator::translate).toList();
            return ResponseEntity.ok(response);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("users/{id}")
    public ResponseEntity<List<Object[]>> getBidsByUser(@PathVariable("id") final long userId) {
        try {
            List<Object[]> response = biddingManager.getBidsByUser(userId);
            return ResponseEntity.ok(response);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping()
    public ResponseEntity<String> addBid(@RequestBody @Valid Bid bid) {
        try {
            biddingManager.addBid(BidTranslator.translate(bid));
            return ResponseEntity.ok().build();
        }
        catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}

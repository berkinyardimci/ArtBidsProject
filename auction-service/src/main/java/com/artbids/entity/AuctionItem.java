package com.artbids.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AuctionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private String productDescription;
    private Integer startingPrice;
    private String artistName;
    private String artistDescription;
    private Integer priceIncreaseRate;
    private String image;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    @JsonBackReference
    private Auction auction;

}
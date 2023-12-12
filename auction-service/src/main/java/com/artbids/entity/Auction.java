package com.artbids.entity;


import com.artbids.entity.enums.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ownerId;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String auctionDescription;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.PENDING;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AuctionItem> auctionItems;



}

package com.artbids.controller;

import com.artbids.data.RestHeader;
import com.artbids.data.request.AddArtRequestDto;
import com.artbids.data.request.SaveAuctionRequestDto;
import com.artbids.entity.AuctionItem;
import com.artbids.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auction")
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody SaveAuctionRequestDto dto) {
        auctionService.createAuction(dto);
        return new ResponseEntity<>("Başarılı", HttpStatus.CREATED);
    }

    @PostMapping("/addart/{auctionId}")
    public ResponseEntity<String> addart(@PathVariable Long auctionId, @RequestBody AddArtRequestDto dto) {
        auctionService.addArt(auctionId,dto);
        return new ResponseEntity<>("Başarılı", HttpStatus.CREATED);
    }
}

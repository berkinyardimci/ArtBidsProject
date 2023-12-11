package com.artbids.controller;

import com.artbids.data.RestHeader;
import com.artbids.data.request.SaveAuctionRequestDto;
import com.artbids.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auction")
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SaveAuctionRequestDto dto) {
        auctionService.createAuction(dto);

        return new ResponseEntity<>("Başarılı", HttpStatus.CREATED);
    }
}

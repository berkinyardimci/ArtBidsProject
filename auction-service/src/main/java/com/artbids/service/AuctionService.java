package com.artbids.service;

import com.artbids.client.UserManager;
import com.artbids.data.request.SaveAuctionRequestDto;
import com.artbids.entity.Auction;
import com.artbids.exception.AuctionNameAlreadyTakenException;
import com.artbids.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final UserManager userManager;


    public void createAuction(SaveAuctionRequestDto dto){
        Long userId = userManager.getUserIdFromUserProfileWithToken(dto.getToken());

        if (auctionRepository.existsByName(dto.getName())){
            throw new AuctionNameAlreadyTakenException("Müzayede ismi zaten kullanılıyor.");
        }
        LocalDateTime startTime = parseStringToDate(dto.getStartTime());
        LocalDateTime endTime = parseStringToDate(dto.getEndTime());

        Auction auction = Auction.builder()
                .ownerId(userId)
                .auctionDescription(dto.getAuctionDescription())
                .name(dto.getName())
                .startTime(startTime)
                .endTime(endTime)
                .build();

        auctionRepository.save(auction);

    }
    private static LocalDateTime parseStringToDate(String userInput) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(userInput, formatter);
    }

}

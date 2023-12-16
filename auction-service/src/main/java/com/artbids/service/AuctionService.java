package com.artbids.service;

import com.artbids.client.UserManager;
import com.artbids.converter.AuctionConverter;
import com.artbids.data.request.AddArtRequestDto;
import com.artbids.data.request.SaveAuctionRequestDto;
import com.artbids.data.response.AddArtResponse;
import com.artbids.data.response.BaseResponse;
import com.artbids.entity.Auction;
import com.artbids.entity.AuctionItem;
import com.artbids.exception.AuctionNameAlreadyTakenException;
import com.artbids.exception.AuctionNotFoundException;
import com.artbids.repository.AuctionItemRepository;
import com.artbids.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final AuctionItemRepository auctionItemRepository;
    private final UserManager userManager;


    public BaseResponse createAuction(SaveAuctionRequestDto dto){
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
        return BaseResponse.builder().build();
    }

    public AddArtResponse addArt(Long auctionId, AddArtRequestDto dto){
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException("Müzayede bulunamadı"));

        AuctionItem auctionItem = AuctionConverter.toAuctionItem(dto);
        auctionItem.setAuction(auction);
        return AuctionConverter.toAddArtResponse(auctionItemRepository.save(auctionItem));

    }

    private static LocalDateTime parseStringToDate(String userInput) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(userInput, formatter);
    }

}

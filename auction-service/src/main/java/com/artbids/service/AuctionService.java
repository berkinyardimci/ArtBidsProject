package com.artbids.service;

import com.artbids.client.AuthServiceClient;
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
import com.artbids.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final AuctionItemRepository auctionItemRepository;
    private final AuthServiceClient authServiceClient;


    public BaseResponse createAuction(SaveAuctionRequestDto dto){
        Long userId = authServiceClient.getUserIdFromUserProfileWithToken(dto.getToken());

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

    public BaseResponse uploadImage(Long auctionItemId,MultipartFile file) throws IOException {
        Optional<AuctionItem> byId = auctionItemRepository.findById(auctionItemId);
        byte[] bytes = ImageUtil.compressImage(file.getBytes());
        byId.get().setImageData(bytes);
        auctionItemRepository.save(byId.get());
        //auctionItemRepository.save(AuctionItem.builder()
        //      .imageData(ImageUtil.compressImage(file.getBytes())).build());
        return  BaseResponse.builder().build();
    }

    public byte[] getImage(Long id) {
        Optional<AuctionItem> byId = auctionItemRepository.findById(id);

        //byte[] image = ImageUtil.decompressImage(dbImage.get().getImageData());
        byte[] image = ImageUtil.decompressImage(byId.get().getImageData());
        return image;
    }

    private static LocalDateTime parseStringToDate(String userInput) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(userInput, formatter);
    }

}

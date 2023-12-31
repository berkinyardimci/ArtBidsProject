package com.artbids.service;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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


    public BaseResponse createAuction(Long id, SaveAuctionRequestDto dto){

        if (auctionRepository.existsByName(dto.getName())){
            throw new AuctionNameAlreadyTakenException("Müzayede ismi zaten kullanılıyor.");
        }
        LocalDateTime startTime = parseStringToDate(dto.getStartTime());
        LocalDateTime endTime = parseStringToDate(dto.getEndTime());

        Auction auction = Auction.builder()
                .ownerId(id)
                .auctionDescription(dto.getAuctionDescription())
                .name(dto.getName())
                .startTime(startTime)
                .endTime(endTime)
                .build();
        auctionRepository.save(auction);
        return BaseResponse.builder().build();
    }
    public AddArtResponse addArt(Long auctionId,AddArtRequestDto dto,MultipartFile file ){
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException("Müzayede bulunamadı"));

        byte[] bytes = convertImage(file);

        AuctionItem auctionItem = AuctionConverter.toAuctionItem(dto);
        auctionItem.setImageData(bytes);
        auctionItem.setAuction(auction);
        return AuctionConverter.toAddArtResponse(auctionItemRepository.save(auctionItem));

    }

    public byte[] convertImage(MultipartFile file){
        byte[] bytes = new byte[0];
        try {
            bytes = ImageUtil.compressImage(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }
    public BaseResponse uploadImage(Long auctionItemId,MultipartFile file) throws IOException {
        Optional<AuctionItem> byId = auctionItemRepository.findById(auctionItemId);
        byte[] bytes = ImageUtil.compressImage(file.getBytes());
        byId.get().setImageData(bytes);
        auctionItemRepository.save(byId.get());
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

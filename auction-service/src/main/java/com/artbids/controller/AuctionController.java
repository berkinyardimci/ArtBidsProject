package com.artbids.controller;

import com.artbids.data.RestHeader;
import com.artbids.data.request.AddArtRequestDto;
import com.artbids.data.request.SaveAuctionRequestDto;
import com.artbids.data.response.BaseResponse;
import com.artbids.entity.AuctionItem;
import com.artbids.service.AuctionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auction")
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping("/save/{userProfileId}")
    public ResponseEntity<String> createAuction(@PathVariable Long userProfileId,@RequestBody SaveAuctionRequestDto dto) {
        auctionService.createAuction(userProfileId,dto);
        return new ResponseEntity<>("Başarılı", HttpStatus.CREATED);
    }

    @PostMapping(value = "/addArt/{auctionId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addArt(@PathVariable Long auctionId, @RequestParam("image") MultipartFile file, @RequestParam("dto") String dto) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            AddArtRequestDto convertedDto = objectMapper.readValue(dto, AddArtRequestDto.class);
            auctionService.addArt(auctionId,convertedDto,file);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>("Başarılı", HttpStatus.CREATED);
    }
    /*
    @PostMapping("/upload/{auctionItemId}")
    public ResponseEntity<?> uploadImage(@PathVariable Long auctionItemId,@RequestParam("image") MultipartFile file) throws IOException {
        BaseResponse response = auctionService.uploadImage(auctionItemId,file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

     */

    @GetMapping("/{id}")
    public ResponseEntity<?>  getImageByName(@PathVariable("id") Long id){
        byte[] image = auctionService.getImage(id);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }
}

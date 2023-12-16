package com.artbids.converter;

import com.artbids.data.request.AddArtRequestDto;
import com.artbids.data.response.AddArtResponse;
import com.artbids.entity.AuctionItem;

public class AuctionConverter {


    public static AddArtResponse toAddArtResponse(AuctionItem auctionItem){
        if (auctionItem != null){
            return AddArtResponse.builder()
                    .productName(auctionItem.getProductName())
                    .productDescription(auctionItem.getProductDescription())
                    .startingPrice(auctionItem.getStartingPrice())
                    .artistName(auctionItem.getArtistName())
                    .artistDescription(auctionItem.getArtistDescription())
                    .priceIncreaseRate(auctionItem.getPriceIncreaseRate())
                    .image(auctionItem.getImage())
                    .build();
        }
        return null;
    }

    public static AuctionItem toAuctionItem(AddArtRequestDto addArtRequestDto){
        if (addArtRequestDto != null){
            return AuctionItem.builder()
                    .productName(addArtRequestDto.getProductName())
                    .productDescription(addArtRequestDto.getProductDescription())
                    .startingPrice(addArtRequestDto.getStartingPrice())
                    .artistName(addArtRequestDto.getArtistName())
                    .artistDescription(addArtRequestDto.getArtistDescription())
                    .priceIncreaseRate(addArtRequestDto.getPriceIncreaseRate())
                    .image(addArtRequestDto.getImage())
                    .build();
        }
        return null;
    }
}

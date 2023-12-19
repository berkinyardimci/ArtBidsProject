package com.artbids.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddArtRequestDto {

    private String productName;
    private String productDescription;
    private Integer startingPrice;
    private String artistName;
    private String artistDescription;
    private Integer priceIncreaseRate;
    private byte[] imageData;

}

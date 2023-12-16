package com.artbids.data.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AddArtResponse {

    private String productName;
    private String productDescription;
    private Integer startingPrice;
    private String artistName;
    private String artistDescription;
    private Integer priceIncreaseRate;
    private String image;

}

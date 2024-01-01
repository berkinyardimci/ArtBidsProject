package com.artbids.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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

}

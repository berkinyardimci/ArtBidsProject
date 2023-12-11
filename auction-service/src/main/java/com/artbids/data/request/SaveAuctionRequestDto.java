package com.artbids.data.request;

import com.artbids.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveAuctionRequestDto {

    private String token;
    private String name;
    //yyyy-MM-dd HH:mm
    private String startTime;
    private String endTime;
    private String auctionDescription;

}

package com.artbids.data.response.auth;


import com.artbids.data.response.BaseResponse;
import com.artbids.entity.enums.EStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class ChangeStatusResponse extends BaseResponse {

    private EStatus status;
}

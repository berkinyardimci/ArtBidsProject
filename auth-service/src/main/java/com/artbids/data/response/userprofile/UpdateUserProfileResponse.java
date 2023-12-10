package com.artbids.data.response.userprofile;


import com.artbids.data.dto.UserProfileDto;
import com.artbids.data.response.BaseResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class UpdateUserProfileResponse extends BaseResponse {

    private UserProfileDto userProfileDto;
}

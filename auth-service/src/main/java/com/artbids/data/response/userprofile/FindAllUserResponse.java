package com.artbids.data.response.userprofile;


import com.artbids.data.dto.UserProfileDto;
import com.artbids.data.response.BaseResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
public class FindAllUserResponse extends BaseResponse {

    List<UserProfileDto> profileList;
}

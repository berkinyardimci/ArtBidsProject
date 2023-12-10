package com.artbids.convertor;

import com.artbids.data.dto.UserProfileDto;
import com.artbids.data.request.auth.RegisterRequestDto;
import com.artbids.data.request.userprofile.UpdateUserProfileRequestDto;
import com.artbids.data.response.auth.LoginResponse;
import com.artbids.data.response.userprofile.FindAllUserResponse;
import com.artbids.data.response.userprofile.UpdateUserProfileResponse;
import com.artbids.entity.Auth;
import com.artbids.entity.UserProfile;
import com.artbids.entity.enums.ERole;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Convertor {


    public static Auth toAuth(RegisterRequestDto dto) {

        return Auth.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .roles(Set.of(ERole.USER))
                .user(new UserProfile())
                .build();
    }

    public static LoginResponse loginResponse(Auth auth, String token) {
        return LoginResponse.builder()
                .token(token)
                .roles(auth.getRoles())
                .build();
    }

    public static UserProfile toUserProfile(UpdateUserProfileRequestDto dto, UserProfile userProfile) {
        userProfile.setAvatar(dto.getAvatar());
        userProfile.setName(dto.getName());
        userProfile.setAbout(dto.getAbout());
        userProfile.setSurName(dto.getSurName());
        userProfile.setBirthDate(dto.getBirthDate());
        userProfile.setPhone(dto.getPhone());
        return userProfile;
    }

    public static FindAllUserResponse toFindAllUserResponse(List<UserProfile> userProfileList) {
        List<UserProfileDto> profileDtoList = userProfileList.stream()
                .map(Convertor::toUserProfileDto)
                .collect(Collectors.toList());

        return FindAllUserResponse.builder()
                .profileList(profileDtoList)
                .build();
    }

    public static UserProfileDto toUserProfileDto(UserProfile userProfile) {
        return UserProfileDto.builder()
                .name(userProfile.getName())
                .surName(userProfile.getSurName())
                .about(userProfile.getAbout())
                .avatar(userProfile.getAvatar())
                .phone(userProfile.getPhone())
                .birthDate(userProfile.getBirthDate())
                .username(userProfile.getAuth().getUsername())
                .email(userProfile.getAuth().getEmail())
                .eStatus(userProfile.getAuth().getStatus())
                .build();
    }
    public static UpdateUserProfileResponse toUpdateUserProfileResponse(UserProfile userProfile) {
        UserProfileDto userProfileDto = toUserProfileDto(userProfile);

        return UpdateUserProfileResponse.builder()
                .userProfileDto(userProfileDto)
                .build();
    }
}

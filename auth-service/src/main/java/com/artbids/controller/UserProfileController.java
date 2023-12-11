package com.artbids.controller;

import com.artbids.data.RestHeader;
import com.artbids.data.request.userprofile.UpdateUserProfileRequestDto;
import com.artbids.data.response.userprofile.FindAllUserResponse;
import com.artbids.data.response.userprofile.UpdateUserProfileResponse;
import com.artbids.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/userprofile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping("/update")
    public ResponseEntity<UpdateUserProfileResponse> updateUserProfile(@RequestBody UpdateUserProfileRequestDto dto) {
        UpdateUserProfileResponse updateUserProfileResponse = userProfileService.updateUserProfile(dto);
        updateUserProfileResponse.setRestHeader(new RestHeader(true,"Güncelleme Başarılı",null));
        return new ResponseEntity<>(updateUserProfileResponse, HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    public ResponseEntity<FindAllUserResponse> findAll(@RequestParam String token) {
        FindAllUserResponse response = userProfileService.findAll(token);
        response.setRestHeader(new RestHeader(true, "Userlar Listelendi",null));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/getUserIdFromToken/{token}")
    public Long getUserIdFromUserProfileWithToken(@PathVariable String token){
        return userProfileService.getUserIdFromUserProfileWithToken(token);
    }
}

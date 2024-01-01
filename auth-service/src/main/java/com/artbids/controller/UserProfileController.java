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
@RequestMapping("/user/profile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PutMapping("/update/{id}")
    public ResponseEntity<UpdateUserProfileResponse> updateUserProfile(@PathVariable Long id, @RequestBody UpdateUserProfileRequestDto dto) {
        UpdateUserProfileResponse updateUserProfileResponse = userProfileService.updateUserProfile(id,dto);
        updateUserProfileResponse.setRestHeader(new RestHeader(true,"Güncelleme Başarılı"));
        return new ResponseEntity<>(updateUserProfileResponse, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<FindAllUserResponse> findAll() {
        FindAllUserResponse response = userProfileService.findAll();
        response.setRestHeader(new RestHeader(true, "Userlar Listelendi"));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}

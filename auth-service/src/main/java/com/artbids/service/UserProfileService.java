package com.artbids.service;

import com.artbids.config.JwtTokenManager;
import com.artbids.convertor.Convertor;
import com.artbids.data.request.auth.UpdateEmailRequestDto;
import com.artbids.data.request.auth.UpdateUsernameRequestDto;
import com.artbids.data.request.userprofile.UpdateUserProfileRequestDto;
import com.artbids.data.response.userprofile.FindAllUserResponse;
import com.artbids.data.response.userprofile.UpdateUserProfileResponse;
import com.artbids.entity.UserProfile;
import com.artbids.entity.enums.EStatus;
import com.artbids.exception.InvalidTokenException;
import com.artbids.exception.UserNotFoundException;
import com.artbids.repository.IUserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final IUserProfileRepository userProfileRepository;
    private final JwtTokenManager jwtTokenManager;
    private final AuthService authService;

    public UserProfile save(UserProfile userProfile) {
        try {
            return userProfileRepository.save(userProfile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Olusturulamadı");
        }
    }

    public UpdateUserProfileResponse updateUserProfile(UpdateUserProfileRequestDto dto) {
        UserProfile userProfile = getUserProfileFromToken(dto.getToken());
        if (!Objects.equals(userProfile.getAuth().getUsername(), dto.getUsername())) {
            authService.updateUsername(UpdateUsernameRequestDto.builder().username(dto.getUsername()).token(dto.getToken()).build());
        }
        if (!Objects.equals(userProfile.getAuth().getEmail(), dto.getEmail())) {
            authService.updateEmail(UpdateEmailRequestDto.builder().email(dto.getEmail()).token(dto.getToken()).build());
        }
        UserProfile updatedUserProfile = userProfileRepository.save(Convertor.toUserProfile(dto, userProfile));
        return Convertor.toUpdateUserProfileResponse(updatedUserProfile);
    }


    private UserProfile getUserProfileFromToken(String token) {
        Long authId = jwtTokenManager.getAuthIdFromToken(token)
                .orElseThrow(() -> new UserNotFoundException("User Bulunamadı"));

        return userProfileRepository.findById(authId)
                .orElseThrow(() -> new UserNotFoundException("User Bulunamadı"));
    }

    public FindAllUserResponse findAll(String token) {
        jwtTokenManager.verifyToken(token);
        List<UserProfile> activeUserProfiles = userProfileRepository
                .findAll()
                .stream()
                .filter(userProfile -> userProfile.getAuth().getStatus() == EStatus.ACTIVE)
                .collect(Collectors.toList());
        return Convertor.toFindAllUserResponse(activeUserProfiles);
    }

    public Long getUserIdFromUserProfileWithToken(String token) {
        return jwtTokenManager.getAuthIdFromToken(token).orElseThrow(() -> new InvalidTokenException("Geçersiz Token"));
    }
}
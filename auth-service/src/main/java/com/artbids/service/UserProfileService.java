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
import java.util.Optional;
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

    public UpdateUserProfileResponse updateUserProfile(Long id,UpdateUserProfileRequestDto dto) {
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findById(id);
        if (!Objects.equals(optionalUserProfile.get().getAuth().getUsername(), dto.getUsername())) {
            authService.updateUsername(id,UpdateUsernameRequestDto.builder().username(dto.getUsername()).build());
        }
        if (!Objects.equals(optionalUserProfile.get().getAuth().getEmail(), dto.getEmail())) {
            authService.updateEmail(id,UpdateEmailRequestDto.builder().email(dto.getEmail()).build());
        }
        UserProfile updatedUserProfile = userProfileRepository.save(Convertor.toUserProfile(dto, optionalUserProfile.get()));
        return Convertor.toUpdateUserProfileResponse(updatedUserProfile);
    }

    public FindAllUserResponse findAll() {
        List<UserProfile> activeUserProfiles = userProfileRepository
                .findAll()
                .stream()
                .filter(userProfile -> userProfile.getAuth().getStatus() == EStatus.ACTIVE)
                .collect(Collectors.toList());
        return Convertor.toFindAllUserResponse(activeUserProfiles);
    }

}
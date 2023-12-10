package com.artbids.service;

import com.artbids.config.JwtTokenManager;
import com.artbids.convertor.Convertor;
import com.artbids.data.request.auth.*;
import com.artbids.data.response.auth.*;
import com.artbids.entity.Auth;
import com.artbids.entity.enums.EStatus;
import com.artbids.exception.AccountAlreadyActive;
import com.artbids.exception.AccountIsNotActiveException;
import com.artbids.exception.PasswordNotMatchesException;
import com.artbids.exception.UserNotFoundException;
import com.artbids.model.SendEmailModel;
import com.artbids.repository.IAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IAuthRepository authRepository;
    private final JwtTokenManager jwtTokenManager;
    private final KafkaProducerService kafkaProducerService;

    public void save(Auth auth) {
        try {
            authRepository.save(auth);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Olusturulamadı");
        }
    }


    public RegisterResponse register(RegisterRequestDto dto) {
        try {

            Auth auth = authRepository.save(Convertor.toAuth(dto));

            SendEmailModel email = SendEmailModel.builder()
                    .username(dto.getUsername())
                    .email(dto.getEmail())
                    .authId(auth.getId())
                    .build();
            kafkaProducerService.sendMessage(email);
            RegisterResponse response = new RegisterResponse();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Kullanıcı kayıt olamadı");
        }
    }

    public LoginResponse login(LoginRequestDto dto) {
        Optional<Auth> auth;
        if (!checkInputIsEmail(dto.getEmail())) {
            auth = authRepository.findByUsernameAndPassword(dto.getEmail(), dto.getPassword());
        } else {
            auth = authRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
        }
        return auth.map(this::validateUserStatus)
                .map(validAuth -> {
                    String token = jwtTokenManager.createToken(validAuth)
                            .orElseThrow(() -> new RuntimeException("Token could not be generated"));
                    return Convertor.loginResponse(validAuth, token);
                })
                .orElseThrow(() -> new UserNotFoundException("User Bulunamadı"));
    }

    private boolean checkInputIsEmail(String email) {
        return email.contains("@");
    }

    private Auth validateUserStatus(Auth auth) {
        if (auth.getStatus() != EStatus.ACTIVE) {
            throw new AccountIsNotActiveException("Hesabınız aktif değil");
        }
        return auth;
    }

    public UpdateUsernameResponse updateUsername(UpdateUsernameRequestDto dto) {
        Long authId = jwtTokenManager.getAuthIdFromToken(dto.getToken())
                .orElseThrow(() -> new UserNotFoundException("User Bulunamadı"));

        Auth auth = authRepository.findById(authId)
                .orElseThrow(() -> new UserNotFoundException("User Bulunamadı"));

        auth.setUsername(dto.getUsername());
        authRepository.save(auth);
        return UpdateUsernameResponse.builder()
                .username(dto.getUsername())
                .build();
    }

    public UpdateEmailResponse updateEmail(UpdateEmailRequestDto dto) {
        Auth auth = getUserProfileFromToken(dto.getToken());


        auth.setEmail(dto.getEmail());
        authRepository.save(auth);
        return UpdateEmailResponse.builder()
                .email(dto.getEmail())
                .build();
    }

    public ChangeStatusResponse changeStatusToActıve(Long id){
        Optional<Auth> optionalAuth = Optional.ofNullable(authRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Bulunamadı")));

        if(optionalAuth.get().getStatus() == EStatus.ACTIVE){
            throw new AccountAlreadyActive("Hesabınız zaten aktif");
        }

        ChangeStatusResponse response = new ChangeStatusResponse();
        optionalAuth.get().setStatus(EStatus.ACTIVE);
        response.setStatus(optionalAuth.get().getStatus());
        authRepository.save(optionalAuth.get());
        return response;
    }

    public ChangePasswordResponse changePassword(ChangePasswordRequestDto dto) {
        Auth auth = getUserProfileFromToken(dto.getToken());

        if(!dto.getOldPassword().equals(auth.getPassword())){
            throw new PasswordNotMatchesException("Eski Şifreniz Doğru Değil");
        }
        if(!dto.getNewPassword().equals(dto.getReNewPassword())){
            throw new PasswordNotMatchesException("Yeni Şifreler uyuşmuyor");
        }

        auth.setPassword(dto.getNewPassword());
        authRepository.save(auth);
        return ChangePasswordResponse.builder().build();
    }

    public ForgetPasswordResponse forgetPassword(ForgetPasswordRequestDto dto){
        Auth auth = authRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Bu email ile User Bulunamadı"));

        auth.setPassword("ZZZZ");
        return ForgetPasswordResponse.builder().build();
    }

    public boolean deleteUserProfile(String token) {
        try {
            Auth auth = getUserProfileFromToken(token);
            auth.setStatus(EStatus.DELETED);
            authRepository.delete(auth);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Silme işlemi başarısız");
        }
    }
    private Auth getUserProfileFromToken(String token) {
        Long authId = jwtTokenManager.getAuthIdFromToken(token)
                .orElseThrow(() -> new UserNotFoundException("User Bulunamadı"));

        return authRepository.findById(authId)
                .orElseThrow(() -> new UserNotFoundException("User Bulunamadı"));
    }

}

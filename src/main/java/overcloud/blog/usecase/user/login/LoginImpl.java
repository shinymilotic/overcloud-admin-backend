package overcloud.blog.usecase.user.login;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import overcloud.blog.auth.service.JwtUtils;
import overcloud.blog.auth.service.SpringAuthenticationService;
import overcloud.blog.entity.RefreshTokenEntity;
import overcloud.blog.entity.UserEntity;
import overcloud.blog.exception.InvalidDataException;
import overcloud.blog.repository.jparepository.JpaRefreshTokenRepository;
import overcloud.blog.response.ApiError;
import overcloud.blog.utils.validation.ObjectsValidator;

import java.util.Optional;
import java.util.UUID;

import static overcloud.blog.usecase.user.login.LoginResMsg.USER_LOGIN_FAILED;

@Service
public class LoginImpl implements Login {
    private final SpringAuthenticationService authenticationService;
    private final JwtUtils jwtUtils;
    private final ObjectsValidator<LoginRequest> validator;
    private final JpaRefreshTokenRepository refreshTokenRepository;

    public LoginImpl(SpringAuthenticationService authenticationService,
                     JwtUtils jwtUtils,
                     ObjectsValidator<LoginRequest> validator,
                     JpaRefreshTokenRepository refreshTokenRepository) {
        this.authenticationService = authenticationService;
        this.jwtUtils = jwtUtils;
        this.validator = validator;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        Optional<ApiError> apiError = validator.validate(loginRequest);
        if (apiError.isPresent()) {
            throw new InvalidDataException(apiError.get());
        }

        String email = loginRequest.getEmail();
        String hashedPassword = loginRequest.getPassword();
        UserEntity user = authenticationService.authenticate(email, hashedPassword)
                .orElseThrow(() -> validator.fail(USER_LOGIN_FAILED))
                .getUser();

        if (!user.isEnable()) {
            throw validator.fail(USER_LOGIN_FAILED);
        }

        String accessToken = jwtUtils.encode(user.getEmail());
        String refreshToken = jwtUtils.generateRefreshToken(user.getEmail());

        saveDBRefreshToken(refreshToken, user.getUserId());

        Cookie jwtTokenCookie = new Cookie("jwtToken", accessToken);
        jwtTokenCookie.setMaxAge(86400);
        jwtTokenCookie.setSecure(false);
        jwtTokenCookie.setHttpOnly(true);
        jwtTokenCookie.setPath("/");
        jwtTokenCookie.setDomain("localhost");
//      jwtTokenCookie.setSecure(true);
        Cookie jwtRefreshTokenCookie = new Cookie("refreshToken", refreshToken);
        jwtRefreshTokenCookie.setMaxAge(86400);
        jwtRefreshTokenCookie.setSecure(false);
        jwtRefreshTokenCookie.setHttpOnly(true);
        jwtRefreshTokenCookie.setPath("/");
        jwtRefreshTokenCookie.setDomain("localhost");
//        jwtRefreshTokenCookie.setSecure(true);
//        jwtRefreshTokenCookie.setAttribute("SameSite", "None");

        response.addCookie(jwtTokenCookie);
        response.addCookie(jwtRefreshTokenCookie);

        return toUserResponse(user);
    }

    private LoginResponse toUserResponse(UserEntity user) {
        LoginResponse response = new LoginResponse();
        response.setId(user.getUserId());
        response.setEmail(user.getEmail());
        response.setBio(user.getBio());
        response.setImage(user.getImage());
        return response;
    }

    private void saveDBRefreshToken(String refreshToken, UUID userId) {
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setRefreshTokenId(UuidCreator.getTimeOrderedEpoch());
        refreshTokenEntity.setRefreshToken(refreshToken);
        refreshTokenEntity.setUserId(userId);
        refreshTokenRepository.save(refreshTokenEntity);
    }
}

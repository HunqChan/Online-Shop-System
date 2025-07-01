package service;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import util.GoogleUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class GoogleService {

    /**
     * Nhận code từ Google → gọi API đổi lấy access_token → lấy user info
     */
    public static Userinfo getUserInfoFromCode(String code) throws IOException, GeneralSecurityException {

        // Đổi code sang access token
        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                "https://oauth2.googleapis.com/token",
                GoogleUtils.getClientId(),
                GoogleUtils.getClientSecret(),
                code,
                GoogleUtils.getRedirectUri()
        ).execute();

        // Tạo đối tượng gọi API userinfo
        Oauth2 oauth2 = new Oauth2.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                request -> request.getHeaders().setAuthorization("Bearer " + tokenResponse.getAccessToken())
        ).setApplicationName("OSS").build();

        return oauth2.userinfo().get().execute(); // Lấy dữ liệu người dùng
    }
}

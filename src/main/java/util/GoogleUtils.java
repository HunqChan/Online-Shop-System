package util;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleUtils {
    private static final String CLIENT_ID = "933351406912-n8o1u31j350v0euv45na10bbse7lgtel.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://localhost:9999/Online_Shop_System_war_exploded/login-google";
    private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email";

    public static String getLoginURL() throws GeneralSecurityException, IOException {
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                CLIENT_ID,
                CLIENT_SECRET,
                Collections.singleton(SCOPE)
        ).build();

        return flow.newAuthorizationUrl()
                .setRedirectUri(REDIRECT_URI)
                .build();
    }

    public static String getClientId() {
        return CLIENT_ID;
    }

    public static String getClientSecret() {
        return CLIENT_SECRET;
    }

    public static String getRedirectUri() {
        return REDIRECT_URI;
    }
}

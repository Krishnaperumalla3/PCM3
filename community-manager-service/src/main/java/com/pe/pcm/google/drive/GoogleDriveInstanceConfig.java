package com.pe.pcm.google.drive;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.properties.GoogleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Component
public class GoogleDriveInstanceConfig {
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private final PasswordUtilityService passwordUtilityService;
    private final GoogleProperties googleProperties;

    @Autowired
    public GoogleDriveInstanceConfig(PasswordUtilityService passwordUtilityService, GoogleProperties googleProperties) {
        this.passwordUtilityService = passwordUtilityService;
        this.googleProperties = googleProperties;
    }

    public Drive getInstance(String encAuthData) throws GeneralSecurityException, IOException {
        return new Drive.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, getCredentials(encAuthData))
                .setApplicationName(googleProperties.getApplicationName())
                .build();
    }

    public HttpRequestInitializer getCredentials(String encAuthData) throws IOException {
        return new HttpCredentialsAdapter(
                ServiceAccountCredentials.fromStream(
                        new ByteArrayInputStream(passwordUtilityService.decryptValueWithJava8(encAuthData).getBytes(StandardCharsets.UTF_8))
                ).createScoped(SCOPES)
        );
    }
}


package com.example.e_voting_system.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.path}") // Path to your Firebase service account JSON
    private String firebaseConfigPath;

    @PostConstruct
    public void initialize() {
        try {
            // Check if FirebaseApp is already initialized
            if (!FirebaseApp.getApps().isEmpty()) {
                // Firebase already initialized, no need to do it again
                return;
            }

            InputStream serviceAccount;

            if (firebaseConfigPath.startsWith("classpath:")) {
                String resourcePath = firebaseConfigPath.replace("classpath:", "");
                serviceAccount = getClass().getClassLoader().getResourceAsStream(resourcePath);
            } else {
                serviceAccount = new FileInputStream(firebaseConfigPath);
            }

            if (serviceAccount == null) {
                throw new RuntimeException("Could not find the Firebase service account key file.");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Firebase", e);
        }
    }
}

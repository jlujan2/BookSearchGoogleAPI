package com.juank.config.firebase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FirebaseConfig {
	
	@PostConstruct
	public void  configureFirebaseConnection() throws IOException {
		
		System.out.println(new File("classpath::.").getAbsolutePath());
		File file = ResourceUtils.getFile("src/main/resources/config/api-project-a2ea5-firebase-adminsdk-q90f8-466bd2b606.json");
	FileInputStream serviceAccount =
			new FileInputStream(file);

			FirebaseOptions options = new FirebaseOptions.Builder()
			  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
			  .build();

			FirebaseApp.initializeApp(options);
	}
}

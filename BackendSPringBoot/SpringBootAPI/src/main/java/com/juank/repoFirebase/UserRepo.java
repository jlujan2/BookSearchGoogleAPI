package com.juank.repoFirebase;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.juank.entity.User;

@Service
public class UserRepo {
	
	public void createUser(User user) {
		Firestore firestore = FirestoreClient.getFirestore();
		
		DocumentReference docReference = firestore.collection("user").document();
		
		//user.setId(docReference.getId().toString());
		//ApiFuture<WriteResult> apiFuture = docReference.set(user);
	}
	
	
}

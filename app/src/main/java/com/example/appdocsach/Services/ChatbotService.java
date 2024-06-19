package com.example.appdocsach.Services;

import android.content.Context;

import com.example.appdocsach.R;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.cloud.dialogflow.v2.TextInput.Builder;

import java.io.InputStream;
import java.util.UUID;

public class ChatbotService {
    private SessionsClient sessionsClient;
    private SessionName sessionName;

    public ChatbotService(Context context, String projectId) {
        try {
            InputStream stream = context.getResources().openRawResource(R.raw.credentials);
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream);
            SessionsSettings sessionsSettings = SessionsSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
            sessionsClient = SessionsClient.create(sessionsSettings);
            sessionName = SessionName.of(projectId, UUID.randomUUID().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public QueryResult detectIntent(String text) {
        try {
            Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode("en-US");
            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
            return sessionsClient.detectIntent(sessionName, queryInput).getQueryResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

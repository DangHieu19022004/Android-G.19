package com.example.appdocsach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.appdocsach.Services.DialogFlowService;
import com.example.appdocsach.model.DialogFlowRequest;
import com.example.appdocsach.model.DialogFlowResponse;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatFragment extends DialogFragment {
    private static final String BASE_URL = "https://dialogflow.googleapis.com/";
    private static final String PROJECT_ID = "appdocsach-18d2f";
    private EditText editTextMessage;
    private Button buttonSend;
    private TextView textViewResponse;
    private String sessionId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        // Initialize views
        editTextMessage = rootView.findViewById(R.id.editTextMessage);
        buttonSend = rootView.findViewById(R.id.buttonSend);
        textViewResponse = rootView.findViewById(R.id.textViewResponse);

        // Generate or retrieve a unique SESSION_ID
        sessionId = UUID.randomUUID().toString();

        // Initialize Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DialogFlowService service = retrofit.create(DialogFlowService.class);

        // Display initial greeting message
        textViewResponse.setText("Xin chào! Hãy nhập tin nhắn của bạn.");

        // Handle send button click event
        buttonSend.setOnClickListener(v -> {
            String message = editTextMessage.getText().toString().trim();
            if (!message.isEmpty()) {
                // Create request to DialogFlow
                DialogFlowRequest.TextInput textInput = new DialogFlowRequest.TextInput(message, "vi");
                DialogFlowRequest.QueryInput queryInput = new DialogFlowRequest.QueryInput(textInput);
                DialogFlowRequest request = new DialogFlowRequest(queryInput);

                // Call detectIntent API
                Call<DialogFlowResponse> call = service.detectIntent(request, PROJECT_ID, sessionId);
                call.enqueue(new Callback<DialogFlowResponse>() {
                    @Override
                    public void onResponse(Call<DialogFlowResponse> call, Response<DialogFlowResponse> response) {
                        if (response.isSuccessful()) {
                            DialogFlowResponse dialogFlowResponse = response.body();
                            // Display response on TextView
                            textViewResponse.setText(dialogFlowResponse.getFulfillmentText());
                        } else {
                            Toast.makeText(getContext(), "Error: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DialogFlowResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                // Clear EditText after sending
                editTextMessage.getText().clear();
            } else {
                Toast.makeText(getContext(), "Vui lòng nhập tin nhắn trước khi gửi.", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}

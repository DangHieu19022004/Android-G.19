package com.example.appdocsach.Fragment.options;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appdocsach.Activity.Setting;
import com.example.appdocsach.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.json.JSONException;

public class UserFragment extends Fragment {
    GoogleSignInOptions gso;

    private static final String ARG_USERNAME = "USERNAME";
    private static final String ARG_EMAIL = "EMAIL";

    private String username;
    private String email;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance(String username, String email) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME);
            email = getArguments().getString(ARG_EMAIL);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView usernameTextView = view.findViewById(R.id.username);
        TextView emailTextView = view.findViewById(R.id.email);

        usernameTextView.setText(username);
        emailTextView.setText(email);

        //Graph API facebook
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) {
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    (object, response) -> {
                        // Application code
                        try {
                            assert object != null;
                            String name = object.getString("name");
                            usernameTextView.setText(name);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link");
            request.setParameters(parameters);
            request.executeAsync();
        }

        //Setting
        ImageView setting = view.findViewById(R.id.setting);
        setting.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), Setting.class));
            if (getActivity() != null) {
                getActivity().finish();
            }
        });
    }
}

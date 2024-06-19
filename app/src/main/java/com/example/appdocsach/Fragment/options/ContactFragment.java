package com.example.appdocsach.Fragment.options;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.appdocsach.R;

public class ContactFragment extends Fragment {
    AppCompatButton btn1, btn2, btn3;
    ImageView backarrow;
    private static final int CALL_PHONE_PERMISSION_REQUEST_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backarrow = view.findViewById(R.id.backarrow);
        btn1 = view.findViewById(R.id.btn1);
        btn3 = view.findViewById(R.id.btn3);

        // Set click listener for backarrow to handle navigation back
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed(); // Quay lại fragment trước đó (UserFragment)
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Quyền gọi điện thoại chưa được cấp
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Yêu cầu quyền gọi điện thoại
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.CALL_PHONE},
                                CALL_PHONE_PERMISSION_REQUEST_CODE);
                    } else {    // Quyền gọi điện thoại đã được cấp
                        // Tiến hành thực hiện cuộc gọi điện thoại
                        Toast.makeText(requireContext(), btn1.getText().toString(), Toast.LENGTH_SHORT).show();
                        makePhoneCall(btn1.getText().toString());
                    }
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Quyền gọi điện thoại chưa được cấp
                        // Yêu cầu quyền gọi điện thoại
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.CALL_PHONE},
                                CALL_PHONE_PERMISSION_REQUEST_CODE);
                    } else {
                        // Quyền gọi điện thoại đã được cấp
                        // Tiến hành thực hiện cuộc gọi điện thoại
                        Toast.makeText(requireContext(), btn3.getText().toString(), Toast.LENGTH_SHORT).show();
                        makePhoneCall(btn3.getText().toString());
                    }
                }
            }
        });

    }

    private void makePhoneCall(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}

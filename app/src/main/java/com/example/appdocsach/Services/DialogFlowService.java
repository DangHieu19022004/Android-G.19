package com.example.appdocsach.Services;
import com.example.appdocsach.model.DialogFlowRequest;
import com.example.appdocsach.model.DialogFlowResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DialogFlowService {
    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer AIzaSyD08BNFPgoObwGSHmalUhkAuagg9afUVgY"
    })
    @POST("v2/projects/{projectId}/agent/sessions/{sessionId}:detectIntent")
    Call<DialogFlowResponse> detectIntent(@Body DialogFlowRequest request,
                                          @Path("projectId") String projectId,
                                          @Path("sessionId") String sessionId);
}

package com.fourcpplus.survey.interfaces;

import com.fourcpplus.survey.model.network.PlotRecheckResponse;
import com.fourcpplus.survey.model.network.ResponseModel;
import com.fourcpplus.survey.model.network.SuccessResponse;
import com.fourcpplus.survey.utils.AppConstants;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

/**
 * Created by Rohit on 24-03-2020.
 */
public interface ApiCall {

    @Multipart
    @POST(AppConstants.surveyDataUploadURL)
    Call<ResponseModel> surveyDataUpload(@PartMap Map<String, RequestBody> options, @Part MultipartBody.Part file);

    @POST(AppConstants.checkOverlap)
    Call<SuccessResponse> checkOverlap(@QueryMap Map<String,String> options);

   @POST(AppConstants.checkDamru)
    Call<SuccessResponse> checkDamru(@QueryMap Map<String, String> options);

   @POST(AppConstants.plotRecheck)
    Call<PlotRecheckResponse> plotRecheck(@QueryMap Map<String,String> options);

}

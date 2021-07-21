package com.vms.app.network;



import com.vms.app.model.response.BaseResponse;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiInterface {

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST( )
    Call<BaseResponse> producPlaceOrder(@Field("user_id") String user_id ,
                                        @Field("grand_total") String prod_id );

}

package com.myscrap.mirzaadiltask.contoller.interfaces;

import com.myscrap.mirzaadiltask.model.LocationDataModel;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;


/**
 * Created by Mirza Adil on 6/27/2018.
 * <p>
 * This class contains the API Interface of the API.
 * All of the attributes in this class shall be static. So, that they can be used from anywhere
 * without even declaring the object of this class.
 */

public interface APIInterface {

    @FormUrlEncoded
    @POST
    Observable<Response<LocationDataModel>> fetchLocationData(@Url String url, @Field("apiKey") String apiKey, @Field("userId") String userId);
}

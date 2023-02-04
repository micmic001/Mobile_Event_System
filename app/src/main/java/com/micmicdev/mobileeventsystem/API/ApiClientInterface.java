package com.micmicdev.mobileeventsystem.API;

import com.micmicdev.mobileeventsystem.STR.strEventDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiClientInterface {
    @FormUrlEncoded
    @POST("/insertViewerData")
    Call<strEventDetails> insertViewerDataField(
            @Field("seat_no") String seat_no,
            @Field("tix_no") String tix_no,
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("vaxCard") String vaxCard,
            @Field("id") String id,
            @Field("device") String device,
            @Field("addedBy") String addedBy
    );

    @POST("/insertViewerData")
    Call<strEventDetails> insertViewerData(@Body strEventDetails postViewerDetail);

    @POST("/insertProcessStatus")
    Call<strEventDetails> updateSeatStatus(@Body strEventDetails postSeatStatus);

    @POST("/getTicketData")
    Call<strEventDetails> getTicketData(@Body strEventDetails getTicketDatas);

    @POST("/insertAttendanceData")
    Call<strEventDetails> setViewerAttendance(@Body strEventDetails setAttendanceData);

    @POST("/getAttendanceList")
    Call<List<strEventDetails>> getViewerAttendanceList(@Body strEventDetails getAttendanceData);
}

package com.doozycod.fleetoptics.Service;

import com.doozycod.fleetoptics.Model.AppointmentResultModel;
import com.doozycod.fleetoptics.Model.GetCurrentVisitors;
import com.doozycod.fleetoptics.Model.GetEmployeeModel;
import com.doozycod.fleetoptics.Model.ResultModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("visitor/setAppointment.php")
    @FormUrlEncoded
    Call<AppointmentResultModel> appointment(
            @Field("checkin_type") String checkin_type,
            @Field("purpose_of_visit") String purpose_of_visit,
            @Field("full_name") String full_name,
            @Field("company_name") String company_name,
            @Field("email_address") String email_address,
            @Field("phone_no") String phone_no,
            @Field("timestamp") String timestamp,
            @Field("image") String image,
            @Field("meet_to_whom") String meet_to_whom);

    @GET("employee/getAllEmployees.php")
    Call<GetEmployeeModel> getAllEmployees();

    @GET("visitor/getCurrentVisitors.php")
    Call<GetCurrentVisitors> getCurrentVisitors();

    @POST("visitor/signOut.php")
    @FormUrlEncoded
    Call<ResultModel> signOutVisitor(
            @Field("email_address") String email_address,
            @Field("timestamp") String timestamp);

    @POST("package/setPackageDelivery.php")
    @FormUrlEncoded
    Call<ResultModel> packageDelivery(
            @Field("checkin_type") String checkin_type,
            @Field("deliver_to_whom") String deliver_to_whom,
            @Field("is_sig_required") String is_sig_required,
            @Field("is_spec_person") String is_spec_person);

}

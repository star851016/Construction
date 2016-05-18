package temp.chiu.com.clinico.network;


import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import temp.chiu.com.clinico.model.ConstructionListResponse;
import temp.chiu.com.clinico.model.Welcome;
import temp.chiu.com.clinico.model.WelcomeResponse;

public interface BackendQuery {


    @POST("/auth/login")
    WelcomeResponse login(@Body Welcome welcome);

    @GET("/apiAccess?scope=resourceAquire&rid=201d8ae8-dffc-4d17-ae1f-e58d8a95b162")
    ConstructionListResponse getConstructionList();



}

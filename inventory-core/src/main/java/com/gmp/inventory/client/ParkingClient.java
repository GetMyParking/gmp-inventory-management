package com.gmp.inventory.client;

import com.gmp.parking.entities.Parking;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.gmp.entities.request.RequestHeaders.HEADER_GMP_TENANT;

/**
 * Retrofit client for Parking Service
 * 
 * @author Mrigank Tandon
 */
public interface ParkingClient {

    @GET("/v1/parking/{parkingId}")
    Call<Parking> getParkingById(@Header(HEADER_GMP_TENANT) String tenant,
                                 @Path("parkingId") Integer parkingId);

    @GET("/v1/parking/parkingList")
    Call<Map<Integer, Parking>> getParkingDataList(@Header(HEADER_GMP_TENANT) String tenant,
                                                    @Query("parkingIdList") Set<Integer> parkingIdList);

    @GET("/v1/parking/parkingIdByCompany/{companyId}")
    Call<List<Integer>> getParkingIdsByCompanyId(@Header(HEADER_GMP_TENANT) String tenant,
                                                 @Path("companyId") Integer companyId);
}

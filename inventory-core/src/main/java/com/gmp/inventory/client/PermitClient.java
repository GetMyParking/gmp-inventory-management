package com.gmp.inventory.client;

import com.gmp.inventory.constants.ApiPaths;
import com.gmp.permit.api.response.PermitMasterAllocationResponse;
import retrofit2.Call;
import retrofit2.http.*;

import static com.gmp.entities.request.RequestHeaders.HEADER_GMP_TENANT;

/**
 * Retrofit client for Permit Service
 * 
 * @author Mrigank Tandon
 */
public interface PermitClient {

    @GET(ApiPaths.PERMIT_ALLOCATION_BY_ID)
    Call<PermitMasterAllocationResponse> getPermitAllocationById(@Header(HEADER_GMP_TENANT) String tenant,
                                                                  @Path("allocationId") Long allocationId);
}

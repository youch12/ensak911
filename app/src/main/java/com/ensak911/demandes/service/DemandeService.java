package com.ensak911.demandes.service;

import com.ensak911.demandes.models.DemandsList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by hp on 1/8/2018.
 */
public interface DemandeService {
    @GET("demandes")
    Call<DemandsList> getDemandes();
}
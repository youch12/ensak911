package com.ensak911.emplois_annonces.services;

import com.ensak911.emplois_annonces.models.EmploisList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by hp on 1/8/2018.
 */
public interface EmploiService {
    @GET("emplois")
    Call<EmploisList> getEmplois();
}

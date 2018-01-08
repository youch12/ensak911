package com.ensak911.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hp on 1/8/2018.
 */
public class RetrofitInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://demo1919965.mockable.io/ensak911/";

    public static Retrofit getRetrofitInstance() {
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        Gson gson = builder.create();
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory( GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
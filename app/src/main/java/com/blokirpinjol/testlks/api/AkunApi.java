package com.blokirpinjol.testlks.api;

import com.blokirpinjol.testlks.model.Akun;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AkunApi {
    @POST("login")
    Observable<Akun> login(
            @Query("username") String name,
            @Query("password") String description
    );

    @POST("register")
    Observable<Akun> register(
            @Query("username") String name,
            @Query("password") String description
    );
}

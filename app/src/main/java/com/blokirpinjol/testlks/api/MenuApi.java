package com.blokirpinjol.testlks.api;

import com.blokirpinjol.testlks.model.Menu;

import java.util.List;

import io.reactivex.Observable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MenuApi {
    @GET("menu")
    Observable<List<Menu>> getMenu();

    @POST("menu")
    Observable<Menu> saveMenu(
            @Query("name") String name,
            @Query("description") String description,
            @Query("price") Integer price
    );

    @POST("menu/{id}")
    Observable<Menu> updateMenu(
            @Path("id") String id,
            @Query("name") String name,
            @Query("description") String description,
            @Query("price") Integer price
    );

    @POST("delete/menu/{id}")
    Observable<Integer> deleteMenu(
            @Path("id") String id
    );
}

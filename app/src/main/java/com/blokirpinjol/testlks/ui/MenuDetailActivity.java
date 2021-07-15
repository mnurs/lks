package com.blokirpinjol.testlks.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.blokirpinjol.testlks.R;
import com.blokirpinjol.testlks.adapter.MenuAdapter;
import com.blokirpinjol.testlks.api.MenuApi;
import com.blokirpinjol.testlks.model.Menu;
import com.blokirpinjol.testlks.service.App;
import com.blokirpinjol.testlks.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MenuDetailActivity extends AppCompatActivity implements MenuAdapter.ItemClickListener{
    private static final String TAG = "MenuDetailActivity";
    private List<Menu> menuList  = new ArrayList<>();;
    MenuAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        App.setmContext(this);
        getMenu();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MenuAksiActivity.class);
                startActivity(i);
            }
        });
        // set up the RecyclerView

    }

    public void setAdapter(){
        RecyclerView recyclerView = findViewById(R.id.rvMenu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MenuAdapter(this, menuList);
        adapter.setClickListener(this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View v, int position) {
//        Log.d(TAG,"MASUK");
//        switch(v.getId()){
//            case R.id.btnEdit: /** Start a new Activity MyCards.java */
//                Log.d(TAG,"MASUK1");
//                Bundle bundle = new Bundle();
//                bundle.putString("id", String.valueOf(adapter.getItem(position)));
//                Intent intent = new Intent(MenuDetailActivity.this, MenuAksiActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                break;
//            case R.id.btnDelete: /** Start a new Activity MyCards.java */
//                Log.d(TAG,"MASUK2");
//                deleteMenu(String.valueOf(adapter.getItem(position)));
//                break;
//        }
////        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
    public void getMenu(){
        /** Create handle for the RetrofitInstance interface*/
        MenuApi service = RetrofitInstance.getRetrofitInstance().create(MenuApi.class);
        Observable<List<Menu>> listObservable = service.getMenu();
        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Menu>>() {
                    @Override
                    public void onNext(List<Menu> menus) {
                        try {
                            Log.d(TAG,"Masuk");
                            menuList = menus;
                            setAdapter();
                            Toast.makeText(getApplicationContext(),"Data Berhasil",Toast.LENGTH_LONG);
                        }catch (Exception e){
                            Log.d(TAG,e.getMessage());
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                        Log.d(TAG,"Selesai");
                    }
                });
    }

    public void deleteMenu(String id){
        /** Create handle for the RetrofitInstance interface*/
        MenuApi service = RetrofitInstance.getRetrofitInstance().create(MenuApi.class);
        Observable<Integer> listObservable = service.deleteMenu(id);
        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer data) {
                        try {
                            Log.d(TAG,"Masuk");
                            Toast.makeText(getApplicationContext(),"Data Berhasil",Toast.LENGTH_LONG);
                        }catch (Exception e){
//                            Log.d(TAG,e.getMessage());
//                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                        Log.d(TAG,"Selesai");
                    }
                });
 
    }
}

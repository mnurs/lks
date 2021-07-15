package com.blokirpinjol.testlks.ui;

import com.blokirpinjol.testlks.R;
import com.blokirpinjol.testlks.api.MenuApi;
import com.blokirpinjol.testlks.model.Menu;
import com.blokirpinjol.testlks.service.App;
import com.blokirpinjol.testlks.service.RetrofitInstance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MenuAksiActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MenuAksiActivity";
    private Button btnAdd;
    private EditText txtName,txtDesc,txtPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.blokirpinjol.testlks.R.layout.activity_menu_add);
        App.setmContext(this);
        btnAdd = (Button)findViewById(com.blokirpinjol.testlks.R.id.btnAdd);
        txtName = (EditText)findViewById(com.blokirpinjol.testlks.R.id.txtName);
        txtDesc = (EditText)findViewById(com.blokirpinjol.testlks.R.id.txtDeskripsi);
        txtPrice = (EditText)findViewById(com.blokirpinjol.testlks.R.id.txtPrice);
        if(getIntent().getExtras()!=null) {
            Bundle bundle = getIntent().getExtras();
            txtName.setText(bundle.getString("name"));
            txtDesc.setText(bundle.getString("desc"));
            txtPrice.setText(bundle.getString("price"));
        }
            btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnAdd: /** Start a new Activity MyCards.java */
                if(getIntent().getExtras()!=null){
                    Bundle bundle = getIntent().getExtras();
                    AksiMenu(bundle.getString("id"),txtName.getText().toString(),txtDesc.getText().toString(), Integer.parseInt(txtPrice.getText().toString()));
                }else{
                    AksiMenu(null,txtName.getText().toString(),txtDesc.getText().toString(), Integer.parseInt(txtPrice.getText().toString()));
                }
                break;
        }
    }

    public void AksiMenu(String id,String name,String desc, Integer price){
        /** Create handle for the RetrofitInstance interface*/
        Log.d(TAG,"MASUK");
        MenuApi service = RetrofitInstance.getRetrofitInstance().create(MenuApi.class);
        Observable<Menu> listObservable;
        if(id == null){
            listObservable = service.saveMenu(name,desc,price);
        }else{
            listObservable = service.updateMenu(id,name,desc,price);
        }
        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Menu>() {
                    @Override
                    public void onNext(Menu data) {
                        try {
                            Log.d(TAG,"Masuk");
                            Toast.makeText(getApplicationContext(),"Data Berhasil",Toast.LENGTH_LONG);
                            Intent i = new Intent(getApplicationContext(),MenuDetailActivity.class);
                            startActivity(i);
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
}

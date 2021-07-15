package com.blokirpinjol.testlks.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blokirpinjol.testlks.R;
import com.blokirpinjol.testlks.api.AkunApi;
import com.blokirpinjol.testlks.model.Akun;
import com.blokirpinjol.testlks.service.App;
import com.blokirpinjol.testlks.service.RetrofitInstance;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin,btnRegis;
    private EditText txtUsername,txtPassword;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.setmContext(this);
        setContentView(com.blokirpinjol.testlks.R.layout.activity_main);
        btnLogin = (Button)findViewById(com.blokirpinjol.testlks.R.id.btnLogin);
        btnRegis = (Button)findViewById(com.blokirpinjol.testlks.R.id.btnAdd);
        txtUsername = (EditText)findViewById(com.blokirpinjol.testlks.R.id.txtName);
        txtPassword = (EditText)findViewById(com.blokirpinjol.testlks.R.id.txtDeskripsi);
        btnLogin.setOnClickListener(this);
        btnRegis.setOnClickListener(this);
    }

    public void login(String username,String password){
        /** Create handle for the RetrofitInstance interface*/
        AkunApi service = RetrofitInstance.getRetrofitInstance().create(AkunApi.class);
        Observable<Akun> listObservable = service.login(username,password);
        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Akun>() {
                    @Override
                    public void onNext(Akun akun) {
                        try {
                            Log.d(TAG,"Masuk");
                            Toast.makeText(getApplicationContext(),"Berhasil Login",Toast.LENGTH_LONG);
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnLogin: /** Start a new Activity MyCards.java */
            if(txtUsername.getText().length() <= 0){
                Toast.makeText(getApplicationContext(),"Harap isi username",Toast.LENGTH_LONG);
            }else if(txtPassword.getText().length() <= 0){
                Toast.makeText(getApplicationContext(),"Harap isi password",Toast.LENGTH_LONG);
            }else{
                login(txtUsername.getText().toString(),txtPassword.getText().toString());
            }
                break;
            case R.id.btnAdd: /** AlerDialog when click on Exit */
                Intent i = new Intent(getApplicationContext(),RegistrasiActivity.class);
                startActivity(i);
                break;
        }
    }
}

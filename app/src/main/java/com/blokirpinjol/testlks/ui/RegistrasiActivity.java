package com.blokirpinjol.testlks.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class RegistrasiActivity extends AppCompatActivity  implements View.OnClickListener{
    private Button btnRegis;
    private EditText txtUsername,txtPassword,txtPasswordConfirm;
    private static final String TAG = "RegistrasiActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        App.setmContext(this);
        btnRegis = (Button)findViewById(R.id.btnRegistrasi);
        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtPasswordConfirm = (EditText)findViewById(R.id.txtPasswordConfirm);
        btnRegis.setOnClickListener(this);
    }

    public void regis(String username,String password){
        /** Create handle for the RetrofitInstance interface*/
        AkunApi service = RetrofitInstance.getRetrofitInstance().create(AkunApi.class);
        Observable<Akun> listObservable = service.register(username,password);
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
            case R.id.btnRegistrasi: /** Start a new Activity MyCards.java */
                if(txtUsername.getText().length() <= 0){
                    Log.d(TAG,"MASUK1");
                    Toast.makeText(getApplicationContext(),"Harap isi username",Toast.LENGTH_LONG);
                }else if(txtPassword.getText().length() <= 0){
                    Log.d(TAG,"MASUK2");
                    Toast.makeText(getApplicationContext(),"Harap isi password",Toast.LENGTH_LONG);
                }else if(!txtPassword.getText().toString().equals(txtPasswordConfirm.getText().toString())){
                    Log.d(TAG,"MASUK3");
                    Toast.makeText(getApplicationContext(),"Confirmasi password tidak sama",Toast.LENGTH_LONG);
                }else{
                    Log.d(TAG,"MASUK4");
                    regis(txtUsername.getText().toString(),txtPassword.getText().toString());
                }
                break;
        }
    }
}

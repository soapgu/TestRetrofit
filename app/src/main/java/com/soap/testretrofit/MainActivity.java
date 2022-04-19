package com.soap.testretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private final CompositeDisposable disposables = new CompositeDisposable();

    private Button button;
    private TextView msg;
    private Api api;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.test_btn);
        msg = findViewById(R.id.msg_tv);
        api = this.provideRetrofit().create( Api.class );
        button.setOnClickListener( v -> disposables.add( api.collectSpaces( "bear 123456789xyz", "1" )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( ()-> msg.setText( "request success" ), e-> {
                    String error = e.getClass().toString();
                    msg.setText( error );
                } )));
    }

    private Retrofit provideRetrofit(){
        return new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:4523/mock/666121/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.disposables.dispose();
    }
}
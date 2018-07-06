package com.mygit.photoandcamera;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import com.mygit.photoandcamera.models.ModelBase;
import com.mygit.photoandcamera.network.sevices.UserService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //请求网络接口，根据实际情况编写
        UserService.getInstance().smsSend(this, "电话号码", smsCodeCallback);

    }

    private ModelBase.OnResult smsCodeCallback = new ModelBase.OnResult() {
        @Override
        public void OnListener(ModelBase model) {

        }
    };
}

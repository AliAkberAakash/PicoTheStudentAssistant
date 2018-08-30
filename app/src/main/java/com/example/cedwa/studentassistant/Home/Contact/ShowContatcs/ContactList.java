package com.example.cedwa.studentassistant.Home.Contact.ShowContatcs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cedwa.studentassistant.R;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.Objects;

public class ContactList extends AppCompatActivity {

    public static final String EXTRA_STRING="extra string";
    public static final String OPERATION_TYPE1 = "teachers";
    public static final String OPERATION_TYPE2 = "classmates";
    public static final String OPERATION_TYPE3 = "others";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Intent intent = getIntent();
        String operationType = Objects.requireNonNull(intent.getExtras()).getString(EXTRA_STRING);

        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.d("Operation type is : "+ operationType);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

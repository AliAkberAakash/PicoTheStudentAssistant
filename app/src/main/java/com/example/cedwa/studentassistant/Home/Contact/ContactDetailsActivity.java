package com.example.cedwa.studentassistant.Home.Contact;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cedwa.studentassistant.R;

import java.net.URI;

public class ContactDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    Button textButton;
    Button callButton;
    TextView numberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        callButton = findViewById(R.id.call_button);
        textButton = findViewById(R.id.text_button);
        numberText = findViewById(R.id.contact_phone_textView);

        callButton.setOnClickListener(this);
        textButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == callButton)
        {
            String phone = numberText.getText().toString().trim();
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone)));
        }
        else if(view == textButton)
        {
            //todo
        }
    }
}

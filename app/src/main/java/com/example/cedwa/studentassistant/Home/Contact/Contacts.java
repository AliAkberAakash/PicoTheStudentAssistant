package com.example.cedwa.studentassistant.Home.Contact;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cedwa.studentassistant.R;


public class Contacts extends Fragment implements View.OnClickListener{

    Button contacts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        contacts=view.findViewById(R.id.contacts_button);
        contacts.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {

        startActivity(new Intent(getActivity(), ContactDetailsActivity.class));
    }
}

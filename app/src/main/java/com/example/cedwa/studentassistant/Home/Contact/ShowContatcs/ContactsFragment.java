package com.example.cedwa.studentassistant.Home.Contact.ShowContatcs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.cedwa.studentassistant.R;

public class ContactsFragment extends Fragment implements View.OnClickListener{

    TextView teachersTextView;
    TextView classmatesTextView;
    TextView othersTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        teachersTextView = view.findViewById(R.id.teachersTextView);
        classmatesTextView = view.findViewById(R.id.classmatesTextView);
        othersTextView = view.findViewById(R.id.othersTextView);

        teachersTextView.setOnClickListener(this);
        classmatesTextView.setOnClickListener(this);
        othersTextView.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View view) {

        if(view==teachersTextView)
        {
            Intent intent = new Intent(getActivity(), ContactList.class);
            intent.putExtra(ContactList.EXTRA_STRING,ContactList.OPERATION_TYPE1);
            startActivity(intent);
        }
        else if(view == classmatesTextView)
        {
            Intent intent = new Intent(getActivity(), ContactList.class);
            intent.putExtra(ContactList.EXTRA_STRING,ContactList.OPERATION_TYPE2);
            startActivity(intent);
        }
        else if(view == othersTextView)
        {
            Intent intent = new Intent(getActivity(), ContactList.class);
            intent.putExtra(ContactList.EXTRA_STRING,ContactList.OPERATION_TYPE3);
            startActivity(intent);
        }

    }
}

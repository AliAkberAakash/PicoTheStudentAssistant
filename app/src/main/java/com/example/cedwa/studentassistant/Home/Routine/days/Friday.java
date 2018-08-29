package com.example.cedwa.studentassistant.Home.Routine.days;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cedwa.studentassistant.R;

public class Friday extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.common_background_with_fab_layout, container, false);

        TextView textView = result.findViewById(R.id.day);
        textView.setText(getString(R.string.friday));

        return result;

    }


}

package com.example.cedwa.studentassistant.Home.Routine.days;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cedwa.studentassistant.R;


public class Sunday extends Saturday {

    private TextView textView;
    RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.common_background_with_fab_layout, container, false);

        TextView textView = result.findViewById(R.id.day);
        textView.setText(getString(R.string.sunday));
        recyclerView=result.findViewById(R.id.recyclerView);
        floatingActionButton=result.findViewById(R.id.fab);

        //floatingActionButton.setOnClickListener(this);

        super.updatePage(recyclerView);

        return result;
    }

}

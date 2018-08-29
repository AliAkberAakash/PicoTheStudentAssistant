package com.example.cedwa.studentassistant.Home.Routine.days;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cedwa.studentassistant.DatabaseFiles.RoutineOperations.RoutineQuery;
import com.example.cedwa.studentassistant.DatabaseFiles.RoutineOperations.RoutineStructure;
import com.example.cedwa.studentassistant.Home.Routine.Functionalities.CreateOrEditRoutineDialog;
import com.example.cedwa.studentassistant.Home.Routine.Functionalities.TalkToDayFragmentListener;
import com.example.cedwa.studentassistant.Home.Routine.RoutineAdapter;
import com.example.cedwa.studentassistant.R;

import java.util.List;


public class Saturday extends Fragment implements View.OnClickListener, CreateOrEditRoutineDialog.RoutineDialogListener, TalkToDayFragmentListener {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View result = inflater.inflate(R.layout.common_background_with_fab_layout, container, false);

         TextView textView = result.findViewById(R.id.day);
         textView.setText(getString(R.string.saturday));

         recyclerView = result.findViewById(R.id.recyclerView);
         FloatingActionButton floatingActionButton = result.findViewById(R.id.fab);
         floatingActionButton.setOnClickListener(this);

         updatePage(recyclerView);

         return result;
    }


    void updatePage(RecyclerView recyclerView)
    {

        RoutineAdapter routineAdapter;
        RoutineQuery routineQuery = new RoutineQuery(getActivity());
        List<RoutineStructure> routineStructureList = routineQuery.getAllRoutines();
        if(routineStructureList!=null)
        {
            routineAdapter = new RoutineAdapter(routineStructureList, this, getActivity());
            recyclerView.setAdapter(routineAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
        }

    }


    @Override
    public void onClick(View view) {
        CreateOrEditRoutineDialog createOrEditRoutineDialog = CreateOrEditRoutineDialog.getInstance("title", this);
        createOrEditRoutineDialog.setOperationType(-1);
        createOrEditRoutineDialog.show(getChildFragmentManager(), "Example Dialog");
    }

    @Override
    public void applyStrings(String subject, String lecturer, String startTime, int alarm) {
        RoutineQuery routineQuery = new RoutineQuery(getActivity());
        routineQuery.insertRoutine(new RoutineStructure(-1,startTime,subject,lecturer,alarm));
        updatePage(recyclerView);
    }

    @Override
    public void updateDatabase(int id,String subject, String lecturer, String startTime, int alarm) {
        RoutineQuery routineQuery = new RoutineQuery(getActivity());
        routineQuery.updateRoutine(new RoutineStructure(id,startTime,subject,lecturer,alarm));
        updatePage(recyclerView);
    }

    @Override
    public void deleted() {
        updatePage(recyclerView);
    }

    @Override
    public void edited(int id) {
        CreateOrEditRoutineDialog createOrEditRoutineDialog = CreateOrEditRoutineDialog.getInstance("title", this);
        createOrEditRoutineDialog.setOperationType(id);
        createOrEditRoutineDialog.show(getChildFragmentManager(), "Example Dialog");
    }
}

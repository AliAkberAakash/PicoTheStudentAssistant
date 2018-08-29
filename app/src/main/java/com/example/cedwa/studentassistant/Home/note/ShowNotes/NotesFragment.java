package com.example.cedwa.studentassistant.Home.note.ShowNotes;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cedwa.studentassistant.DatabaseFiles.NotesOperations.NotesQuery;
import com.example.cedwa.studentassistant.DatabaseFiles.NotesOperations.NotesStructure;
import com.example.cedwa.studentassistant.Home.MainActivity;
import com.example.cedwa.studentassistant.Home.note.CreateNotes.CreateNoteDialogFragmentListener;
import com.example.cedwa.studentassistant.Home.note.CreateNotes.CreateNotesDialogFragment;
import com.example.cedwa.studentassistant.R;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    TextView emptyPageTextView;
    TextView headLineTextView;
    RecyclerView recyclerView;

    //vars
    List<NotesStructure> notesStructureList;
    private NotesQuery notesQuery;
    NotesListRecyclerViewAdapter notesListRecyclerViewAdapter;

    public NotesFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_background_with_fab_layout, container, false);

        notesStructureList = new ArrayList<>();

        floatingActionButton=view.findViewById(R.id.fab);
        emptyPageTextView = view.findViewById(R.id.empty_page_textview_id);
        headLineTextView = view.findViewById(R.id.day);
        recyclerView = view.findViewById(R.id.recyclerView);

        headLineTextView.setText(R.string.notes_headline);
        notesQuery = new NotesQuery(getActivity());
        notesStructureList = notesQuery.getAllNotes();

        notesListRecyclerViewAdapter = new NotesListRecyclerViewAdapter(getActivity(), notesStructureList, new OnNoteDeletedListener() {
            @Override
            public void onNoteDeleted() {
                checkVisibility();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(notesListRecyclerViewAdapter);

        checkVisibility();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateNotesDialogFragment createNotesDialogFragment = CreateNotesDialogFragment.getInstance("title",new CreateNoteDialogFragmentListener() {
                    @Override
                    public void onNoteCreated(NotesStructure notesStructure) {
                        notesStructureList.add(notesStructure);
                        notesListRecyclerViewAdapter.notifyDataSetChanged();
                        checkVisibility();
                    }
                });

                createNotesDialogFragment.show(getChildFragmentManager(),"tag");

            }
        });

        return view;
    }

    public void checkVisibility()
    {
        if(notesStructureList.isEmpty())
        {
            emptyPageTextView.setVisibility(View.VISIBLE);
            emptyPageTextView.setText(R.string.empty_page);
        }
        else
        {
            emptyPageTextView.setVisibility(View.GONE);
        }
    }

}

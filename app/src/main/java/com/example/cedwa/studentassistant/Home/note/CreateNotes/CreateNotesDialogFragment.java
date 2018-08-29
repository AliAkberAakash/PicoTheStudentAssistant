package com.example.cedwa.studentassistant.Home.note.CreateNotes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.cedwa.studentassistant.DatabaseFiles.NotesOperations.NotesQuery;
import com.example.cedwa.studentassistant.DatabaseFiles.NotesOperations.NotesStructure;
import com.example.cedwa.studentassistant.R;

public class CreateNotesDialogFragment extends AppCompatDialogFragment
{

    private static CreateNoteDialogFragmentListener createNoteDialogFragmentListener;
    private NotesQuery notesQuery;

    //widgets
    private EditText titleEditText;
    private EditText descriptionEditText;


    public CreateNotesDialogFragment()
    {

    }

    public static CreateNotesDialogFragment getInstance(String title,
                                    CreateNoteDialogFragmentListener createNoteDialogFragmentListenerPassed)
    {
        CreateNotesDialogFragment createNotesDialogFragment = new CreateNotesDialogFragment();
        createNoteDialogFragmentListener=createNoteDialogFragmentListenerPassed;
        Bundle args = new Bundle();
        args.putString("title", title);
        createNotesDialogFragment.setArguments(args);
        return createNotesDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.notes_dialog_fragment_layout,null);


        titleEditText=view.findViewById(R.id.notes_title_editText);
        descriptionEditText=view.findViewById(R.id.notes_description_editText);

        builder.setView(view)
                .setCancelable(false)
                .setMessage(R.string.create_notes_dialog_title)
                .setPositiveButton(R.string.save_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = titleEditText.getText().toString();
                        String description = descriptionEditText.getText().toString();

                        NotesStructure notesStructure = new NotesStructure(-1,title,description);

                        notesQuery = new NotesQuery(getActivity());
                        long id = notesQuery.insertNotes(notesStructure);

                        if(id>0)
                        {
                            notesStructure.setId(id);
                            createNoteDialogFragmentListener.onNoteCreated(notesStructure);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //cancel dialog
                        getDialog().dismiss();
                    }
                });


        return builder.create();
    }
}

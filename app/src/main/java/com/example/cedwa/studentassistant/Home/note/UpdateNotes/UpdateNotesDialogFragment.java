package com.example.cedwa.studentassistant.Home.note.UpdateNotes;

        import android.app.Dialog;
        import android.content.DialogInterface;
        import android.os.Bundle;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatDialogFragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.example.cedwa.studentassistant.DatabaseFiles.NotesOperations.NotesQuery;
        import com.example.cedwa.studentassistant.DatabaseFiles.NotesOperations.NotesStructure;
        import com.example.cedwa.studentassistant.R;
        import com.orhanobut.logger.AndroidLogAdapter;
        import com.orhanobut.logger.Logger;


public class UpdateNotesDialogFragment extends AppCompatDialogFragment {

    private static UpdateNotesDialogFragmentListener updateNotesDialogFragmentListener;
    private NotesQuery notesQuery;

    //widgets
    private EditText titleEditText;
    private EditText descriptionEditText;

    //vars
    private static long id;

    public UpdateNotesDialogFragment()
    {

    }

    public static UpdateNotesDialogFragment getInstance(long idPassed,
                                                        UpdateNotesDialogFragmentListener updateNotesDialogFragmentListenerPassed)
    {

        UpdateNotesDialogFragment updateNotesDialogFragment = new UpdateNotesDialogFragment();
        updateNotesDialogFragmentListener=updateNotesDialogFragmentListenerPassed;
        id=idPassed;

        /*Bundle args = new Bundle();
        args.putString("title", title);
        updateNotesDialogFragment.setArguments(args);
*/
        return updateNotesDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Logger.addLogAdapter(new AndroidLogAdapter());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.notes_dialog_fragment_layout,null);

        //to do
        titleEditText=view.findViewById(R.id.notes_title_editText);
        descriptionEditText=view.findViewById(R.id.notes_description_editText);

        notesQuery = new NotesQuery(getActivity());


        final NotesStructure notesStructure = notesQuery.getNotesById(id);


        if(notesStructure!=null)
        {
            titleEditText.setText(notesStructure.getNoteTitle());
            descriptionEditText.setText(notesStructure.getNoteDescription());
        }

        builder.setView(view)
                .setCancelable(false)
                .setMessage(R.string.update_notes_dialog_title)
                .setPositiveButton(R.string.update_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = titleEditText.getText().toString();
                        String description = descriptionEditText.getText().toString();

                        notesStructure.setNoteTitle(title);
                        notesStructure.setNoteDescription(description);

                        long newId = notesQuery.updateNotes(notesStructure);
                        //Logger.d("The id is: "+ newId);
                        if(newId>0)
                        {
                            updateNotesDialogFragmentListener.onNotesUpdated(notesStructure);
                            getDialog().dismiss();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getDialog().dismiss();
                    }
                });

        return builder.create();
    }

}

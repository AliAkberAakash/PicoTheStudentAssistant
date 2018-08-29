package com.example.cedwa.studentassistant.Home.note.ShowNotes;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cedwa.studentassistant.DatabaseFiles.NotesOperations.NotesQuery;
import com.example.cedwa.studentassistant.DatabaseFiles.NotesOperations.NotesStructure;
import com.example.cedwa.studentassistant.Home.MainActivity;
import com.example.cedwa.studentassistant.Home.note.UpdateNotes.UpdateNotesDialogFragment;
import com.example.cedwa.studentassistant.Home.note.UpdateNotes.UpdateNotesDialogFragmentListener;
import com.example.cedwa.studentassistant.R;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

public class NotesListRecyclerViewAdapter extends RecyclerView.Adapter<CustomNotesViewHolder> {

    private Context context;
    private List<NotesStructure> notesStructureList;
    private NotesQuery notesQuery;
    private OnNoteDeletedListener onNoteDeletedListener;

    public NotesListRecyclerViewAdapter(Context context,
                                        List<NotesStructure> notesStructureList,
                                        OnNoteDeletedListener onNoteDeletedListener) {
        this.context = context;
        this.onNoteDeletedListener=onNoteDeletedListener;
        this.notesStructureList = notesStructureList;
        notesQuery = new NotesQuery(context);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }


    @Override
    public CustomNotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_note_cardview_layout, parent, false);
        return new CustomNotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomNotesViewHolder holder, final int position) {

        final int itemPosition = position;
        final NotesStructure notesStructure = notesStructureList.get(position);


        holder.title.setText(notesStructure.getNoteTitle());
        holder.description.setText(notesStructure.getNoteDescription());
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Logger.d("Update button Clicked with id: "+ notesStructure.getId());

                UpdateNotesDialogFragment updateNotesDialogFragment = UpdateNotesDialogFragment
                        .getInstance(notesStructure.getId(), new UpdateNotesDialogFragmentListener() {
                            @Override
                            public void onNotesUpdated(NotesStructure notesStructurePassed) {
                                notesStructureList.set(position,notesStructurePassed);
                                notifyDataSetChanged();
                            }
                        });

                updateNotesDialogFragment.show(((MainActivity)context).getSupportFragmentManager(),"tag");
            }

        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Logger.d("Delete button Clicked");

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage(R.string.delete_dialog_message);
                alertDialogBuilder.setPositiveButton(R.string.yes_button,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteStudent(itemPosition);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        });

    }

    @Override
    public int getItemCount() {
        return notesStructureList.size();
    }

    public void deleteStudent(int position)
    {
        NotesStructure notesStructure = notesStructureList.get(position);

        long deleted = notesQuery.deleteSingleNote(notesStructure.getId());

        if(deleted>0)
        {
            notesStructureList.remove(position);
            notifyDataSetChanged();
            onNoteDeletedListener.onNoteDeleted();
            Toast.makeText(context, "Note has been deleted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //thi is a comment
            Toast.makeText(context, "Sorry something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }


}

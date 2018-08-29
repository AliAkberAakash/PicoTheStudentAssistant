package com.example.cedwa.studentassistant.Home.note.ShowNotes;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cedwa.studentassistant.R;

public class CustomNotesViewHolder extends ViewHolder {

    public TextView title;
    public TextView description;
    public Button editButton;
    public Button deleteButton;


    public CustomNotesViewHolder(View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title_text_view_id);
        description = itemView.findViewById(R.id.description_text_view_id);
        editButton = itemView.findViewById(R.id.edit_button_id);
        deleteButton = itemView.findViewById(R.id.delete_button_id);
    }
}

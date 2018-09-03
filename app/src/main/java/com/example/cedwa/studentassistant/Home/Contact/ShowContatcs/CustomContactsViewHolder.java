package com.example.cedwa.studentassistant.Home.Contact.ShowContatcs;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cedwa.studentassistant.R;

public class CustomContactsViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView textView;

    public CustomContactsViewHolder(View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.contactListImageView);
        textView = itemView.findViewById(R.id.contactListTextView);
    }
}

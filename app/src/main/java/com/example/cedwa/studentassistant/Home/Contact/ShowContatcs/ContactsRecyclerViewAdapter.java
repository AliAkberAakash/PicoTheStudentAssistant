package com.example.cedwa.studentassistant.Home.Contact.ShowContatcs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.cedwa.studentassistant.DatabaseFiles.ContactsOperations.ContactStructure;
import com.example.cedwa.studentassistant.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<CustomContactsViewHolder>{

    List<ContactStructure> contactStructureList;
    Context context;

    public ContactsRecyclerViewAdapter(Context context, List<ContactStructure> contactStructureList)
    {
        this.context=context;
        this.contactStructureList=contactStructureList;
    }

    @Override
    public CustomContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_contact_card_view, parent, false);
        return new CustomContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomContactsViewHolder holder, int position) {

        ContactStructure contactStructure = contactStructureList.get(position);
        holder.textView.setText(contactStructure.getName());
    }

    @Override
    public int getItemCount() {
        return contactStructureList.size();
    }

    //filter for search
    public void filterItems(List<ContactStructure> contactList)
    {
        contactStructureList = new ArrayList<>();
        contactStructureList.addAll(contactList);
        notifyDataSetChanged();
    }

    }

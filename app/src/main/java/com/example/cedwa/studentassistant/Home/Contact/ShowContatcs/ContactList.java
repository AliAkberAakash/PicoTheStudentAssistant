package com.example.cedwa.studentassistant.Home.Contact.ShowContatcs;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.cedwa.studentassistant.DatabaseFiles.ContactsOperations.ContactStructure;
import com.example.cedwa.studentassistant.DatabaseFiles.ContactsOperations.ContactsQuery;
import com.example.cedwa.studentassistant.R;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ContactList extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener{

    //static strings
    public static final String EXTRA_STRING="extra string";
    public static final String OPERATION_TYPE1 = "teachers";
    public static final String OPERATION_TYPE2 = "classmates";
    public static final String OPERATION_TYPE3 = "others";
    private static final int REQUEST_CODE_PICK_CONTACTS = 1;

    //widgets
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    //vars
    String operationType;
    private Uri uriContact;
    private String contactID;
    private ContactsQuery contactsQuery;
    private ContactsRecyclerViewAdapter contactsRecyclerViewAdapter;
    private List<ContactStructure> contactStructureList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Logger.addLogAdapter(new AndroidLogAdapter());

        Intent intent = getIntent();
        operationType = Objects.requireNonNull(intent.getExtras()).getString(EXTRA_STRING);

        recyclerView = findViewById(R.id.contactsListRecyclerView);
        floatingActionButton = findViewById(R.id.fab);
        toolbar = findViewById(R.id.toolbar_search);

        setSupportActionBar(toolbar);

        contactsQuery = new ContactsQuery(this);
        contactStructureList.addAll(contactsQuery.getAllContacts(operationType));
        floatingActionButton.setOnClickListener(this);

        sortList();
        contactsRecyclerViewAdapter = new ContactsRecyclerViewAdapter(this,
                contactStructureList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contactsRecyclerViewAdapter);
    }


    @Override
    public void onClick(View view) {
        // using native contacts selection
        // Intent.ACTION_PICK = Pick an item from the data, returning what was selected.
        startActivityForResult(new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
        //Toast.makeText(this, "Fab clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
            uriContact = data.getData();

            ContactStructure contactStructure = retrieveContact();
            contactsQuery.insertContact(contactStructure);
            contactStructureList.add(contactStructure);
            sortList();
            contactsRecyclerViewAdapter.notifyDataSetChanged();
            Logger.d("RecyclerViewUpdated");
        }
    }

    private ContactStructure retrieveContact() {

        String contactNumber = null;
        String contactEmail = null;

        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID != null) {
            if (cursorID.moveToFirst()) {

                contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
            }


            cursorID.close();
        }

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
                new String[]{contactID},
                null);

        if (cursorPhone != null)
        {
            if (cursorPhone.moveToFirst()) {
                contactNumber = cursorPhone.getString(cursorPhone
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
        cursorPhone.close();
         }

         //find email
        Cursor cursorEmail = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Email.ADDRESS},

                ContactsContract.CommonDataKinds.Email.ADDRESS+ " = ? AND " +
                        ContactsContract.CommonDataKinds.Email.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Email.TYPE_MOBILE,
                new String[]{contactID},
                null);

        if (cursorEmail != null)
        {
            if (cursorEmail.moveToFirst()) {
                contactEmail = cursorEmail.getString(cursorEmail
                        .getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
            }
            cursorEmail.close();
        }

        Logger.d(contactEmail+" "+contactNumber);

        String name = retrieveContactName();

        return  new ContactStructure(-1,null,name, contactNumber, contactEmail, operationType);
    }


    private String retrieveContactName() {

        String contactName = null;

        // querying contact data store
        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);

        if(cursor!=null)
        {
            if (cursor.moveToFirst()) {

                // DISPLAY_NAME = The display name for the contact.
                // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            }
            cursor.close();
        }

        return  contactName;
    }

    public void sortList()
    {
        Collections.sort(contactStructureList, new Comparator<ContactStructure>() {
            @Override
            public int compare(ContactStructure t1, ContactStructure t2) {
                return t1.getName().compareTo(t2.getName());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String searchString = newText.toLowerCase();
        List<ContactStructure> newList = new ArrayList<>();

        for(ContactStructure contactStructure : contactStructureList)
        {
            if(contactStructure.getName().toLowerCase().contains(searchString))
            {
                newList.add(contactStructure);
            }
        }

        contactsRecyclerViewAdapter.filterItems(newList);

        return true;
    }
}



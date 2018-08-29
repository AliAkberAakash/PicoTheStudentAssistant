package com.example.cedwa.studentassistant.Home.Routine;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cedwa.studentassistant.DatabaseFiles.RoutineOperations.RoutineQuery;
import com.example.cedwa.studentassistant.DatabaseFiles.RoutineOperations.RoutineStructure;
import com.example.cedwa.studentassistant.Home.Routine.Functionalities.CreateOrEditRoutineDialog;
import com.example.cedwa.studentassistant.Home.Routine.Functionalities.TalkToDayFragmentListener;
import com.example.cedwa.studentassistant.R;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.ViewHolder>{

    private List<RoutineStructure> routineStructures;
    TalkToDayFragmentListener talkToDayFragmentListener;
    Context context;

    private TextView startTime;
    private TextView subject;
    private TextView lecturer;
    private Button deleteButton;
    private Button editButton;

    public RoutineAdapter(List<RoutineStructure> routineStructures, TalkToDayFragmentListener talkToDayFragmentListener, Context context)
    {
        this.context=context;
        this.routineStructures=routineStructures;
        this.talkToDayFragmentListener = talkToDayFragmentListener;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public RoutineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_routine_card,parent,false);

        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(RoutineAdapter.ViewHolder holder, final int position) {

        final CardView cardView = holder.cardView;
        startTime= cardView.findViewById(R.id.start_time_text);
        subject=cardView.findViewById(R.id.subject_text_id);
        lecturer=cardView.findViewById(R.id.lecturer_text_id);
        deleteButton=cardView.findViewById(R.id.delete_button_id);
        editButton=cardView.findViewById(R.id.edit_button_id);

        deleteButton.setOnClickListener(new CardView.OnClickListener() {
            @Override
            public void onClick(View view) {

                Logger.d("Delete button clicked on routine");

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(R.string.delete_dialog_message)
                        .setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RoutineQuery routineQuery = new RoutineQuery(context);
                                routineQuery.deleteSingleRoutine(routineStructures.get(position).getId());
                                Toast.makeText(context, "The entry has been deleted", Toast.LENGTH_SHORT).show();
                                talkToDayFragmentListener.deleted();
                            }
                        })
                        .setNegativeButton(R.string.no_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.d("Edit button clicked on routine");
                talkToDayFragmentListener.edited(routineStructures.get(position).getId());
            }
        });

        startTime.setText(routineStructures.get(position).getStart());
        subject.setText(routineStructures.get(position).getSubject());
        lecturer.setText(routineStructures.get(position).getTeacher());
    }

    @Override
    public int getItemCount() {
        return routineStructures.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

}

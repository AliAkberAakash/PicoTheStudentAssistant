package com.example.cedwa.studentassistant.Home.Routine.Functionalities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.cedwa.studentassistant.R;

public class CreateOrEditRoutineDialog extends AppCompatDialogFragment implements View.OnClickListener{

    private static RoutineDialogListener routineListener;
    private CheckBox checkBox;
    private TimePicker timePicker;
    private TextView selectTime;
    private Button okButton;
    private EditText subjectText;
    private EditText lecturerText;
    private int operationType;

    public CreateOrEditRoutineDialog()
    {

    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public static CreateOrEditRoutineDialog getInstance(String title, RoutineDialogListener listener)
    {
        routineListener = listener;
        CreateOrEditRoutineDialog createOrEditRoutineDialog = new CreateOrEditRoutineDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        createOrEditRoutineDialog.setArguments(args);

        return createOrEditRoutineDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.routine_dialog_layout,null);

        checkBox=view.findViewById(R.id.check_box_routine_dialog);
        timePicker=view.findViewById(R.id.routine_alarm_picker);
        selectTime = view.findViewById(R.id.start_time_selector);
        okButton = view.findViewById(R.id.time_selected_button);
        subjectText=view.findViewById(R.id.subject_text_id);
        lecturerText=view.findViewById(R.id.lecturer_text_id);

        okButton.setOnClickListener(this);
        selectTime.setOnClickListener(this);

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResourcesToActivity();
                    }
                });

        return builder.create();
    }


    public int setAlarmValue(boolean x)
    {
        if(x)
            return 1;
        return 0;
    }


    public boolean nullItemsInDialog(String subject, String lecturer)
    {
        if(subject.length()==0)
            return true;
        else if(lecturer.length()==0)
            return true;

        return false;
    }

    public void sendResourcesToActivity()
    {
        String subject,lecturer,startTime;
        int alarm;

            subject = subjectText.getText().toString().trim();
            lecturer = lecturerText.getText().toString().trim();
            startTime = selectTime.getText().toString().trim();
            alarm = setAlarmValue(checkBox.isChecked());

        if(nullItemsInDialog(subject,lecturer))
        {
            Toast.makeText(getActivity(), getString(R.string.null_item_check), Toast.LENGTH_SHORT).show();
            return;
        }

        if(operationType==-1)
        routineListener.applyStrings(subject,lecturer,startTime,alarm);
        else
            routineListener.updateDatabase(operationType,subject,lecturer,startTime,alarm);


    }

    public String getTime(int hour, int minute)
    {
        String format="AM";
        if(hour>12)
        {
            hour-=12;
            format="PM";
        }

        return  ""+hour+":"+minute+" "+format;
    }

    @Override
    public void onClick(View view) {
        if(view == selectTime)
        {
            timePicker.setVisibility(View.VISIBLE);
            okButton.setVisibility(View.VISIBLE);
        }
        else if(view == okButton)
        {
            String currentTime;

            if(Build.VERSION.SDK_INT >= 23)
            {
                currentTime=getTime(timePicker.getHour(),timePicker.getMinute());
            }
            else
            {
                currentTime=getTime(timePicker.getCurrentHour(),timePicker.getCurrentMinute());
            }

            selectTime.setText(currentTime);
            timePicker.setVisibility(View.GONE);
            okButton.setVisibility(View.GONE);
        }
    }

    public interface RoutineDialogListener
    {
        void applyStrings(String Subject, String lecturer, String startTime, int alarm);
        void updateDatabase(int id,String Subject, String lecturer, String startTime, int alarm);
    }
}

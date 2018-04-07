package com.example.an.exercise5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.an.exercise5.models.Task;

import java.util.UUID;

import io.realm.Realm;

public class TaskDFragment extends DialogFragment {
    public static EditText edtTask;
    public static CheckBox cbHigh, cbLow, cbNormal;
    static String nameOfTask = "";
    DatePicker datePicker;
    static String priorityOfTask = "";
    AlertDialog.Builder dialog;
    private Realm realm;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new AlertDialog.Builder(getActivity());
        realm = Realm.getDefaultInstance();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View mview = inflater.inflate(R.layout.task_add_fragment,null);
        dialog.setView(mview);
        edtTask = (EditText) mview.findViewById(R.id.etInputTask);


        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Task task = realm.createObject(Task.class, UUID.randomUUID().toString());
                        task.setName(String.valueOf(edtTask.getText()));
                        task.setTimestamp(System.currentTimeMillis());

//                        if (cbHigh.isChecked()){
//                            priorityOfTask = "High";
//                        }
//                        else if (cbLow.isChecked()){
//                            priorityOfTask = "Low";
//                        }
//                        else if (cbNormal.isChecked()){
//                            priorityOfTask = "Normal";
//                        }
//                        String day = String.valueOf(datePicker.getDayOfMonth());
//                        String month = String.valueOf(datePicker.getMonth()+1);
//                        String year = String.valueOf(datePicker.getYear());
//
//                        task.setPriority(priorityOfTask);
//                        task.setDate(day+"/"+month+"/"+year);
                    }
                });
            }
        });
        dialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return dialog.create();
    }
}

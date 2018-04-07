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

import io.realm.Realm;

public class TaskDFragmentEdit extends DialogFragment {
    public static EditText edtTask;
    public static CheckBox cbHigh, cbLow, cbNormal;
    static String nameOfTask = "";
    DatePicker datePicker;
    static String priorityOfTask = "";
    AlertDialog.Builder dialog;
    private Realm realm;
    String ID;
    private Task task;
    public void setValue(String IDs) {
        this.ID = IDs;
    }
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
                        Task task = realm.where(Task.class).equalTo("id",ID).findFirst();
                        task.setName(String.valueOf(String.valueOf(edtTask.getText())));

                    }
                });

            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return dialog.create();
    }
}

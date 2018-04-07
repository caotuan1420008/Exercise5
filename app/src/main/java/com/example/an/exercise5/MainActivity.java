package com.example.an.exercise5;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.an.exercise5.models.Task;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    public String id;
    private ListView listView;
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        RealmResults<Task> tasks = realm.where(Task.class).findAll();
        tasks = tasks.sort("timestamp");
        final TaskAdapter adapter = new TaskAdapter(this, tasks);

        listView = (ListView) findViewById(R.id.task_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Task task = (Task) adapterView.getAdapter().getItem(i);
                TaskDFragmentEdit dialog1 = new TaskDFragmentEdit();
                dialog1.setValue(task.getId());
                dialog1.show(getFragmentManager(),"aab");
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub
                final Task task = (Task) adapter.getAdapter().getItem(pos);
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteTask(task.getId());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                deleteTask(task.getId());
                            }
                        })
                        .create();
                dialog.show();

                return true;
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskDFragment dialog1 = new TaskDFragment();
                dialog1.show(getFragmentManager(),"aab");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public void changeTaskDone(final String taskId) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Task task = realm.where(Task.class).equalTo("id", taskId).findFirst();
                task.setDone(!task.isDone());
            }
        });
    }

    private void deleteTask(final String taskId) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Task.class).equalTo("id", taskId)
                        .findFirst()
                        .deleteFromRealm();
            }
        });
    }

    private void deleteAllDone() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Task.class).equalTo("done", true)
                        .findAll()
                        .deleteAllFromRealm();
            }
        });
    }

    public String getId() {
        return id;
    }
}

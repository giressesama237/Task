package com.orange.tpodc;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orange.tpodc.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private LinkedList<String> mTasklist = new LinkedList<>();
    private EditText mAlertEdit;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    FirebaseFirestore db =  FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                //input.setPadding(16,16,16,16);
                //tLayoutInflater(this,R.layout.alertforrm_layout);
               // Toast.makeText(this,"salueeee",Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(),"dfdfdfdfd",Toast.LENGTH_LONG).show();
                AlertDialog.Builder myAlert = new AlertDialog.Builder(MainActivity.this);

                View formView = getLayoutInflater().inflate(R.layout.alertforrm_layout,null);

                EditText description = formView.findViewById(R.id.alert_edit);
                myAlert.setView(formView);

                myAlert.setTitle("Fixez vous un nouvel objectif");

                //myAlert.setView(input);
                //myAlert.setMessage("djfdfkjdfjd0");
                myAlert.setPositiveButton("Enregister", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Map<String, Object> task = new HashMap<>();
                        task.put("name","Douala" );
                        task.put("state", true);
                        task.put("country", true);
                        db.collection("taches")
                                .add(task)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("TAG_FAILURE", "Error writing document", e);
                                    }
                                });
                       int mTaskListSize = mTasklist.size();
                        //mAlertEdit = findViewById(R.id.alert_edit);
                        mTasklist.addLast(description.getText().toString());
                        mRecyclerView.getAdapter().notifyItemInserted(mTaskListSize);
                        mRecyclerView.smoothScrollToPosition(mTaskListSize);
                        Toast.makeText(getApplicationContext(),"okkk",Toast.LENGTH_LONG).show();
                    }
                });
                myAlert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"cancel",Toast.LENGTH_LONG).show();

                    }
                });
                myAlert.show();
            }
        });

        mRecyclerView = findViewById(R.id.task_recyclerview);
        mAdapter = new RecyclerViewAdapter(this, mTasklist);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
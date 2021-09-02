package com.orange.tpodc;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.LinkedList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{
    private ArrayList<Task> mTaskList;
    private LayoutInflater mInflater;
    static final String TAG = "TAG_ADAPTER";

    public RecyclerViewAdapter(Context context) {
        this.mTaskList = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
        FirebaseUtil
                .getReferenceFirestore(FirebaseUtil.TASK_COLLECTION)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "listen:error", e);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    Log.d(TAG, "New city: " + dc.getDocument().getData());
                                    Task task = new Task(dc.getDocument().getString("taskTitle"),
                                            dc.getDocument().getBoolean("taskchecked"), 0);
                                    mTaskList.add(task.getItemPosition(),task);
                                    RecyclerViewAdapter.this.notifyItemInserted(task.getItemPosition());
                                    break;
                                case MODIFIED:
                                    Log.d(TAG, "Modified city: " + dc.getDocument().getData());
                                    break;
                                case REMOVED:
                                    Log.d(TAG, "Removed city: " + dc.getDocument().getData());
                                    break;
                            }
                        }

                    }
                });

    }

    @NonNull
    @Override
    public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.task_layout,parent,false);
        return new RecyclerViewHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        Task task = mTaskList.get(position);
        holder.taskItemCheck.setText(task.getTaskTitle());
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final CheckBox taskItemCheck;
        final RecyclerViewAdapter mAdapter;
        public RecyclerViewHolder(@NonNull View itemView, RecyclerViewAdapter adapter) {
            super(itemView);
            //CheckBox ItemCheck = itemView.findViewById(R.id.task_textview);
            taskItemCheck = itemView.findViewById(R.id.task_textview);
            this.mAdapter = adapter;
            taskItemCheck.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            boolean checked;
            int mPosition = getLayoutPosition();
            Task task = mTaskList.get(mPosition);
            Toast.makeText(itemView.getContext(), "validé",Toast.LENGTH_LONG).show();
            AlertDialog.Builder myAlert = new AlertDialog.Builder(view.getContext());
            myAlert.setTitle("Confirmation");
            myAlert.setMessage("Avez vous terminé la tâche "+task.getTaskTitle());
            myAlert.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(view.getContext(), "validé",Toast.LENGTH_LONG).show();
                }
            });
            myAlert.show();
        }
    }
}

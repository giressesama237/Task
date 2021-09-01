package com.orange.tpodc;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{
    private final LinkedList<String> mTaskList;
    private LayoutInflater mInflater;

    public RecyclerViewAdapter(Context context, LinkedList<String> mTaskList) {
        this.mTaskList = mTaskList;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.task_layout,parent,false);
        return new RecyclerViewHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String mCurrent = mTaskList.get(position);
        holder.taskItemCheck.setText(mCurrent);
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
            String element = mTaskList.get(mPosition);
            Toast.makeText(itemView.getContext(), "validé",Toast.LENGTH_LONG).show();
//            AlertDialog.Builder myAlert = new AlertDialog.Builder(view.getContext());
//            myAlert.setTitle("Confirmation");
//            myAlert.setMessage("Avez vous terminé la tâche "+element);
//            myAlert.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    Toast.makeText(view.getContext(), "validé",Toast.LENGTH_LONG).show();
//                }
//            });
//            myAlert.show();
        }
    }
}

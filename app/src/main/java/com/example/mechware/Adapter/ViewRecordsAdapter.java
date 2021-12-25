package com.example.mechware.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mechware.Helper.ViewRecordsHelper;
import com.example.mechware.R;
import com.example.mechware.ViewRecords.view_aircraft_records;
import com.example.mechware.ViewRecords.view_engine_records;
import com.example.mechware.ViewRecords.view_ndt_records;
import com.example.mechware.ViewRecords.view_pitot_records;
import com.example.mechware.ViewRecords.view_propeller_records;
import com.example.mechware.aircraft_form;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.List;

public class ViewRecordsAdapter extends RecyclerView.Adapter<ViewRecordsAdapter.ViewRecordsHolder>{

    private List<ViewRecordsHelper> viewRecordsHelper;

    public static class ViewRecordsHolder extends RecyclerView.ViewHolder{

        TextView label;
        AppCompatButton edit, view;

        String user_type, item_id, parent_reference;

        public ViewRecordsHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.label);

            edit = itemView.findViewById(R.id.edit_btn);
            view = itemView.findViewById(R.id.view_btn);

        }
    }

    public ViewRecordsAdapter(List<ViewRecordsHelper> viewRecordsHelper){
        this.viewRecordsHelper = viewRecordsHelper;
    }

    @NonNull
    @NotNull
    @Override
    public ViewRecordsAdapter.ViewRecordsHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.forms_item_layout, parent, false);
        ViewRecordsHolder vrh = new ViewRecordsHolder(v);
        return vrh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewRecordsAdapter.ViewRecordsHolder holder, int position) {
        ViewRecordsHelper currentViewRecordsHelper = viewRecordsHelper.get(position);
        holder.label.setText(currentViewRecordsHelper.getLabel());
        holder.item_id = currentViewRecordsHelper.getItem_id();
        holder.parent_reference = currentViewRecordsHelper.getParent_reference();
        holder.user_type = currentViewRecordsHelper.getUser_type();

        if(holder.parent_reference.equals("aircraft_records")){
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), view_aircraft_records.class);
                    intent.putExtra("action_type", "edit");
                    intent.putExtra("user_type", holder.user_type);
                    intent.putExtra("parent_ref",holder.parent_reference);
                    intent.putExtra("item_id",holder.item_id);
                    holder.itemView.getContext().startActivity(intent);
                }
            });
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), view_aircraft_records.class);
                    intent.putExtra("action_type", "view");
                    intent.putExtra("user_type", holder.user_type);
                    intent.putExtra("parent_ref",holder.parent_reference);
                    intent.putExtra("item_id",holder.item_id);
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }

        if(holder.parent_reference.equals("engine_records")){
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), view_engine_records.class);
                    intent.putExtra("action_type", "edit");
                    intent.putExtra("user_type", holder.user_type);
                    intent.putExtra("parent_ref",holder.parent_reference);
                    intent.putExtra("item_id",holder.item_id);
                    holder.itemView.getContext().startActivity(intent);
                }
            });
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), view_engine_records.class);
                    intent.putExtra("action_type", "view");
                    intent.putExtra("user_type", holder.user_type);
                    intent.putExtra("parent_ref",holder.parent_reference);
                    intent.putExtra("item_id",holder.item_id);
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }

        if(holder.parent_reference.equals("propeller_records")){
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), view_propeller_records.class);
                    intent.putExtra("action_type", "edit");
                    intent.putExtra("user_type", holder.user_type);
                    intent.putExtra("parent_ref",holder.parent_reference);
                    intent.putExtra("item_id",holder.item_id);
                    holder.itemView.getContext().startActivity(intent);
                }
            });
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), view_propeller_records.class);
                    intent.putExtra("action_type", "view");
                    intent.putExtra("user_type", holder.user_type);
                    intent.putExtra("parent_ref",holder.parent_reference);
                    intent.putExtra("item_id",holder.item_id);
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }

        if(holder.parent_reference.equals("pitot_records")){
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), view_pitot_records.class);
                    intent.putExtra("action_type", "edit");
                    intent.putExtra("user_type", holder.user_type);
                    intent.putExtra("parent_ref",holder.parent_reference);
                    intent.putExtra("item_id",holder.item_id);
                    holder.itemView.getContext().startActivity(intent);
                }
            });
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), view_pitot_records.class);
                    intent.putExtra("action_type", "view");
                    intent.putExtra("user_type", holder.user_type);
                    intent.putExtra("parent_ref",holder.parent_reference);
                    intent.putExtra("item_id",holder.item_id);
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }

        if(holder.parent_reference.equals("NDT_records")){
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), view_ndt_records.class);
                    intent.putExtra("action_type", "edit");
                    intent.putExtra("user_type", holder.user_type);
                    intent.putExtra("parent_ref",holder.parent_reference);
                    intent.putExtra("item_id",holder.item_id);
                    holder.itemView.getContext().startActivity(intent);
                }
            });
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), view_ndt_records.class);
                    intent.putExtra("action_type", "view");
                    intent.putExtra("user_type", holder.user_type);
                    intent.putExtra("parent_ref",holder.parent_reference);
                    intent.putExtra("item_id",holder.item_id);
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return viewRecordsHelper.size();
    }
}

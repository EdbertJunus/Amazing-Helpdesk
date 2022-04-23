package com.example.amazinghelpdesk.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amazinghelpdesk.HomeActivity;
import com.example.amazinghelpdesk.OfflineStaffActivity;
import com.example.amazinghelpdesk.OnlineStaffActivity;
import com.example.amazinghelpdesk.R;
import com.example.amazinghelpdesk.model.Reservation;
import com.example.amazinghelpdesk.model.Staff;

import java.util.ArrayList;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {

    private Context ctx;
    private ArrayList<Staff> staffList;

    public StaffAdapter(Context ctx, ArrayList<Staff> staffList){
        this.ctx = ctx;
        this.staffList = staffList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.staff_item, parent, false);
        return new ViewHolder(view);
    }

    private void handleStaffList(@NonNull ViewHolder holder, int position){
        Staff staff = staffList.get(position);

        String currentStaffId = staff.getId();

        holder.tvCardName.setText(staff.getName());

        if(!staff.isAvailableStatus()){
            holder.cvCard.setBackgroundColor(ContextCompat.getColor(ctx, R.color.offline));
        }

        holder.cvCard.setOnClickListener(view -> {
            Intent intent;
            if(staff.isAvailableStatus()){
                intent = new Intent(ctx, OnlineStaffActivity.class);
            }else{
                intent = new Intent(ctx, OfflineStaffActivity.class);
            }

            intent.putExtra("staffId", currentStaffId);
            ctx.startActivity(intent);
        });

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        handleStaffList(holder, position);
    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        protected CardView cvCard;
        protected TextView tvCardName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvCard = itemView.findViewById(R.id.staff_card);
            tvCardName = itemView.findViewById(R.id.staff_card_name);
        }
    }
}

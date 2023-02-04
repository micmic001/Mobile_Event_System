package com.micmicdev.mobileeventsystem.ADAPTERS;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.micmicdev.mobileeventsystem.R;
import com.micmicdev.mobileeventsystem.STR.strEventDetails;

import java.text.DecimalFormat;
import java.util.List;

public class attendanceDataAdapter extends RecyclerView.Adapter<attendanceDataAdapter.DataViewHolder> {
    private List<strEventDetails> attendanceDataList;

    public void refreshView(int position){
        notifyItemChanged(position);
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView attendanceName, attendanceSeatNo, attendanceTime, attendanceStatus;
        public DataViewHolder(View itemView) {
            super(itemView);
            attendanceName = itemView.findViewById(R.id.tv_name);
            attendanceSeatNo = itemView.findViewById(R.id.tv_seatNo);
            attendanceTime = itemView.findViewById(R.id.tv_time);
            attendanceStatus = itemView.findViewById(R.id.tv_attStatus);
        }
    }

    public attendanceDataAdapter(List<strEventDetails> attendanceDataList){
        this.attendanceDataList =attendanceDataList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_attendance_item, parent, false);

        return new DataViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        strEventDetails collector = attendanceDataList.get(position);
        holder.attendanceName.setText(collector.getFullName());
        holder.attendanceSeatNo.setText(collector.getSeat_no());
        String inStatusValue = collector.getInStatServer();
        String outStatusValue = collector.getOutStatServer();
        holder.attendanceStatus.setText("-");
        if(inStatusValue.equals("1")){
            holder.attendanceTime.setText(collector.getInTime());
            holder.attendanceStatus.setText("IN");
        }
        else if(outStatusValue.equals("1")){
            holder.attendanceTime.setText(collector.getOutTime());
            holder.attendanceStatus.setText("OUT");
        }

    }

    @Override
    public int getItemCount() {
        return attendanceDataList.size();
    }
}

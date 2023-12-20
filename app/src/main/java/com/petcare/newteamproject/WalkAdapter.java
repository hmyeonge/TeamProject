package com.petcare.newteamproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class WalkAdapter extends RecyclerView.Adapter<WalkAdapter.WalkViewHolder> {
    private ArrayList<WalkItem> nWalkItems;
    private Context nContext;
    private DBHelper nDBHelper;


    public WalkAdapter(ArrayList<WalkItem> nWalkItems, Context nContext) {
        this.nWalkItems = nWalkItems;
        this.nContext = nContext;
        nDBHelper = new DBHelper(nContext);
    }

    // 뷰 홀더 클래스 레이아웃 가져옴
    @NonNull
    @Override
    public WalkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_walk_list, parent, false);
        return new WalkViewHolder(holder);
    }

    // 아이템에 데이터 바인딩
    @Override
    public void onBindViewHolder(@NonNull WalkViewHolder holder, int position) {
        WalkItem walkItem = nWalkItems.get(position);
        holder.tvTime.setText(walkItem.getTime());
        holder.tvDate.setText(walkItem.getWriteDate());
    }

    // 리사이클러뷰에 표시될 아이템 총개수 반환
    @Override
    public int getItemCount() {
        if (nWalkItems == null) {
            return 0;
        }
        return nWalkItems.size();
    }

    // 뷰 홀더 클래스
    public static class WalkViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvDate;

        public WalkViewHolder(View itemView) {
            super(itemView);

            tvTime = itemView.findViewById(R.id.tv_walk_time);
            tvDate = itemView.findViewById(R.id.tv_date2);
        }
    }
}

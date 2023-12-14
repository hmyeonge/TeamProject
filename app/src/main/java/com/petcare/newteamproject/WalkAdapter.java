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

    // 생성자
    public WalkAdapter(ArrayList<WalkItem> nWalkItems, Context nContext) {
        this.nWalkItems = nWalkItems;
        this.nContext = nContext;
        nDBHelper = new DBHelper(nContext);
    }

    @NonNull
    @Override
    public WalkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 뷰 홀더에 사용될 레이아웃을 item_walk_list.xml 에서 가져옴
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_walk_list, parent, false);
        return new WalkViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull WalkViewHolder holder, int position) {
        WalkItem walkItem = nWalkItems.get(position);
        Log.d("WalkAdapter", "Time: " + walkItem.getTime() + ", Date: " + walkItem.getWriteDate());
        // 'time' 과 'writeDate' 필드의 값을 TextView 에 설정
        holder.tvTime.setText(walkItem.getTime());
        holder.tvDate.setText(walkItem.getWriteDate());
    }
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

    // 액티비티에서 호출되는 함수, 현재 어댑터에 새로운 아이템을 전달받아 추가
    public void addItem(WalkItem _item){
        // 역순으로 데이터 정렬 : 항상 최신 아이템이 맨 위로 오도록
        nWalkItems.add(0, _item);
        notifyItemInserted(0);
    }
}

package com.petcare.newteamproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WeightAdapter extends RecyclerView.Adapter<WeightAdapter.ViewHolder> {

    private ArrayList<WeightItem> nWeightItems;
    private Context nContext;
    private DBHelper nDBHelper;

    public WeightAdapter(ArrayList<WeightItem> nWeightItems, Context nContext) {
        this.nWeightItems = nWeightItems;
        this.nContext = nContext;
        nDBHelper = new DBHelper(nContext);
    }

    // 뷰 홀더 클래스 레이아웃 가져옴
    @NonNull
    @Override
    public WeightAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(holder);
    }

    // 아이템에 데이터를 바인딩
    @Override
    public void onBindViewHolder(@NonNull WeightAdapter.ViewHolder holder, int position) {
        holder.tv_weight.setText(String.valueOf(nWeightItems.get(position).getWeight()));
        holder.tv_date.setText(nWeightItems.get(position).getWriteDate());
    }

    // 리사이클러뷰에 표시될 아이템 총개수 반환
    @Override
    public int getItemCount() {
        return nWeightItems.size();
    }

    // 뷰 홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_weight;
        private TextView tv_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_weight = itemView.findViewById(R.id.tv_weight);
            tv_date = itemView.findViewById(R.id.tv_date);
        }
    }

}

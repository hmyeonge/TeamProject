package com.petcare.newteamproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>
{
    // nWeightItems: RecyclerView에서 표시될 데이터를 포함하는 WeightItem 객체들의 집합
    private ArrayList<WeightItem> nWeightItems;
    private Context nContext;
    private DBHelper nDBHelper;

    // 생성자
    public CustomAdapter(ArrayList<WeightItem> nWeightItems, Context nContext) {
        this.nWeightItems = nWeightItems;
        this.nContext = nContext;
        // nDBHelper 인스턴스 생성
        nDBHelper = new DBHelper(nContext);
    }

    @NonNull
    @Override
    // onCreateViewHolder 메소드 : RecyclerView 에 표시될 각 아이템의 뷰 홀더 객체 생성
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater : XML 에  정의된 뷰를 실제 뷰 객체로 변환 (R.layout.item_list 를 사용해 새로운 뷰 객체 생성)
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        // 위에서 생성된 뷰를 사용해 ViewHolder 객체를 생성
        // ViewHolder 클래스 : RecyclerView 에 표시될 아이템들의 뷰를 담는 컨테이너
        return new ViewHolder(holder);
    }

    @Override
    // RecyclerView 의 각 아이팀에 데이터를 바인딩
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        // ViewHolder 컨테이너의 뷰 요소들을 해당 데이터로 업데이트
        // String.valueOf 메소드 : Double 타입의 'weight' 값을 String 으로 변환
        holder.tv_weight.setText(String.valueOf(nWeightItems.get(position).getWeight()));
        holder.tv_date.setText(nWeightItems.get(position).getWriteDate());
    }

    @Override
    public int getItemCount() {
        // RecyclierView 화면에 표시될 아이템 총 개수 반환하는 메서드
        // nWeightItems 리스트의 아이템 수를 반환
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

    // 액티비티에서 호출되는 함수, 현재 어댑터에 새로운 아이템을 전달받아 추가
    public void addItem(WeightItem _item){
        // 역순으로 데이터 정렬 : 항상 최신 아이템이 맨 위로 오도록
        nWeightItems.add(0, _item);
        notifyItemInserted(0);
    }
}

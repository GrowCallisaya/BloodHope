package com.kantutapp.bloodhope.adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kantutapp.bloodhope.R;
import com.kantutapp.bloodhope.utils.TextViewMontserratRegular;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class BloodAdapter extends RecyclerView.Adapter<BloodAdapter.BloodViewHolder> {

    public List<String> mData;
    public Context mContext;
    public OnBloodClickHandler mListener;

    public int positionBloodSelected = -1;

    public BloodAdapter(List<String> data, Context context, OnBloodClickHandler listener) {
        mData = data;
        mContext = context;
        mListener = listener;
    }


    public interface OnBloodClickHandler{
        void onBloodTypeClickListener(String bloodType);
    }


    @NonNull
    @Override
    public BloodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutResource = R.layout.item_blood_type;

        View view = inflater.inflate(layoutResource,parent,false);
        BloodViewHolder  holder =  new BloodViewHolder (view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BloodViewHolder  holder, int position) {

        if (positionBloodSelected == position){
            holder.circleImageViewBackground.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_blood_clicked));
            holder.txtBloodType.setTextColor(Color.WHITE);
        }else {
            holder.circleImageViewBackground.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_blood));
            holder.txtBloodType.setTextColor(Color.GRAY);
        }

        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }




    public class BloodViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_blood_type) TextViewMontserratRegular txtBloodType;
        @BindView(R.id.ci_blood_background) CircleImageView circleImageViewBackground;

        public BloodViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(int position) {
            String bloodType= mData.get(position);
            txtBloodType.setText(bloodType);
        }

        @OnClick(R.id.ci_blood_background)
        public void onCardClicked(){
            positionBloodSelected = getAdapterPosition();
            notifyDataSetChanged();

            mListener.onBloodTypeClickListener(mData.get(positionBloodSelected));
        }



    }
}

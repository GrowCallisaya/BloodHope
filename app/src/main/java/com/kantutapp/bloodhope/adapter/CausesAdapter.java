package com.kantutapp.bloodhope.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kantutapp.bloodhope.R;
import com.kantutapp.bloodhope.models.Cause;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;

public class CausesAdapter extends RecyclerView.Adapter<CausesAdapter.CauseViewHolder> {

    public List<Cause> mData;
    public Context mContext;
    public OnItemClickHandler mListener;


    public CausesAdapter(List<Cause> data, Context context, OnItemClickHandler listener) {
        mData = data;
        mContext = context;
        mListener = listener;
    }


    public interface OnItemClickHandler{
        void onCardClickListener(Cause cause);
        void onDeleteListener(Cause cause);
        void onEditListener(Cause cause);
    }


    @NonNull
    @Override
    public CauseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutResource = R.layout.item_causes;

        View view = inflater.inflate(layoutResource,parent,false);
        CauseViewHolder holder =  new CauseViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CauseViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }




    public class CauseViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_cause)         public ImageView imageViewCause;
        @BindView(R.id.btn_delete_cause) public FancyButton btnDeleteCause;
        @BindView(R.id.btn_edit_cause)   public FancyButton btnEditCause;


        public CauseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(int position) {
            Cause cause= mData.get(position);
            if (cause.getImage() != null){
                if (!cause.getImage().isEmpty())
                    Picasso.get()
                            .load(cause.getImage())
                            .placeholder(R.drawable.cause)
                            .into(imageViewCause);
            }

        }

        @OnClick(R.id.card_my_cause)
        public void onCardClicked(){
            int position = getAdapterPosition();
            Cause cause = mData.get(position);
            mListener.onCardClickListener(cause);
        }

        @OnClick(R.id.btn_edit_cause)
        public void editCause(){
            int position = getAdapterPosition();
            Cause cause = mData.get(position);
            mListener.onEditListener(cause);
        }

        @OnClick(R.id.btn_delete_cause)
        public void deleteCause(){
            int position = getAdapterPosition();
            Cause cause = mData.get(position);
            mListener.onDeleteListener(cause);
        }
    }
}

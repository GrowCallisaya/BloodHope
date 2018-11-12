package com.kantutapp.bloodhope.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.kantutapp.bloodhope.R;
import com.kantutapp.bloodhope.models.Cause;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;

public class CauseViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.iv_cause)         public ImageView imageViewCause;
    @BindView(R.id.btn_delete_cause) public FancyButton btnDeleteCause;
    @BindView(R.id.btn_edit_cause)   public FancyButton btnEditCause;
    @BindView(R.id.card_my_cause)   public CardView cardView;

    public CauseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindToCause(Cause cause) {
        Picasso.get()
                .load(cause.getImage())
                .placeholder(R.drawable.cause)
                .into(imageViewCause);

    }
}


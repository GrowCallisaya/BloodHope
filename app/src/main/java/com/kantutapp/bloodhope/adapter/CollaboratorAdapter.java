package com.kantutapp.bloodhope.adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.kantutapp.bloodhope.R;
import com.kantutapp.bloodhope.models.UserCollaborator;
import com.kantutapp.bloodhope.utils.TextViewMontserratRegular;
import com.squareup.picasso.Picasso;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CollaboratorAdapter extends RecyclerView.Adapter<CollaboratorAdapter.CauseViewHolder> {

    public List<UserCollaborator> mData;
    public Context mContext;
    public onChechboxHandler mChechboxHandler;


    public CollaboratorAdapter(List<UserCollaborator> data, Context context,  onChechboxHandler handler) {
        mData = data;
        mContext = context;
        mChechboxHandler = handler;
    }

    public interface onChechboxHandler{
        void onCheckBoxSelectedListener(UserCollaborator userCollaborator);
    }

    @NonNull
    @Override
    public CauseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutResource = R.layout.item_collaborator;

        View view = inflater.inflate(layoutResource, parent, false);
        CauseViewHolder holder = new CauseViewHolder(view);

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


    public class CauseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.collaborator_profile)
        CircleImageView collaboratorProfile;
        @BindView(R.id.collaborator_name)
        TextViewMontserratRegular collaboratorName;
        @BindView(R.id.collaborator_city)
        TextViewMontserratRegular collaboratorCity;
        @BindView(R.id.collaborator_status)
        CheckBox collaboratorStatus;

        public CauseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(int position) {
            final UserCollaborator userCollaborator = mData.get(position);
            Picasso.get()
                    .load(userCollaborator.getPhoto())
                    .placeholder(R.drawable.cause)
                    .into(collaboratorProfile);
            collaboratorName.setText(userCollaborator.getName());
            collaboratorCity.setText(userCollaborator.getCity());
            collaboratorStatus.setChecked(userCollaborator.getStatus());
            if (collaboratorStatus.isChecked()){
                collaboratorStatus.setEnabled(false);
            }
            collaboratorStatus.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
                    if (isChecked){
                        new LovelyStandardDialog(mContext, LovelyStandardDialog.ButtonLayout.VERTICAL)
                                .setTopColorRes(R.color.colorAccent)
                                .setButtonsColorRes(R.color.colorAccent)
                                .setIcon(R.drawable.ic_profile)
                                .setIconTintColor(Color.WHITE)
                                .setTitle("Add Collaborator")
                                .setMessage("Are you sure you are going to add this collaborator?")
                                .setPositiveButton("YES, I AM", v -> {
                                    Log.e("","cauusea");
                                    mChechboxHandler.onCheckBoxSelectedListener(userCollaborator);
                                })
                                .setNegativeButton("NOT YET", v ->{
                                    buttonView.setChecked(false);
                                })
                                .show();
                    }
            });
        }
    }
}

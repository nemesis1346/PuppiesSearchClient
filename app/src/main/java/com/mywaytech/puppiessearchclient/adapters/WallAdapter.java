package com.mywaytech.puppiessearchclient.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.models.UserPetObject;

import java.util.List;

/**
 * Created by Marco on 4/15/2016.
 */
public class WallAdapter extends RecyclerView.Adapter<WallAdapter.ItemViewHolder> {
    private Context mContext;
    private List<UserPetObject> mListItems;

    private CallBacks callbacks;


    public WallAdapter(Context mContext, List<UserPetObject> mListItems) {
        this.mContext = mContext;
        this.mListItems = mListItems;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.wall_item_layout, parent, false);
        return new ItemViewHolder(itemView);

    }

    public void setListItems(List<UserPetObject> newListItems) {
        mListItems.clear();
        mListItems.addAll(newListItems);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        holder.userName.setText(mListItems.get(position).getuName());
        holder.petImage.setImageResource(mListItems.get(position).getpImage());
        holder.userAddres.setText(mListItems.get(position).getuAddress());
        holder.userComment.setText(mListItems.get(position).getuComment());

    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {


        private TextView userName;
        private ImageView petImage;
        private TextView userAddres;
        private TextView userComment;

        public ItemViewHolder(View itemView) {
            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.item_name);
            petImage= (ImageView) itemView.findViewById(R.id.item_dog_image);
            userAddres= (TextView) itemView.findViewById(R.id.item_address);
            userComment= (TextView) itemView.findViewById(R.id.item_comment);
            if (callbacks != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callbacks.onClickListener(mListItems.get(getAdapterPosition()));
                    }
                });
            }
        }
    }


    public void setCallbacks(CallBacks callbacks) {
        this.callbacks = callbacks;
    }

    public interface CallBacks {
        public void onClickListener(UserPetObject listItem);
    }
}

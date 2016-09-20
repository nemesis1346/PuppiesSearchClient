package com.mywaytech.puppiessearchclient.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.models.ReportObject;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Marco on 4/15/2016.
 */
public class WallAdapter extends RecyclerView.Adapter<WallAdapter.ItemViewHolder> {
    private Context mContext;
    private List<ReportObject> mListItems;
    private CallBacks callbacks;

    public WallAdapter(Context mContext, List<ReportObject> mListItems) {
        this.mContext = mContext;
        this.mListItems = mListItems;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.wall_item_layout, parent, false);
        return new ItemViewHolder(itemView);

    }

    public void setListItems(List<ReportObject> newListItems) {
        mListItems.clear();
        mListItems.addAll(newListItems);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        mListItems.get(position);
        holder.userName.setText(mListItems.get(position).getuName());
       // holder.petImage.setImageResource(mListItems.get(position).getpImage());

        if(mListItems.get(position).getImagePath()!=null) {
            File imgFile = new  File(mListItems.get(position).getImagePath());

            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.petImage.setImageBitmap(myBitmap);
            }
        }
        holder.userAddres.setText(mListItems.get(position).getuAddress());
        holder.userComment.setText(mListItems.get(position).getuComment());
        holder.mUserPictureContainer.setImageResource(R.drawable.ic_user_picture);

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
        private CircleImageView mUserPictureContainer;

        public ItemViewHolder(View itemView) {
            super(itemView);

            mUserPictureContainer = (CircleImageView) itemView.findViewById(R.id.image_userPicture_container);
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
        public void onClickListener(ReportObject listItem);
    }

    public void updateData(ReportObject reportObject){
        mListItems.add(0, reportObject);
        notifyDataSetChanged();
    }
}

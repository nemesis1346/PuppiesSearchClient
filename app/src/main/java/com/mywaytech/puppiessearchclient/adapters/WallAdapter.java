package com.mywaytech.puppiessearchclient.adapters;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.models.UserPetObject;

import java.io.File;
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
       // holder.petImage.setImageResource(mListItems.get(position).getpImage());
        int width = holder.petImage.getWidth();
        int height=holder.petImage.getHeight();


        if(mListItems.get(position).getImagePath()!=null) {
            File imgFile = new  File(mListItems.get(position).getImagePath());

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.petImage.setImageBitmap(myBitmap);

            }
        }else{
            holder.petImage.setImageBitmap(decodeSampledBitmapFromResource(mContext.getResources(), mListItems.get(position).getpImage(), 200, 200));
        }

        holder.userAddres.setText(mListItems.get(position).getuAddress());
        holder.userComment.setText(mListItems.get(position).getuComment());

    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
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

    public void updateData(UserPetObject userPetObject){
        mListItems.add(0,userPetObject);
        notifyDataSetChanged();
    }
}

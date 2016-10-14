package com.mywaytech.puppiessearchclient.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.fragments.ReportFragment;
import com.mywaytech.puppiessearchclient.models.NewUserModel;
import com.mywaytech.puppiessearchclient.models.ReportModel;
import com.mywaytech.puppiessearchclient.dataaccess.FireBaseHandler;
import com.mywaytech.puppiessearchclient.utils.Utils;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Marco on 4/15/2016.
 */
public class WallAdapter extends RecyclerView.Adapter<WallAdapter.ItemViewHolder> {
    private Context mContext;
    private List<ReportModel> mListItems;
    private CallBacks callbacks;

    public WallAdapter(Context mContext, List<ReportModel> mListItems) {
        this.mContext = mContext;
        this.mListItems = mListItems;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.wall_item_layout, parent, false);
        return new ItemViewHolder(itemView);
    }

    public void setListItems(List<ReportModel> newListItems) {
        mListItems.clear();
        Collections.sort(newListItems, new Comparator<ReportModel>() {
            @Override
            public int compare(ReportModel lhs, ReportModel rhs) {
                Date o1 = Utils.convertLongToDate(lhs.getuDate());
                Date o2 = Utils.convertLongToDate(rhs.getuDate());
                return o1.compareTo(o2);
            }
        });
        mListItems.addAll(newListItems);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        showProgress(holder);

        FireBaseHandler.getInstance(mContext).getUserObjectFirebaseDatabaseReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        NewUserModel mNewUserObject = dataSnapshot.getValue(NewUserModel.class);

                        final long ONE_MEGABYTE = 1024 * 1024;

                        FireBaseHandler.getInstance(mContext)
                                .getUserObjectFirebaseStorageReference(mNewUserObject.getUserImagePath())
                                .getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                holder.mUserPictureContainer.setImageBitmap(bitmap);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        final long ONE_MEGABYTE = 1024 * 1024 * 2;

        FireBaseHandler.getInstance(mContext)
                .getImageFirebaseStorageReference(mListItems.get(position).getImagePath())
                .getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                hideProgress(holder);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                holder.petImage.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                showError(holder, R.string.error_no_results_found);
            }
        });

        holder.userName.setText(mListItems.get(position).getuName());
        holder.userAddress.setText(mListItems.get(position).getuAddress());
        holder.userComment.setText(mListItems.get(position).getuComment());

        holder.type.setText(mListItems.get(position).getuType());
        holder.userEmail.setText(mListItems.get(position).getuEmail());
        holder.userEmail.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        holder.userEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.openEmail(v.getContext(), new String[]{mListItems.get(position).getuEmail()}, null, null);
            }
        });

        String typeParam;
        if (mListItems.get(position).getuType() != null) {
            typeParam = mListItems.get(position).getuType();

            switch (typeParam) {
                case ReportFragment.TYPE_PET_ADOPTION:
                    holder.typeBackground.setBackgroundResource(R.color.red);
                    break;
                case ReportFragment.TYPE_PET_LOST:
                    holder.typeBackground.setBackgroundResource(R.color.yellow);
                    break;
                default:
                    break;
            }
        } else {
            holder.typeBackground.setBackgroundResource(R.color.yellow);
        }

        if(mListItems.get(position).getuDate()!=null){
            holder.mDate.setText(Utils.convertLongToString(mListItems.get(position).getuDate()));
        }
    }

    private void showProgress(ItemViewHolder itemViewHolder) {
        itemViewHolder.mProgressBar.setVisibility(View.VISIBLE);
        itemViewHolder.mProgressTextInfo.setVisibility(View.VISIBLE);
        itemViewHolder.mProgressTextInfo.setText(R.string.pet_loading_message);
    }

    private void hideProgress(ItemViewHolder itemViewHolder) {
        itemViewHolder.mProgressBar.setVisibility(View.GONE);
        itemViewHolder.mProgressTextInfo.setVisibility(View.GONE);
    }

    private void showError(ItemViewHolder itemViewHolder, @StringRes int stringId) {
        itemViewHolder.mProgressBar.setVisibility(View.GONE);
        itemViewHolder.mProgressTextInfo.setVisibility(View.VISIBLE);
        itemViewHolder.mProgressErrorImg.setVisibility(View.VISIBLE);
        itemViewHolder.mProgressTextInfo.setText(stringId);
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar mProgressBar;
        private TextView mProgressTextInfo;
        private ImageView mProgressErrorImg;

        private TextView userName;
        private ImageView petImage;
        private TextView userAddress;
        private TextView userComment;
        private TextView userEmail;
        private TextView mDate;
        private CircleImageView mUserPictureContainer;

        private TextView type;
        private LinearLayout typeBackground;

        public ItemViewHolder(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            mProgressTextInfo = (TextView) itemView.findViewById(R.id.text_progress_info);
            mProgressErrorImg = (ImageView) itemView.findViewById(R.id.img_error_icon);

            mUserPictureContainer = (CircleImageView) itemView.findViewById(R.id.image_userPicture_container);
            userName = (TextView) itemView.findViewById(R.id.item_name);
            userEmail = (TextView) itemView.findViewById(R.id.item_email);
            petImage = (ImageView) itemView.findViewById(R.id.item_dog_image);
            userAddress = (TextView) itemView.findViewById(R.id.item_address);
            userComment = (TextView) itemView.findViewById(R.id.item_comment);
            mDate = (TextView) itemView.findViewById(R.id.item_date);
            type = (TextView) itemView.findViewById(R.id.item_type);
            typeBackground = (LinearLayout) itemView.findViewById(R.id.layout_type_date);

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
        void onClickListener(ReportModel listItem);
    }

    public void updateData(ReportModel reportModel) {
        mListItems.add(0, reportModel);
        notifyDataSetChanged();
    }


}

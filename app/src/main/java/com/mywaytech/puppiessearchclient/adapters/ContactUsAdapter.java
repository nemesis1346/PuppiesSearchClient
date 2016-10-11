package com.mywaytech.puppiessearchclient.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.fragments.ReportFragment;
import com.mywaytech.puppiessearchclient.models.ContactUsModel;

import java.util.List;

/**
 * Created by nemesis1346 on 7/10/2016.
 */
public class ContactUsAdapter extends RecyclerView.Adapter<ContactUsAdapter.ContactUsViewHolder> {

    public static final String TYPE_PHONE = "PHONE";
    public static final String TYPE_ADDRESS = "ADDRESS";
    public static final String TYPE_EMAIL = "EMAIL";
    public static final String TYPE_LINK = "LINK";

    private Context mContext;
    private List<ContactUsModel> mContactUsModelList;

    public ContactUsAdapter(Context context, List<ContactUsModel> contactUsModels) {
        this.mContext = context;
        this.mContactUsModelList = contactUsModels;
    }

    @Override
    public ContactUsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.row_contact_us, parent, false);
        return new ContactUsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactUsViewHolder holder, int position) {
        holder.bindContacts(mContactUsModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return mContactUsModelList.size();
    }

    public static class ContactUsViewHolder extends RecyclerView.ViewHolder {
        private OnContactClickListener mOnContactClickListener;

        private TextView mName;
        private TextView mAddress;
        private TextView mEmail;
        private TextView mPhone;
        private TextView mLink;

        private ImageView mAddressImage;
        private ImageView mEmailImage;
        private ImageView mPhoneImage;
        private ImageView mLinkImage;

        public ContactUsViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.contact_name);
            mAddress = (TextView) itemView.findViewById(R.id.contact_address);
            mEmail = (TextView) itemView.findViewById(R.id.contact_email);
            mPhone = (TextView) itemView.findViewById(R.id.contact_phone);
            mLink = (TextView) itemView.findViewById(R.id.contact_link);

            mAddressImage = (ImageView) itemView.findViewById(R.id.ic_address);
            mEmailImage = (ImageView) itemView.findViewById(R.id.ic_email);
            mPhoneImage = (ImageView) itemView.findViewById(R.id.ic_phone);
            mLinkImage = (ImageView) itemView.findViewById(R.id.ic_link);
        }

        public void bindContacts(final ContactUsModel contactUsModel) {
            if (!contactUsModel.getName().equals("")) {
                mName.setText(contactUsModel.getName());
            } else {
                mName.setVisibility(View.GONE);
            }
            if(!contactUsModel.getAddress().equals("")){
                mAddress.setText(contactUsModel.getAddress());
                mAddressImage.setImageResource(R.drawable.ic_address);
                mAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnContactClickListener != null) {
                            mOnContactClickListener.onClick(contactUsModel.getAddress(), TYPE_ADDRESS);
                        }
                    }
                });
            }else{
                mAddress.setVisibility(View.GONE);
                mAddressImage.setVisibility(View.GONE);
            }
            if(!contactUsModel.getEmailText().equals("")){
                mEmail.setText(contactUsModel.getEmailText());
                mEmailImage.setImageResource(R.drawable.ic_email);
                mEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnContactClickListener != null) {
                            mOnContactClickListener.onClick(contactUsModel.getEmailText(), TYPE_EMAIL);
                        }
                    }
                });
            }else{
                mEmail.setVisibility(View.GONE);
                mEmailImage.setVisibility(View.GONE);
            }
            if(!contactUsModel.getCellphone().equals("")){
                mPhone.setText(contactUsModel.getCellphone());
                mPhoneImage.setImageResource(R.drawable.ic_phone);
                mPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnContactClickListener != null) {
                            mOnContactClickListener.onClick(contactUsModel.getCellphone(), TYPE_PHONE);
                        }
                    }
                });
            }else{
                mPhone.setVisibility(View.GONE);
                mPhoneImage.setVisibility(View.GONE);
            }
            if(!contactUsModel.getLink().equals("")){
                mLink.setText(contactUsModel.getLink());
                mLinkImage.setImageResource(R.drawable.ic_link);
                mLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnContactClickListener != null) {
                            mOnContactClickListener.onClick(contactUsModel.getLink(), TYPE_LINK);
                        }
                    }
                });
            }else{
                mLink.setVisibility(View.GONE);
                mLinkImage.setVisibility(View.GONE);
            }


        }
    }

    public interface OnContactClickListener{
        void onClick(String input, String type);
    }
}

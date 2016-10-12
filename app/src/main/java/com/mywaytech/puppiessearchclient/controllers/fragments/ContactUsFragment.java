package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.adapters.ContactUsAdapter;
import com.mywaytech.puppiessearchclient.models.ContactUsModel;
import com.mywaytech.puppiessearchclient.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nemesis1346 on 7/10/2016.
 */
public class ContactUsFragment extends Fragment implements ContactUsAdapter.OnContactClickListener{

    private String[] mAddress;
    private String[] mName;
    private String[] mLink;
    private String[] mPhone;
    private String[] mEmail;
    private List<ContactUsModel> mContactUsModelList;
    private ContactUsAdapter mContactUsAdapter;
    private RecyclerView mContactusList;

    public static ContactUsFragment newInstance() {
        Bundle args = new Bundle();
        ContactUsFragment fragment = new ContactUsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact_us, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.main_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.contact_us_title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mName = getResources().getStringArray(R.array.contact_us_name_array);
        mEmail = getResources().getStringArray(R.array.contact_us_email_array);
        mAddress = getResources().getStringArray(R.array.contact_us_address_array);
        mPhone = getResources().getStringArray(R.array.contact_us_cellphone_array);
        mLink = getResources().getStringArray(R.array.contact_us_link_array);

        mContactusList = (RecyclerView) rootView.findViewById(R.id.recycler_view_contacts_options);

        mContactUsModelList=new ArrayList<>();

        for (int i = 0; i < mName.length; i++) {
            ContactUsModel contactUsModel = new ContactUsModel();

            if (!mName[i].isEmpty()) {
                contactUsModel.setName(mName[i]);
            }else{
                contactUsModel.setName("");
            }
            if (!mEmail[i].isEmpty()) {
                contactUsModel.setEmailText(mEmail[i]);
            }else{
                contactUsModel.setEmailText("");
            }
            if (!mAddress[i].isEmpty()) {
                contactUsModel.setAddress(mAddress[i]);
            }else{
                contactUsModel.setAddress("");
            }
            if (!mPhone[i].isEmpty()) {
                contactUsModel.setCellphone(mPhone[i]);
            }else{
                contactUsModel.setCellphone("");
            }
            if (!mLink[i].isEmpty()) {
                contactUsModel.setLink(mLink[i]);
            }else{
                contactUsModel.setLink("");
            }

            mContactUsModelList.add(contactUsModel);
        }

        mContactUsAdapter = new ContactUsAdapter(getContext(), mContactUsModelList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        mContactusList.setLayoutManager(linearLayoutManager);
        mContactusList.setAdapter(mContactUsAdapter);
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(String input, String type) {
        switch(type){
            case ContactUsAdapter.TYPE_ADDRESS:
                break;
            case ContactUsAdapter.TYPE_EMAIL:
                Utils.openEmail(getContext(),new String[]{input},null, null);
                break;
            case ContactUsAdapter.TYPE_LINK:
                Utils.openBrowser(getContext(), input);
                break;
            case ContactUsAdapter.TYPE_PHONE:
                Utils.openPhone(getContext(), input);
                break;
            default:
                break;
        }
    }
}

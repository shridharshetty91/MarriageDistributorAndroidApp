package com.example.shridhar.distributorapp.NewContactDetails;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.shridhar.distributorapp.AppComponents.AppFragment;
import com.example.shridhar.distributorapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateContactFragment extends AppFragment {

    private Toolbar mToolbar;

    private EditText mNameEditText;
    private EditText mPhoneEditText;
    private Button mCreateContactButton;
    private Button mDeleteContactButton;

    public Contact contact;
    public Contact.ContactType contactType;

    public CreateContactFragment() {
        // Required empty public constructor
    }

    private OnCreateContactFragmentInteractionListner mContactListner;
    public void setOnCreateContactListner(OnCreateContactFragmentInteractionListner listner) {
        mContactListner = listner;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_contact, container, false);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);

        loadAllViews(view);
        setUpViewActions();
        updateViewsData();
        return view;
    }

    private void loadAllViews(View view) {
        mNameEditText = (EditText)view.findViewById(R.id.new_contact_name_tf);
        mPhoneEditText = (EditText)view.findViewById(R.id.new_contact_phone_tf);
        mCreateContactButton = (Button)view.findViewById(R.id.create_contact_button);
        mDeleteContactButton = (Button)view.findViewById(R.id.delete_contact_button);
    }

    private void setUpViewActions() {

        mCreateContactButton.setOnClickListener(mOnCreateContactClickListner);
        mDeleteContactButton.setOnClickListener(mOnDeleteContactClickListner);
    }

    private void updateViewsData() {
        if (contact != null) {
            mNameEditText.setText(contact.name);
            mPhoneEditText.setText(contact.phoneNumber);
            mCreateContactButton.setText(getResources().getString(R.string.contact_update_button_title));
            mDeleteContactButton.setVisibility(View.VISIBLE);
        } else {
            mCreateContactButton.setText(getResources().getString(R.string.create_contact_button_title));
            mDeleteContactButton.setVisibility(View.GONE);
        }

        if (contactType == Contact.ContactType.Bride) {
            mToolbar.setTitle("Bride Contact Details");
        } else {
            mToolbar.setTitle("Groom Contact Details");
        }
    }

    /*
    * Button Actions
    * */
    private View.OnClickListener mOnCreateContactClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (contact != null) {
                updateContact(contact);
                mContactListner.updatedEvent(contact);
            } else {
                Contact contact = new Contact();
                updateContact(contact);
                mContactListner.contactCreated(contact);
            }

            popFragment();
        }
    };

    private View.OnClickListener mOnDeleteContactClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mContactListner.deletContact(contact);
            popFragment();
        }
    };

    /*
    * Helper Methods
    * */
    private void updateContact(Contact contact) {
        contact.type = contactType;
        contact.name = mNameEditText.getText().toString();
        contact.phoneNumber = mPhoneEditText.getText().toString();
    }

    public interface OnCreateContactFragmentInteractionListner {
        void contactCreated(Contact contact);
        void updatedEvent(Contact contact);
        void deletContact(Contact contact);
    }

}

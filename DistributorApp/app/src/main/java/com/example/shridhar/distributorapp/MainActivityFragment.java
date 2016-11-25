package com.example.shridhar.distributorapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.shridhar.distributorapp.AppComponents.AppFragment;
import com.example.shridhar.distributorapp.CreateEvent.CreateEventFragment;
import com.example.shridhar.distributorapp.CreateEvent.Event;
import com.example.shridhar.distributorapp.FavouritePlaces.CreateFavouritePlaceFragment;
import com.example.shridhar.distributorapp.FavouritePlaces.PlaceInfo;
import com.example.shridhar.distributorapp.HelperComponents.HelperFunctions;
import com.example.shridhar.distributorapp.ImageCropperHelper.ImageCropperActivity;
import com.example.shridhar.distributorapp.NewContactDetails.Contact;
import com.example.shridhar.distributorapp.NewContactDetails.CreateContactFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends AppFragment {

    private EditText mBrideFNEditText;
    private EditText mBrideLNEditText;
    private EditText mGroomFNEditText;
    private EditText mGroomLNEditText;
    private EditText mMarriageDateEditText;
    private EditText mMarriageTimeEditText;
    private ImageView mBrideImageView;
    private Button mBrideImageDeleteButton;
    private ImageView mGroomImageView;
    private Button mGroomImageDeleteButton;

    private Button mAddNewEventButton;
    private LinearLayout mEventsHolder;

    private Button mAddBrideContactDetailsButton;
    private LinearLayout mBrideContactsHolder;
    private Button mAddGroomContactDetailsButton;
    private LinearLayout mGroomContactsHolder;

    private EditText mAdminEmailIdEditText;
    private Button mAddAdminEmailIdButton;
    private LinearLayout mAdminEmailIdsHolderView;

    private Button mAddNearTouristPlacesButton;
    private LinearLayout mNearTouristPlacesHolderView;

    private ArrayList<Event> mEvents = new ArrayList<>();
    private ArrayList<Contact> mBrideContacts = new ArrayList<>();
    private ArrayList<Contact> mGroomContacts = new ArrayList<>();
    private ArrayList<PlaceInfo> mNearByPlaces = new ArrayList<>();

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        loadAllViews(view);
        setUpViewActions();

        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        view.setFocusableInTouchMode(true);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == getResources().getInteger(R.integer.bride_image_request)) {
            handleImageCropActivityResult(data, mBrideImageView, mBrideImageDeleteButton);
        } else if (requestCode == getResources().getInteger(R.integer.groom_image_request)) {
            handleImageCropActivityResult(data, mGroomImageView, mGroomImageDeleteButton);
        }
    }

    //Date Edit Text Related
    private View.OnClickListener mMarriageDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDateFragment();
        }
    };

    private View.OnFocusChangeListener mMarriageDateFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (!hasFocus) return;
            hideKeyboard();
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    showDateFragment();
                }
            }, 200);
        }
    };

    void showDateFragment() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String text = String.valueOf(dayOfMonth) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
                mMarriageDateEditText.setText(text);
            }
        }, year, month, day);
        dialog.show();
    }

    /*
    * Time Edit Text Related
    * */
    private View.OnClickListener mMarriageTimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showTimeFragment();
        }
    };

    private View.OnFocusChangeListener mMarriageTimeFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (!hasFocus) return;
            hideKeyboard();
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    showTimeFragment();
                }
            }, 200);
        }
    };

    private void showTimeFragment() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String text = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
                mMarriageTimeEditText.setText(text);
            }
        }, hour, minute,
                DateFormat.is24HourFormat(getContext()));
        timePickerDialog.show();
    }

    /*
    * Image Related
    * */
    View.OnClickListener mChooseBrideImageClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presentImageCropperActivity(getResources().getInteger(R.integer.bride_image_request));
        }
    };

    View.OnClickListener mDeleteBrideImageClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mBrideImageView.setImageBitmap(null);
            mBrideImageDeleteButton.setVisibility(View.GONE);
        }
    };

    View.OnClickListener mChooseGroomImageClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presentImageCropperActivity(getResources().getInteger(R.integer.groom_image_request));
        }
    };

    View.OnClickListener mDeleteGroomImageClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mGroomImageView.setImageBitmap(null);
            mGroomImageDeleteButton.setVisibility(View.GONE);
        }
    };

    void presentImageCropperActivity(int requestCode) {
        Intent intent = new Intent(getContext(), ImageCropperActivity.class);
        intent.putExtra("CropType", "CIRCULAR");
        startActivityForResult(intent, requestCode);
    }

    /*
    * Events Related
    * */
    private View.OnClickListener mAddNewEventClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            addCreateEventFragment(null);
        }
    };

    private CreateEventFragment.OnCreateEventFragmentInteractionListner mEventCreateListner =
            new CreateEventFragment.OnCreateEventFragmentInteractionListner() {
                @Override
                public void eventCreated(Event event) {
                    mEvents.add(event);
                    TextView textView = getNewTextView();
                    textView.setText(event.name);
                    textView.setOnClickListener(mOnClickEventListner);
                    LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                    layout.gravity = Gravity.LEFT;
                    mEventsHolder.addView(textView, layout);
                }

                @Override
                public void updatedEvent(Event event) {
                    int index = mEvents.indexOf(event);
                    Button button = (Button) mEventsHolder.getChildAt(index);
                    button.setText(event.name);
                }

                @Override
                public void deleteEvent(Event event) {
                    int index = mEvents.indexOf(event);
                    mEventsHolder.removeViewAt(index);
                    mEvents.remove(index);
                }
            };

    private View.OnClickListener mOnClickEventListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = mEventsHolder.indexOfChild(v);
            addCreateEventFragment(mEvents.get(index));
        }
    };

    /*
    * Contact Details Related
    * */
    private View.OnClickListener mAddBrideContactDetailsClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addCreateContactFragment(null, Contact.ContactType.Bride);
        }
    };

    private View.OnClickListener mAddGroomContactDetailsClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addCreateContactFragment(null, Contact.ContactType.Groom);
        }
    };

    private CreateContactFragment.OnCreateContactFragmentInteractionListner mCreateContactListner =
            new CreateContactFragment.OnCreateContactFragmentInteractionListner() {
                @Override
                public void contactCreated(Contact contact) {
                    TextView textView = getNewTextView();
                    textView.setText(contact.name);
                    textView.setOnClickListener(mOnClickContactListner);
                    LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                    layout.gravity = Gravity.START;

                    if (contact.type == Contact.ContactType.Bride) {
                        mBrideContacts.add(contact);
                        mBrideContactsHolder.addView(textView, layout);
                    } else if (contact.type == Contact.ContactType.Groom) {
                        mGroomContacts.add(contact);
                        mGroomContactsHolder.addView(textView, layout);
                    }
                }

                @Override
                public void updatedEvent(Contact contact) {

                    TextView textView = null;
                    if (contact.type == Contact.ContactType.Bride) {
                        int index = mBrideContacts.indexOf(contact);
                        textView = (TextView) mBrideContactsHolder.getChildAt(index);
                    } else if (contact.type == Contact.ContactType.Groom) {
                        int index = mGroomContacts.indexOf(contact);
                        textView = (TextView) mGroomContactsHolder.getChildAt(index);
                    }
                    textView.setText(contact.name);
                }

                @Override
                public void deletContact(Contact contact) {

                    if (contact.type == Contact.ContactType.Bride) {
                        int index = mBrideContacts.indexOf(contact);
                        mBrideContactsHolder.removeViewAt(index);
                        mBrideContacts.remove(index);
                    } else if (contact.type == Contact.ContactType.Groom) {
                        int index = mGroomContacts.indexOf(contact);
                        mGroomContactsHolder.removeViewAt(index);
                        mGroomContacts.remove(index);
                    }
                }
            };

    private View.OnClickListener mOnClickContactListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = mBrideContactsHolder.indexOfChild(v);
            Contact contact = null;
            if (index != -1) {
                //This is from Bride Contact
                contact = mBrideContacts.get(index);
            } else {
                index = mGroomContactsHolder.indexOfChild(v);
                if (index != -1) {
                    //This is from Groom Contact
                    contact = mGroomContacts.get(index);
                }
            }

            if (contact != null) {
                addCreateContactFragment(contact, contact.type);
            }

        }
    };

    private View.OnClickListener addEmailIdButtonListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String adminEmailId = mAdminEmailIdEditText.getText().toString();
            if (adminEmailId.length() > 0 && HelperFunctions.isValidEmail(adminEmailId)) {
                AdminEmailHolder adminEmailHolder = (AdminEmailHolder) getActivity().
                        getLayoutInflater().inflate(R.layout.admin_email_holder, null);
                adminEmailHolder.setEmailId(adminEmailId);
                adminEmailHolder.setDeleteButtonClickListner(deleteAdminEmailIdListner);
                mAdminEmailIdsHolderView.addView(adminEmailHolder);

                mAdminEmailIdEditText.setText("");
            } else {
                Toast.makeText(getActivity(), "Invalid Email Id",
                        Toast.LENGTH_SHORT).show();
            }

        }
    };

    private View.OnClickListener deleteAdminEmailIdListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AdminEmailHolder emailHolder = (AdminEmailHolder) v.getParent();
            ((LinearLayout)emailHolder.getParent()).removeView(emailHolder);
        }
    };

    /*
    * Near By Places
    * */
    private View.OnClickListener mAddNearByPlaceClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNearByPlaceFragment(null);
        }
    };

    private CreateFavouritePlaceFragment.OnCreateFavouritePlaceFragmentListener
            onCreateFavouritePlaceFragmentListener = new CreateFavouritePlaceFragment.
            OnCreateFavouritePlaceFragmentListener() {
        @Override
        public void favouritePlaceCreated(PlaceInfo placeInfo) {

            TextView button = getNewTextView();
            button.setText(placeInfo.name);
            button.setOnClickListener(mNearByPlaceClickListener);
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            layout.gravity = Gravity.START;

            mNearByPlaces.add(placeInfo);
            mNearTouristPlacesHolderView.addView(button, layout);
        }

        @Override
        public void updatedFavouritePlace(PlaceInfo placeInfo) {
            int index = mNearByPlaces.indexOf(placeInfo);

            Button button = (Button) mNearTouristPlacesHolderView.getChildAt(index);
            button.setText(placeInfo.name);
        }

        @Override
        public void deleteFavouritePlace(PlaceInfo placeInfo) {
            int index = mNearByPlaces.indexOf(placeInfo);
            mNearTouristPlacesHolderView.removeViewAt(index);
            mNearByPlaces.remove(index);
        }
    };

    private View.OnClickListener mNearByPlaceClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = mNearTouristPlacesHolderView.indexOfChild(v);
            addNearByPlaceFragment(mNearByPlaces.get(index));
        }
    };

    private View.OnClickListener mCreateButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Error")
                    .setMessage("Are you sure you want to delete this entry?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    };

    /*
    * Helper Methods
    * */
    private void loadAllViews(View view) {

        Button button = (Button) view.findViewById(R.id.create_button);
        button.setOnClickListener(mCreateButtonClickListener);

        mBrideFNEditText = (EditText)view.findViewById(R.id.bride_fn_editText);
        mBrideLNEditText = (EditText)view.findViewById(R.id.bride_ln_editText);
        mGroomFNEditText = (EditText)view.findViewById(R.id.groom_fn_editText);
        mGroomLNEditText = (EditText)view.findViewById(R.id.groom_ln_editText);
        mMarriageDateEditText = (EditText)view.findViewById(R.id.marriage_date_editText);
        mMarriageTimeEditText = (EditText)view.findViewById(R.id.marriage_time_editText);
        mBrideImageView = (ImageView)view.findViewById(R.id.bride_image_view);
        mBrideImageDeleteButton = (Button)view.findViewById(R.id.bride_image_delete_button);
        mGroomImageView = (ImageView)view.findViewById(R.id.groom_image_view);
        mGroomImageDeleteButton = (Button)view.findViewById(R.id.groom_image_delete_button);
        mAddNewEventButton = (Button)view.findViewById(R.id.add_new_event);
        mEventsHolder = (LinearLayout)view.findViewById(R.id.events_holder_view);
        mAddBrideContactDetailsButton = (Button) view.findViewById(R.id.add_bride_contact_details);
        mBrideContactsHolder= (LinearLayout)view.findViewById(R.id.bride_contact_details_holder_view);
        mAddGroomContactDetailsButton= (Button) view.findViewById(R.id.add_groom_contact_details);
        mGroomContactsHolder= (LinearLayout)view.findViewById(R.id.groom_contact_details_holder_view);
        mAdminEmailIdEditText = (EditText) view.findViewById(R.id.admin_email_id_editText);
        mAddAdminEmailIdButton = (Button) view.findViewById(R.id.add_admin_email_id_button);
        mAdminEmailIdsHolderView = (LinearLayout) view.findViewById(R.id.admin_email_ids_holder_view);
        mAddNearTouristPlacesButton = (Button) view.findViewById(R.id.add_near_tourist_place_button);
        mNearTouristPlacesHolderView = (LinearLayout) view.findViewById(R.id.near_tourist_places_holder_view);
    }

    private void setUpViewActions() {
        mMarriageDateEditText.setOnClickListener(mMarriageDateClickListener);
        mMarriageDateEditText.setOnFocusChangeListener(mMarriageDateFocusChangeListener);

        mMarriageTimeEditText.setOnClickListener(mMarriageTimeClickListener);
        mMarriageTimeEditText.setOnFocusChangeListener(mMarriageTimeFocusChangeListener);

        mBrideImageView.setOnClickListener(mChooseBrideImageClickListner);
        mBrideImageDeleteButton.setOnClickListener(mDeleteBrideImageClickListner);
        mGroomImageView.setOnClickListener(mChooseGroomImageClickListner);
        mGroomImageDeleteButton.setOnClickListener(mDeleteGroomImageClickListner);

        mAddNewEventButton.setOnClickListener(mAddNewEventClickListner);

        mAddBrideContactDetailsButton.setOnClickListener(mAddBrideContactDetailsClickListner);
        mAddGroomContactDetailsButton.setOnClickListener(mAddGroomContactDetailsClickListner);

        mAddAdminEmailIdButton.setOnClickListener(addEmailIdButtonListner);

        mAddNearTouristPlacesButton.setOnClickListener(mAddNearByPlaceClickListener);
    }

    private TextView getNewTextView() {
        TextView textView = new TextView(getContext());
        textView.setTextSize(20);
        return textView;
    }

    private void addCreateEventFragment(Event event) {
        CreateEventFragment createEventFragment= new CreateEventFragment();
        createEventFragment.event = event;
        createEventFragment.setOnEventCreateListner(mEventCreateListner );
        addFragment(R.id.container, createEventFragment);
    }

    private void addCreateContactFragment(Contact contact, Contact.ContactType type) {
        CreateContactFragment createContactFragment= new CreateContactFragment();
        createContactFragment.contact = contact;
        createContactFragment.contactType = type;
        createContactFragment.setOnCreateContactListner(mCreateContactListner);
        addFragment(R.id.container, createContactFragment);
    }

    private void handleImageCropActivityResult(Intent data, ImageView imageView, Button deleteButton) {
        Uri uri = data.getParcelableExtra("URI");
        if (uri != null) {
            imageView.setImageURI(uri);
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            deleteButton.setVisibility(View.GONE);
        }
    }

    private void addNearByPlaceFragment(PlaceInfo placeInfo) {
        CreateFavouritePlaceFragment createContactFragment= new CreateFavouritePlaceFragment();
        createContactFragment.placeInfo = placeInfo;
        createContactFragment.setOnCreateFavouritePlaceListener(onCreateFavouritePlaceFragmentListener);
        addFragment(R.id.container, createContactFragment);
    }
}

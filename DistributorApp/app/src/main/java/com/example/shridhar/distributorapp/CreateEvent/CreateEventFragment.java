package com.example.shridhar.distributorapp.CreateEvent;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.shridhar.distributorapp.AppComponents.AppFragment;
import com.example.shridhar.distributorapp.R;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CreateEventFragment extends AppFragment {

    private OnFragmentInteractionListener mListener;


    private EditText mEventNameEditText;
    private EditText mEventDateEditText;
    private EditText mEventTimeEditText;
    private CheckBox mEventAddressCheckBox;
    private EditText mEventAddressEditText;
    private Button mCreateEventButton;
    private Button mDeleteEventButton;

    public Event event;

    public CreateEventFragment() {
        // Required empty public constructor
    }

    private OnCreateEventFragmentInteractionListner mEventListner;
    public void setOnEventCreateListner(OnCreateEventFragmentInteractionListner listner) {
        mEventListner = listner;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        loadAllViews(view);
        setUpViewActions();
        updateViewsData();
        return view;
    }

    private void loadAllViews(View view) {
        mEventNameEditText = (EditText)view.findViewById(R.id.new_event_name_tf);
        mEventDateEditText = (EditText)view.findViewById(R.id.new_event_date_tf);
        mEventTimeEditText = (EditText)view.findViewById(R.id.new_event_time_tf);
        mEventAddressCheckBox = (CheckBox) view.findViewById(R.id.event_address_check_box);
        mEventAddressEditText = (EditText)view.findViewById(R.id.event_address_tf);
        mCreateEventButton = (Button)view.findViewById(R.id.create_event_button);
        mDeleteEventButton = (Button)view.findViewById(R.id.delete_event_button);
    }

    private void setUpViewActions() {
        mEventDateEditText.setOnClickListener(mEventDateClickListener);
        mEventDateEditText.setOnFocusChangeListener(mEventDateFocusChangeListener);

        mEventTimeEditText.setOnClickListener(mEventTimeClickListener);
        mEventTimeEditText.setOnFocusChangeListener(mEventTimeFocusChangeListener);

        mEventAddressCheckBox.setOnClickListener(mOnEventAddressSameAsMarriageClickListner );

        mCreateEventButton.setOnClickListener(mOnCreateEventClickListner);
        mDeleteEventButton.setOnClickListener(mOnDeleteEventClickListner);
    }

    private void updateViewsData() {
        if (event != null) {
            mEventNameEditText.setText(event.name);
            mEventDateEditText.setText(event.date);
            mEventTimeEditText.setText(event.time);
            mEventAddressCheckBox.setChecked(event.addressSameAsMarriage);
            mEventAddressEditText.setText(event.address);
            mCreateEventButton.setText(getResources().getString(R.string.event_update_button_title));
            mDeleteEventButton.setVisibility(View.VISIBLE);
        } else {
            mCreateEventButton.setText(getResources().getString(R.string.event_create_button_title));
            mDeleteEventButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    //Date Edit Text Related
    private View.OnClickListener mEventDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDateFragment();
        }
    };

    private View.OnFocusChangeListener mEventDateFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (!hasFocus) return;

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
                mEventDateEditText.setText(text);
            }
        }, year, month, day);
        dialog.show();
    }

    /*
    * Time Edit Text Related
    * */
    private View.OnClickListener mEventTimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showTimeFragment();
        }
    };

    private View.OnFocusChangeListener mEventTimeFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (!hasFocus) return;

            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    hideKeyboard();
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
                mEventTimeEditText.setText(text);
            }
        }, hour, minute,
                DateFormat.is24HourFormat(getContext()));
        timePickerDialog.show();
    }

    /*
    * Button Actions
    * */
    private View.OnClickListener mOnEventAddressSameAsMarriageClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mEventAddressCheckBox.isChecked()) {
                mEventAddressEditText.setVisibility(View.GONE);
            } else {
                mEventAddressEditText.setVisibility(View.VISIBLE);
            }
        }
    };

    private View.OnClickListener mOnCreateEventClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (event != null) {
                updateEvent(event);
                mEventListner.updatedEvent(event);
            } else {
                Event event = new Event();
                updateEvent(event);
                mEventListner.eventCreated(event);
            }

            popFragment();
        }
    };

    private View.OnClickListener mOnDeleteEventClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mEventListner.deleteEvent(event);
            popFragment();
        }
    };

    /*
    * Helper Methods
    * */

    private void updateEvent(Event event) {
        event.name = mEventNameEditText.getText().toString();
        event.date = mEventDateEditText.getText().toString();
        event.time = mEventTimeEditText.getText().toString();
        event.addressSameAsMarriage = mEventAddressCheckBox.isChecked();
        event.address = mEventAddressEditText.getText().toString();
    }

    public interface OnCreateEventFragmentInteractionListner {
        void eventCreated(Event event);
        void updatedEvent(Event event);
        void deleteEvent(Event event);
    }
}

package com.example.shridhar.distributorapp.FavouritePlaces;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.shridhar.distributorapp.AppComponents.AppFragment;
import com.example.shridhar.distributorapp.ImageCropperHelper.ImageCropperActivity;
import com.example.shridhar.distributorapp.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateFavouritePlaceFragment extends AppFragment {

    private Toolbar mToolbar;

    private EditText mNameEditText;
    private EditText mDistanceEditText;
    private ImageView mPlaceImage;
    private Button mCreatePlaceButton;
    private Button mDeletePlaceButton;

    public PlaceInfo placeInfo;
    private String mImageUri;

    private OnCreateFavouritePlaceFragmentListener mListener;
    public void setOnCreateFavouritePlaceListener(OnCreateFavouritePlaceFragmentListener listener) {
        mListener = listener;
    }


    public CreateFavouritePlaceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_favourite_place, container, false);

        loadAllViews(view);
        setUpViewActions();
        updateViewsData();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == getResources().getInteger(R.integer.favourite_place_image_request)) {
            Uri uri = data.getParcelableExtra("URI");
            if (uri != null) {
                mImageUri = uri.toString();
                mPlaceImage.setImageURI(uri);
            }
        }
    }

    /*
    * Button Actions
    * */
    private View.OnClickListener mOnChooseImageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), ImageCropperActivity.class);
            intent.putExtra("CropType", "RECT");
            intent.putExtra("AspectRatioFirst",4);
            intent.putExtra("AspectRatioSecond",3);
            startActivityForResult(intent, getResources().getInteger(R.integer.favourite_place_image_request));
        }
    };

    private View.OnClickListener mOnCreatePlaceClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (placeInfo != null) {
                updatePlaceInfo(placeInfo);
                mListener.updatedFavouritePlace(placeInfo);
            } else {
                PlaceInfo placeInfo = new PlaceInfo();
                updatePlaceInfo(placeInfo);
                mListener.favouritePlaceCreated(placeInfo);
            }

            popFragment();
        }
    };

    private View.OnClickListener mOnDeletePlaceClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mListener.deleteFavouritePlace(placeInfo);
            popFragment();
        }
    };

    /*
    * Helper Methods
    * */
    private void loadAllViews(View view) {
        mNameEditText = (EditText)view.findViewById(R.id.new_place_name_tf);
        mDistanceEditText = (EditText)view.findViewById(R.id.distance_tf);
        mPlaceImage = (ImageView) view.findViewById(R.id.place_image);
        mCreatePlaceButton = (Button)view.findViewById(R.id.add_place_button);
        mDeletePlaceButton = (Button)view.findViewById(R.id.delete_place_button);
    }

    private void setUpViewActions() {
        mPlaceImage.setOnClickListener(mOnChooseImageListener);
        mCreatePlaceButton.setOnClickListener(mOnCreatePlaceClickListener);
        mDeletePlaceButton.setOnClickListener(mOnDeletePlaceClickListner);
    }

    private void updateViewsData() {
        if (placeInfo != null) {
            mNameEditText.setText(placeInfo.name);
            mDistanceEditText.setText(placeInfo.distance);
            mImageUri = placeInfo.imageUri;
            if (placeInfo.imageUri != null) {
                mPlaceImage.setImageURI(Uri.parse(placeInfo.imageUri));
            }
            mCreatePlaceButton.setText(getResources().getString(R.string.update_place));
            mDeletePlaceButton.setVisibility(View.VISIBLE);
        } else {
            mCreatePlaceButton.setText(getResources().getString(R.string.add_place));
            mDeletePlaceButton.setVisibility(View.GONE);
        }
    }

    private void updatePlaceInfo(PlaceInfo contact) {
        contact.name = mNameEditText.getText().toString();
        contact.distance = mDistanceEditText.getText().toString();
        contact.imageUri = mImageUri;
    }

    public interface OnCreateFavouritePlaceFragmentListener {
        void favouritePlaceCreated(PlaceInfo placeInfo);
        void updatedFavouritePlace(PlaceInfo placeInfo);
        void deleteFavouritePlace(PlaceInfo placeInfo);
    }

}
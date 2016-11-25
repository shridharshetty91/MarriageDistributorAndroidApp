package com.example.shridhar.distributorapp.NewContactDetails;

import android.widget.EditText;

/**
 * Created by Shridhar on 10/27/16.
 */


public class Contact {

    public String name;
    public String phoneNumber;
    public ContactType type;

    public static enum ContactType {
        Bride,
        Groom
    }
}

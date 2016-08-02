package com.housing.studenthousing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Config;
import com.firebase.client.Firebase;

/**
 * Created by Ishan on 7/27/2016.
 */
public class AddPlaceActivity extends AppCompatActivity {

    public static final String FIREBASE_URL = "https://nanochat-8050a.firebaseio.com/placeDetails";

    private TextView placeName;
    private EditText unitNumber;
    private EditText rent;
    private EditText email;
    private EditText phoneNumber;
    private Button addPlaceButton;
    PlaceData placeData;
    public static final String EXTRA_OBJECT_KEY = "ExtraObjectKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_place_activity);

        placeName = (TextView) findViewById(R.id.placeName);
        unitNumber = (EditText) findViewById(R.id.unitNumber);
        rent = (EditText) findViewById(R.id.rent);
        email = (EditText) findViewById(R.id.email);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        addPlaceButton = (Button) findViewById(R.id.addPlaceButton);
        Firebase.setAndroidContext(this);

        final Firebase myFirebaseRef = new Firebase(FIREBASE_URL);

        final double lat = getIntent().getDoubleExtra(MainActivity.LAT_EXTRA, 0.0);
        final double lng = getIntent().getDoubleExtra(MainActivity.LNG_EXTRA, 0.0);
        final String name = getIntent().getStringExtra(MainActivity.PLACENAME_EXTRA);
        final String id = getIntent().getStringExtra(MainActivity.PLACEID_EXTRA);
        placeName.setText(name);

        addPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unit = unitNumber.getText().toString();
                String rentAmount = rent.getText().toString();
                String emailId = email.getText().toString();
                String phone = phoneNumber.getText().toString();

                PlaceDetails placeDetails = new PlaceDetails();
                placeDetails.setUnitNumber(unit);
                placeDetails.setRent(rentAmount);
                placeDetails.setEmail(emailId);
                placeDetails.setPhoneNumber(phone);
                placeDetails.setLng(lng);
                placeDetails.setPlaceName(name);
                placeDetails.setLat(lat);
                placeDetails.setId(id);

                Firebase myRef = myFirebaseRef.push();
                myRef.setValue(placeDetails);
                String placeKey =myRef.getKey();
                System.out.println("Key in AddPlaceActivity "+placeKey);
                placeDetails.setPlaceKey(placeKey);

                Intent i = new Intent(AddPlaceActivity.this, MainActivity.class);

                startActivity(i);
                finish();
            }
        });



    }

}

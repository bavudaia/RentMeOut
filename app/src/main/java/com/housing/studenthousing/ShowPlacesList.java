package com.housing.studenthousing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.core.Context;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by Ishan on 7/30/2016.
 */
public class ShowPlacesList extends AppCompatActivity {

    public static final String FIREBASE_URL = "https://nanochat-8050a.firebaseio.com/placeDetails";
    public static final String KEY_EXTRA = "PlaceID";
    private DatabaseReference mRef;
    private DatabaseReference mChatRef;
    private FirebaseRecyclerAdapter<PlaceDetails, PlaceDetailsHolder> mRecyclerViewAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        RecyclerView rv = (RecyclerView) findViewById(R.id.placeRecycler);
        rv.setLayoutManager(llm);

        mRef = FirebaseDatabase.getInstance().getReference();
        mChatRef = mRef.child("placeDetails");

        Query lastFifty = mChatRef.limitToLast(50);
        mRecyclerViewAdapter = new FirebaseRecyclerAdapter<PlaceDetails, PlaceDetailsHolder>(
                PlaceDetails.class, R.layout.place_row, PlaceDetailsHolder.class, lastFifty) {
            @Override
            protected void populateViewHolder(PlaceDetailsHolder placeDetailsHolder, PlaceDetails placeDetails, int position) {
                placeDetailsHolder.setPlaceNameCard(placeDetails.getPlaceName());
                placeDetailsHolder.setUnitNumberCard(placeDetails.getUnitNumber());
                placeDetailsHolder.setRentCard(placeDetails.getRent());
                placeDetailsHolder.setPlaceId((placeDetails.getId()));
            }
        };
        rv.setAdapter(mRecyclerViewAdapter);
    }

    public static class PlaceDetailsHolder extends RecyclerView.ViewHolder {

        View mView;
        CardView placeCard;
        TextView placeNameCard;
        TextView unitNumberCard, rentCard;
        TextView keyName;
        ImageButton editDetails, deleteCommunity;
        android.content.Context context;
        PlaceDetailsHolder(View itemView) {
            super(itemView);
            mView = itemView;
            placeCard = (CardView) itemView.findViewById(R.id.placeCard);
            editDetails = (ImageButton) itemView.findViewById(R.id.editDetails);
            deleteCommunity = (ImageButton) itemView.findViewById(R.id.deleteCommunity);
            context = itemView.getContext();

            deleteCommunity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    keyName = (TextView) mView.findViewById(R.id.key);
                    System.out.println("Key is" +keyName.getText());
                    String placeId  = (String) keyName.getText();
                    Firebase.setAndroidContext(context);
                    final Firebase myFirebaseRef = new Firebase(FIREBASE_URL);
                    com.firebase.client.Query q = myFirebaseRef.orderByChild("id").equalTo(placeId);
                    q.addChildEventListener(new com.firebase.client.ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            dataSnapshot.getRef().setValue(null);
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

                }
            });

            editDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 /* keyName = (TextView) mView.findViewById(R.id.key);
                    //System.out.println("Key in ediit is" +keyName.getText());
                    String placeId  = (String) keyName.getText();
                    System.out.println("Key in edit is" +placeId);
                    Intent i = new Intent(ShowPlacesList.this, ModifyPlaceDetails.class);
                    i.putExtra(KEY_EXTRA, placeId);
                    startActivity(i);*/
                }
            });

        }


        public void setPlaceId(String placeId) {
            System.out.println("keyyyyyyyyy "+placeId);
            keyName = (TextView) mView.findViewById(R.id.key);
            keyName.setText(placeId);
        }

        public void setPlaceNameCard(CharSequence name) {
            placeNameCard = (TextView) mView.findViewById(R.id.placeNameCard);
            placeNameCard.setText(name);
        }

        public void setUnitNumberCard(CharSequence unitNumber) {
            unitNumberCard = (TextView) mView.findViewById(R.id.unitNumberCard);
            unitNumberCard.setText(unitNumber);
        }

        public void setRentCard(String rent) {
            rentCard = (TextView) mView.findViewById(R.id.rentCard);
            rentCard.setText(rent);
        }
    }
}

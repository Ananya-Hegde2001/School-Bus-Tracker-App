package com.example.bustracker1;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.bustracker1.databinding.ActivityDrivermapBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class drivermap extends FragmentActivity implements OnMapReadyCallback {

    FirebaseAuth mAuth;
    GoogleMap mMap;

    private ActivityDrivermapBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://bustracker1-de711-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();


        binding = ActivityDrivermapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String uid = mAuth.getCurrentUser().getUid();
                String busno = dataSnapshot.child("Students").child(uid).child("busno").getValue().toString();

                if(dataSnapshot.child("Bus no").child(busno).hasChild("Location")) {

                    String slat = dataSnapshot.child("Bus no").child(busno).child("Location").child("latitude").getValue().toString();
                    String slon = dataSnapshot.child("Bus no").child(busno).child("Location").child("longitude").getValue().toString();

                    double lat = Double.parseDouble(slat);
                    double lon = Double.parseDouble(slon);

                    LatLng latLng = new LatLng(lat, lon);

                    // Add a marker in Sydney and move the camera
                    mMap.clear();
                    MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Bus");
                    mMap.addMarker(new MarkerOptions().position(latLng));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                }
                else{
                    Toast.makeText(drivermap.this, "Bus has not started its trip",
                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(drivermap.this, "Error while fetching data from database",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


}

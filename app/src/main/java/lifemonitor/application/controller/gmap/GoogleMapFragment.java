package lifemonitor.application.controller.gmap;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import lifemonitor.application.R;
import lifemonitor.application.helper.rest.RESTHelper;
import lifemonitor.application.model.jsonMapping.PharmacyPlace;

/**
 * Google Map to display pharmacies nearby.
 * @author Celia Cacciatore, Quentin Bailleul
 */
public class GoogleMapFragment extends Fragment implements LocationListener {

    private LocationManager locationManager;
    private MapView mapView;
    private Marker user;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return a new instance of fragment GoogleMapFragment.
     */
    public static GoogleMapFragment newInstance() {
        return new GoogleMapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_google_map, container, false);

        // Initialize map
        mapView = (MapView) v.findViewById(R.id.googleMap);
        mapView.onCreate(savedInstanceState);
        MapsInitializer.initialize(this.getActivity());

        // Create a user marker
        user = mapView.getMap().addMarker(new MarkerOptions().title(getString(R.string.self_position)).position(new LatLng(0, 0)));

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Allow GPS geolocation
            subscriptionGPS();
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(),R.string.activate_gps,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        // Deny GPS geolocation
        unsubscriptionGPS();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Subscription to GPS location.
     */
    public void subscriptionGPS() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
    }

    /**
     * Cancellation of subscription to GPS location.
     */
    public void unsubscriptionGPS() {
        locationManager.removeUpdates(this);
    }

    @Override
    public void onProviderDisabled(final String provider) {
        // If GPS is no more activated, deny the service
        if ("gps".equals(provider)) {
            unsubscriptionGPS();
        }
    }

    @Override
    public void onProviderEnabled(final String provider) {
        // If GPS is activated, allow the service
        if ("gps".equals(provider)) {
            subscriptionGPS();
        }
    }

    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) {}

    @Override
    public void onLocationChanged(final Location location) {
        // Geolocation : center on user's position
        mapView.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 14));
        // Update user marker's position
        user.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
        // Search and display pharmacies' locations
        addPharmaciesMarkers(performSearch());
    }

    /**
     * Search for pharmacies near the user's position.
     * In case of failure, return an empty list.
     * @return a list of pharmacies
     */
    public ArrayList<PharmacyPlace> performSearch() {
        RESTHelper<String> restHelper = new RESTHelper<>(getActivity());
        GooglePlacesSearch gps = new GooglePlacesSearch(user, restHelper, this);
        try {
            // Search for pharmacies
            return gps.performSearch();
        } catch (java.lang.Exception e) {
            Log.e("GoogleMapFragment", "Searching for pharmacies failed");
        }
        // Return an empty list in case of failure
        return new ArrayList<>();
    }

    /**
     * Display pharamacies' markers
     * @param pharmacies list of pharmacies
     */
    public void addPharmaciesMarkers(ArrayList<PharmacyPlace> pharmacies) {
        for (PharmacyPlace pharmacyPlace : pharmacies) {
            // Add green marker for each pharmacy
            mapView.getMap().addMarker(new MarkerOptions()
                    .title(pharmacyPlace.getName())
                    .position(new LatLng(pharmacyPlace.getLatitude(), pharmacyPlace.getLongitude()))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }
    }
}
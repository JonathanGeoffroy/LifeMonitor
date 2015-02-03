package lifemonitor.application.controller.gmap;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedList;
import java.util.List;

import lifemonitor.application.R;

/**
 * Google Map to display pharmacies nearby.
 * @author Celia Cacciatore, Quentin Bailleul
 */
public class GoogleMapFragment extends Fragment implements LocationListener {
    private List<Marker> markers;
    private LocationManager locationManager;
    private MapView mapView;
    private Marker user;

    private static final String PLACES_SEARCH_URL =  "https://maps.googleapis.com/maps/api/place/search/json?";

    private static final boolean PRINT_AS_STRING = false;

    public GoogleMapFragment() {
        markers = new LinkedList<Marker>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GoogleMapFragment.
     */
    public static GoogleMapFragment newInstance() {
        GoogleMapFragment fragment = new GoogleMapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_google_map, container, false);

        //googleMap = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.googleMap)).getMap();
        GooglePlacesSearch gps = new GooglePlacesSearch();
        try {
            gps.performSearch();
            Log.e("","try");
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        mapView = (MapView) v.findViewById(R.id.googleMap);
        mapView.onCreate(savedInstanceState);
        MapsInitializer.initialize(this.getActivity());
        user = mapView.getMap().addMarker(new MarkerOptions().title("You are here").position(new LatLng(0, 0)));

        return v;
    }

    private void addMarker(GoogleMap map) {
//        Marker marker = map.addMarker(
//                new MarkerOptions()
//                        .position()
//                        .title()
//                        .snippet()
//        );
//        markers.add(marker);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        locationManager = (LocationManager) this.getActivity().getSystemService(this.getActivity().LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            subscriptionGPS();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        unsubscriptionGPS();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
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
    public void onLocationChanged(final Location location) {
        mapView.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
        user.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onProviderDisabled(final String provider) {
        // If GPS is no more activated, unsubscription
        if("gps".equals(provider)) {
            unsubscriptionGPS();
        }
    }

    @Override
    public void onProviderEnabled(final String provider) {
        // If GPS is activated, subscription
        if("gps".equals(provider)) {
            subscriptionGPS();
        }
    }

    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) { }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
    }


}

package lifemonitor.application.controller.gmap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.Marker;

import java.util.LinkedList;
import java.util.List;

import lifemonitor.application.R;

/**
 * Google Map to display pharmacies nearby.
 * @author Celia Cacciatore, Quentin Bailleul
 */
public class GoogleMapFragment extends Fragment {
    private MapView mapView;
    private List<Marker> markers;

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

        mapView = (MapView) v.findViewById(R.id.googleMap);
        mapView.onCreate(savedInstanceState);
        MapsInitializer.initialize(this.getActivity());

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
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
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

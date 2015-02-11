package lifemonitor.application.controller.gmap;

import android.util.Log;

import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import lifemonitor.application.helper.rest.RESTHelper;
import lifemonitor.application.helper.rest.listeners.SingleResultRESTListener;
import lifemonitor.application.model.jsonMapping.PharmacyPlace;

/**
 * Make a request to Google's webservices to retrieve pharmacies' locations.<br/>
 * Parse and give the list of pharmacies to the Map fragment.
 * @author Célia Cacciatore, Quentin Bailleul
 */
public class GooglePlacesSearch {

    // Browser API key
    private static final String API_KEY = "AIzaSyAE4SA-4up7qcY95r9xjPIk_d2WIn6T58o";

    // The different Places API endpoints.
    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";

    // User's latitude and longitude
    private double latitude;
    private double longitude;

    private RESTHelper restHelper;
    private GoogleMapFragment googleMapFragment;

    /**
     * @param user user's marker to retrieve its location
     * @param restHelper a rest helper to perform the search request
     * @param googleMapFragment the parent activity to which it will return the result
     */
    public GooglePlacesSearch(Marker user, RESTHelper restHelper, GoogleMapFragment googleMapFragment) {
        this.latitude = user.getPosition().latitude;
        this.longitude = user.getPosition().longitude;
        this.restHelper = restHelper;
        this.googleMapFragment = googleMapFragment;
    }

    /**
     * Search for pharmacies nearby
     * @return a list of pharmacies
     */
    public ArrayList<PharmacyPlace> performSearch() {
        // Create URL with required parameters
        StringBuilder requestUrl = new StringBuilder()
                .append(PLACES_SEARCH_URL)
                .append("key=").append(API_KEY)
                .append("&types=pharmacy&location=")
                .append(latitude).append(",").append(longitude)
                .append("&radius=1000&sensor=false");

        // Make search request
        this.restHelper.makeRequest(requestUrl.toString(), new SingleResultRESTListener<String>() {
            @Override
            public void onGetResponse(String result) {
                // Parse the result of search and
                // Give the list of pharmacies to the fragment
                GooglePlacesSearch.this.googleMapFragment.addPharmaciesMarkers(parsePharmacyPlaces(result));
            }
            @Override
            public void onError() {
                Log.e("GooglePlacesSearch", "Error during pharmacies search request");
            }
        });
        return new ArrayList<>();
    }

    /**
     * Parse JSON result containing pharmacies' places
     * @param jsonResult string representation of json
     * @return the list of pharmacies contained in json result
     */
    public ArrayList parsePharmacyPlaces(String jsonResult) {
        ArrayList<PharmacyPlace> pharmacyPlacesList = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(jsonResult);
            // Beginning of parsing
            JSONArray arr = obj.getJSONArray("results");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject loc = arr.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");
                PharmacyPlace pharmacyPlace = new PharmacyPlace(loc.getDouble("lat"), loc.getDouble("lng"), arr.getJSONObject(i).getString("name"));
                pharmacyPlacesList.add(pharmacyPlace);
            }
        } catch (JSONException e) {
            Log.e("GooglePlacesSearch", "Error during parsing the pharmacies search. Please check Google's webservices.");
        }
        return pharmacyPlacesList;
    }
}
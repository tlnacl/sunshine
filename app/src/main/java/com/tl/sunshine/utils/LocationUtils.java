package com.tl.sunshine.utils;

import android.content.Context;
import android.location.Location;

import com.tl.sunshine.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by tlnacl on 23/12/14.
 */
public class LocationUtils {

    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    /*
     * Constants for location update parameters
     */
    // Milliseconds per second
    public static final int MILLISECONDS_PER_SECOND = 1000;

    // The update interval
    public static final int UPDATE_INTERVAL_IN_SECONDS = 5;

    // A fast interval ceiling
    public static final int FAST_CEILING_IN_SECONDS = 1;

    // Update interval in milliseconds
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS =
            MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;

    // A fast ceiling of update intervals, used when the app is visible
    public static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS =
            MILLISECONDS_PER_SECOND * FAST_CEILING_IN_SECONDS;

    // Create an empty string for initializing strings
    public static final String EMPTY_STRING = "";

    /**
     * Get the latitude and longitude from the Location object returned by
     * Location Services.
     *
     * @param currentLocation A Location object containing the current location
     * @return The latitude and longitude of the current location, or null if no
     * location is available.
     */
    public static String getLatLng(Context context, Location currentLocation) {
        // If the location is valid
        if (currentLocation != null) {

            // Return the latitude and longitude as strings
            return context.getString(
                    R.string.latitude_longitude,
                    currentLocation.getLatitude(),
                    currentLocation.getLongitude());
        } else {

            // Otherwise, return the empty string
            return EMPTY_STRING;
        }
    }

    public static LatLngBounds getBounds(Location location, double distance) {
        return getBounds(location.getLatitude(), location.getLongitude(), distance);
    }

    /**
     * Finding boundaries for location and distance in kilometers
     */
    public static LatLngBounds getBounds(double lat, double lng, double distance) {
        final double radius = 6371.009; // In Km

        double maxLat = lat + Math.toDegrees(distance/radius);
        double minLat = lat - Math.toDegrees(distance/radius);

        double maxLng = lng + Math.toDegrees(distance/radius) / Math.cos(Math.toRadians(lat));
        double minLng = lng - Math.toDegrees(distance/radius) / Math.cos(Math.toRadians(lat));

        return new LatLngBounds(new LatLng(minLat, minLng), new LatLng(maxLat, maxLng));
    }
}

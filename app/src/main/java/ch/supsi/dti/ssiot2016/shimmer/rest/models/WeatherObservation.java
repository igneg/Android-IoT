package ch.supsi.dti.ssiot2016.shimmer.rest.models;


import com.google.gson.annotations.SerializedName;

public class WeatherObservation {

    @SerializedName("elevation")
    private int mElevation;

    @SerializedName("lng")
    private double mLongitude;

    @SerializedName("lat")
    private double mLatitude;

    public int getElevation() {
        return mElevation;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    @Override
    public String toString() {
        return "WeatherObservation { Elevation = " + mElevation + ", Longitude = " + mLongitude + ", Latitude = " + mLatitude + '}';
    }
}

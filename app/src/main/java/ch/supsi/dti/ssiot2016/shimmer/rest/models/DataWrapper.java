package ch.supsi.dti.ssiot2016.shimmer.rest.models;

import com.google.gson.annotations.SerializedName;

public class DataWrapper {

    @SerializedName("weatherObservation")
    private WeatherObservation mWeatherObservation;

    public DataWrapper() {
    }

    public WeatherObservation getWeatherObservation() {
        return mWeatherObservation;
    }

    @Override
    public String toString() {
        return mWeatherObservation.toString();
    }
}

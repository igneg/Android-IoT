package ch.supsi.dti.ssiot2016.shimmer.fragment;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import ch.supsi.dti.ssiot2016.shimmer.R;
import ch.supsi.dti.ssiot2016.shimmer.rest.RestClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StethoDemoTabFragment extends BaseTabFragment{

    /**
     * String used for logging purposes
     */
    private static final String TAG = StethoDemoTabFragment.class.getSimpleName();


    /**
     * Holds fragment's views
     */
    private ViewHolder mViewHolder;

    /**
     * New instance
     * @param fragmentId the id number of the fragment
     * @return a new BaseFragment instance
     */
    public static BaseTabFragment newInstance(int fragmentId){
        return  BaseTabFragment.newInstance(fragmentId, R.layout.fragment_stetho_demo, new StethoDemoTabFragment());
    }

    /**
     * Method used to initialize views
     *
     * @param rootView the main view
     */
    @Override
    public void initViews(View rootView) {

        // inits the view holder
        mViewHolder = new ViewHolder(rootView);

        // events
        mViewHolder.btnSendRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: ");

                Call<ResponseBody> call = RestClient.getInstance().getWeatherInfo();
                call.enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        Log.d(TAG, "onResponse() -> " + response.body().toString());

                        try {
                            mViewHolder.tvLogRequest.setText(response.body().string());
                        }
                        catch (IOException e) {

                            Log.e(TAG, "Failed to parse response body: ", e);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e(TAG, "onFailure() -> ", t);
                    }
                });
            }
        });
    }

    private static class ViewHolder {

        TextView tvLogRequest;
        Button btnSendRequest;

        ViewHolder(View rootView){

            tvLogRequest   = (TextView) rootView.findViewById(R.id.tvLogRequest);
            btnSendRequest = (Button) rootView.findViewById(R.id.tvSendRequest);
        }
    }
}

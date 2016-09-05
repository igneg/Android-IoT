package ch.supsi.dti.ssiot2016.shimmer.fragment;


import android.bluetooth.BluetoothDevice;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ch.supsi.dti.ssiot2016.shimmer.R;

public class ShimmerTabFragment extends BaseTabFragment implements DevicesListFragment.OnDeviceSelectedListener{

    /**
     * String used for logging purposes
     */
    private static final String TAG = ShimmerTabFragment.class.getSimpleName();

    /**
     * Preferences keys
     */
    private static final String PREFS_DEVICE_NAME_KEY    = "DeviceName";
    private static final String PREFS_DEVICE_ADDRESS_KEY = "DeviceAddress";

    /**
     * Support class for this fragment's subviews
     */
    private ViewHolder mViewHolder;

    /**
     * Current selected device
     */
    private BluetoothDevice mSelectedDevice;

    /**
     * New instance
     * @param fragmentId the id number of the fragment
     * @return a new BaseFragment instance
     */
    public static BaseTabFragment newInstance(int fragmentId){
        return  BaseTabFragment.newInstance(fragmentId, R.layout.fragment_shimmer, new ShimmerTabFragment());
    }

    /**
     * Method used to initialize views
     *
     * @param rootView the main view
     */
    @Override
    public void initViews(View rootView) {

        /**
         * Initializes view holder
         */
        mViewHolder = new ViewHolder(rootView);

        /**
         * Sets up texts and buttons
         */
        mViewHolder.tvDeviceName.setText(R.string.pair_no_sensor_selected);
        mViewHolder.tvDeviceAddress.setText("");
        mViewHolder.btnConnectDisconnect.setEnabled(false);

        /**
         * Sets up the click listeners
         */
        mViewHolder.btnSelectSensor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DialogFragment devicesListDialog = DevicesListFragment.newInstance(ShimmerTabFragment.this);
                devicesListDialog.show(getChildFragmentManager(), DevicesListFragment.class.getSimpleName());
            }
        });
        mViewHolder.btnConnectDisconnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO: 05/09/16 -> check sensor status (connected and disconnected)

            }
        });
    }

    /**
     * Called when a bluetooth device is selected from the list of paired devices
     * @param device
     */
    @Override
    public void onDeviceSelected(BluetoothDevice device) {

        Log.d(TAG, "onDeviceSelected(): " + "device = [" + device + "]");
        setupDeviceView(device);
    }

    /**
     * Sets up the device view
     */
    private void setupDeviceView(BluetoothDevice device){

        /**
         * Updates the selected device
         */
        mSelectedDevice = device;

        // TODO: 05/09/16 -> set device to service

        /**
         * Adjusts views
         */
        mViewHolder.tvDeviceName.setText(device.getName().trim());
        mViewHolder.tvDeviceAddress.setText(device.getAddress().trim());
        mViewHolder.btnConnectDisconnect.setEnabled(true);
    }

    /**
     * View holder class: utils to hold subviews
     */
    private static class ViewHolder {

        // selected
        TextView tvDeviceName;
        TextView tvDeviceAddress;
        Button btnSelectSensor;
        Button btnConnectDisconnect;

        public ViewHolder(View rootView){

            // selected
            tvDeviceName         = (TextView) rootView.findViewById(R.id.tvDeviceName);
            tvDeviceAddress      = (TextView) rootView.findViewById(R.id.tvDeviceAddress);
            btnSelectSensor      = (Button) rootView.findViewById(R.id.btnSelectSensor);
            btnConnectDisconnect = (Button) rootView.findViewById(R.id.btnConnectDisconnect);
        }
    }
}

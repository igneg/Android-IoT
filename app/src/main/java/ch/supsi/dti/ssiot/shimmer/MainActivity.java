package ch.supsi.dti.ssiot.shimmer;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import ch.supsi.dti.ssiot.shimmer.adapter.TabsFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    /**
     * Tag used for logging purposes
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Int code used as discriminant
     */
    private static final int REQUEST_ENABLE_BT = 0x100;

    /**
     * Service used to connect and grab data from a Shimmer sensor
     */
    private MyService mShimmerService;

    /**
     * Flag that indicates if the service is correctly binded
     */
    private boolean mShimmerServiceBind;

    /**
     * PagerAdapter, it's a fragments holder
     */
    private TabsFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Get the ViewPager and sets the PagerAdapter
         */
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerAdapter = new TabsFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(mPagerAdapter);
        mPagerAdapter.setup(viewPager, (TabLayout) findViewById(R.id.tablayout));

        /**
         * Bluetooth
         */
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        /**
         * Checks if bluetooth is supported
         */
        if (bluetoothAdapter == null) {

            Log.i(TAG, "Bluetooth adapter not supported...");

            // show a dialog...
            new AlertDialog.Builder(this)
                    .setTitle(BluetoothAdapter.class.getSimpleName())
                    .setMessage(getString(R.string.bluetooth_no_adapter))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        /**
         * Checks if bluetooth is enabled, if not, asks for permissions to enable it
         */
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        /**
         * Starts the shimmer service
         */
        Intent startIntent = new Intent(this, MyService.class);
        startIntent.setAction(MyService.ACTION_START);
        startService(startIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        /**
         * If is the request we are looking for, i.e, the one who asks to enable bluetooth
         */
        if (requestCode == REQUEST_ENABLE_BT){

            if (resultCode != RESULT_OK){ // something went wrong, i.e, the user pressed "No"

                // show a dialog...
                new AlertDialog.Builder(this)
                        .setTitle(BluetoothAdapter.class.getSimpleName())
                        .setMessage(getString(R.string.bluetooth_not_granted))
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
        Log.d(TAG, "onStart()");

        Intent intent = new Intent(this, MyService.class);

        getApplicationContext().bindService(intent, mShimmerServiceConnection, Context.BIND_AUTO_CREATE);

        if (isShimmerServiceRunning()) {
            Log.d(TAG,"Shimmer service started!");
        }
        else {
            Log.d(TAG, "Shimmer service not started!");
        }
    }

    @Override
    protected void onStop() {

        super.onStop();

        if (mShimmerServiceBind){
            getApplicationContext().unbindService(mShimmerServiceConnection);
        }
    }

    /**
     * Gets the shimmer service
     * @return a MyService object
     */
    public MyService getShimmerService() {
        return mShimmerService;
    }

    /**
     * Checks if the service is currently running or not
     * @return <code>true</code> if it is running, otherwise <code>false</code>
     */
    protected boolean isShimmerServiceRunning() {

        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {

            if (MyService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }

        return false;
    }

    protected ServiceConnection mShimmerServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            Log.d(TAG, "---> ServiceConnection#onServiceConnected()");

            MyService.LocalBinder binder = (MyService.LocalBinder) iBinder;
            mShimmerService = binder.getService();
            mShimmerServiceBind = true;
            mPagerAdapter.notifyAllFragments(MyService.NOTIFY_SERVICE_STARTED);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            Log.d(TAG, "---> ServiceConnection#onServiceDisconnected()");
            mShimmerServiceBind = false;
        }
    };

    public void notifyNewData(Object data) {
        mPagerAdapter.notifyFragment(1, MyService.MESSAGE_NEW_DATA, data);
    }
}

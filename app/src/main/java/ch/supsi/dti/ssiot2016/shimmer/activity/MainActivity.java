package ch.supsi.dti.ssiot2016.shimmer.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import ch.supsi.dti.ssiot2016.shimmer.R;
import ch.supsi.dti.ssiot2016.shimmer.adapter.TabsFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    /**
     * Tag used for logging purposes
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Int code used as discriminant
     */
    private static final int REQUEST_ENABLE_BT = 0x100;

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
        final TabsFragmentPagerAdapter mPagerAdapter = new TabsFragmentPagerAdapter(getSupportFragmentManager(), this);
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
        else {
            setup();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        /**
         * If is the request we are looking for, i.e, the one who asks to enable bluetooth
         */
        if (requestCode == REQUEST_ENABLE_BT){

            if (resultCode == RESULT_OK){ // bluetooth enabled correctly
                setup();
            }
            else { // something went wrong, i.e, the user pressed "No"

                // show a dialog...
                new AlertDialog.Builder(this)
                        .setTitle(BluetoothAdapter.class.getSimpleName())
                        .setMessage(getString(R.string.bluetooth_no_adapter))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
    }

    /**
     * Sets up Shimmer service
     */
    private void setup(){
        Log.d(TAG, "setup()");
    }
}

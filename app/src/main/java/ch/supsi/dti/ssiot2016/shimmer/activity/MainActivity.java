package ch.supsi.dti.ssiot2016.shimmer.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ch.supsi.dti.ssiot2016.shimmer.R;
import ch.supsi.dti.ssiot2016.shimmer.adapter.TabsFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

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
    }
}

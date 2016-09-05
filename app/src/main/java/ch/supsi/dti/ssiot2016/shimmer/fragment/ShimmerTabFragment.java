package ch.supsi.dti.ssiot2016.shimmer.fragment;


import android.view.View;

import ch.supsi.dti.ssiot2016.shimmer.R;

public class ShimmerTabFragment extends BaseTabFragment{

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

        // empty



        // full
    }
}

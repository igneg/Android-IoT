package ch.supsi.dti.ssiot2016.shimmer.fragment;

import android.view.View;

import ch.supsi.dti.ssiot2016.shimmer.R;

public class EmptyTabFragment extends BaseTabFragment {

    /**
     * Resource id for the empty layout
     */
    public static final int LAYOUT_RESOURCE_ID = R.layout.fragment_empty;

    /**
     * New instance
     * @param fragmentId the id number of the fragment
     * @return a new BaseFragment instance
     */
    public static BaseTabFragment newInstance(int fragmentId){
        return BaseTabFragment.newInstance(fragmentId, 0, null);
    }

    @Override
    public void initViews(View rootView) {

    }
}
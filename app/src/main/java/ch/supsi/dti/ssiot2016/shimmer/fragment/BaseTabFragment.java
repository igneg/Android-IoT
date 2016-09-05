package ch.supsi.dti.ssiot2016.shimmer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseTabFragment extends Fragment {

    /**
     * Extras
     */
    public static final String EXTRA_RESOURCE_ID = "BaseTabFragment.ResourceId";
    public static final String EXTRA_FRAGMENT_ID = "BaseTabFragment.FragmentId";

    /**
     * The identification number of the fragment
     */
    protected int mFragmentId;

    /**
     * The resource id of the layout
     */
    private int mResourceId;

    /**
     * New instance
     * @param fragmentId the id number of the fragment
     * @param resourceId the resource id for fragment's layout
     * @param fragment fragment where to set bundle args
     * @return a new BaseFragment instance
     */
    public static BaseTabFragment newInstance(int fragmentId, int resourceId, BaseTabFragment fragment) {

        if(fragment == null || resourceId == 0){

            fragment   = new EmptyTabFragment();
            resourceId = EmptyTabFragment.LAYOUT_RESOURCE_ID;
        }

        Bundle args = new Bundle();
        args.putInt(EXTRA_FRAGMENT_ID, fragmentId);
        args.putInt(EXTRA_RESOURCE_ID, resourceId);

        fragment.setArguments(args);

        return fragment;
    }

    /**
     * New instance
     * @param fragmentId the id number of the fragment
     * @return a new BaseFragment instance
     */
    public static BaseTabFragment newInstance(int fragmentId) {
        return newInstance(fragmentId, 0, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mFragmentId = getArguments().getInt(EXTRA_FRAGMENT_ID);
        mResourceId = getArguments().getInt(EXTRA_RESOURCE_ID);

        View view = inflater.inflate(mResourceId, container, false);
        initMembers();
        initViews(view);

        return view;
    }

    /**
     * Method used to initialize member fields of the subclasses
     */
    public void initMembers() {
        Log.d(getClass().getSimpleName(), "initMembers()");
    }

    /**
     * Method used to initialize views of the subclasses
     * @param rootView the main view
     */
    public abstract void initViews(View rootView);
}
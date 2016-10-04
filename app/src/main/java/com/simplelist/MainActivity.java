package com.simplelist;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.simplelist.fragments.AddNameFragment;
import com.simplelist.fragments.ListNameFragment;

public class MainActivity extends AppCompatActivity implements AddNameFragment.OnAddNameListener,View.OnClickListener {

    private final static String TAG = MainActivity.class.getCanonicalName();

    private final String LIST_FRAGMENT = "ListFragment";
    private final String ADD_NAME_FRAGMENT = "AddNameFragment";

    private ListNameFragment mListNameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI();
    }

    /**
     *
     */
    private void initializeUI(){

        try {

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            //Instantiate some stuff here like view components
            mListNameFragment = new ListNameFragment();

            //Now you can set the fragment to be visible here
            setFragment(mListNameFragment);


            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(this);

        } catch (Exception e) {
            Log.e(TAG, "Error while initialize UI components and message ="
                    + e.getMessage());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                showAddNameDialog();
                break;
            default:
                break;
        }

    }

    /**
     *
     * @param frag
     */
    public void setFragment(Fragment frag)
    {
        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(R.id.container) == null) {
            fm.beginTransaction().add(R.id.container, frag,LIST_FRAGMENT).commit();
        }

    }

    /**
     *
     */
    private void showAddNameDialog() {
        FragmentManager fm = getFragmentManager();
        AddNameFragment mAddNameFragment = new AddNameFragment();
        mAddNameFragment.show(fm, ADD_NAME_FRAGMENT);
    }

    @Override
    public void onNameAdded(String firstName, String lastName) {
        ListNameFragment listNameFragment = (ListNameFragment)
                getFragmentManager().findFragmentByTag(LIST_FRAGMENT);

        if (listNameFragment != null) {
            listNameFragment.addNewName(firstName,lastName);
        } else {
            // Todo else case handling
        }
    }
}

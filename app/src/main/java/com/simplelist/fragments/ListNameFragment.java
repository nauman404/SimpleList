package com.simplelist.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simplelist.R;
import com.simplelist.adapters.UserAdapter;
import com.simplelist.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * ListNameFragment to list First and Last Name
 * @author by Nauman Ashraf on 9/11/2016.
 */
public class ListNameFragment extends Fragment {

    private final static String TAG = ListNameFragment.class.getCanonicalName();

    private ArrayList<User> mUsers;
    private Context mContext;
    private UserAdapter mUserAdapter;
    private View mViewListName = null;
    RecyclerView mUserRecycler;

    public ListNameFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext= context;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                mContext= activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnAddNameListener");
            }
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this Fragment so that it will not be destroyed when an orientation
        setRetainInstance(true);

        mUsers = new ArrayList<>();

        try{
            JSONArray values = setupDummyData();
            for(int i=0; i<values.length(); i++){
                JSONObject jsonObject = values.getJSONObject(i);
                String firstName = jsonObject.getString("firstName");
                String lastName = jsonObject.getString("lastName");
                mUsers.add(new User(firstName,lastName));
            }
        }catch (JSONException e){

        }

        mUserAdapter = new UserAdapter(getActivity(), mUsers);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewListName = inflater.inflate(R.layout.fragment_list_name,
                container, false);

        initializeUI();

        return mViewListName;
    }

    /**
     *
     */
    private void initializeUI(){

        try {

            // Lookup the recyclerview in activity layout

            mUserRecycler = (RecyclerView) mViewListName.findViewById(R.id.rvUsers);

            // Attach the userAdapter to the recyclerview to populate items
            mUserRecycler.setAdapter(mUserAdapter);
            // Set layout manager to position the items
            mUserRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        } catch (Exception e) {
            Log.e(TAG, "Error while initialize UI components and message ="
                    + e.getMessage());
        }
    }

    /**
     *
     * @return
     */
    public JSONArray setupDummyData(){

        JSONObject user1 = new JSONObject();
        JSONObject user2 = new JSONObject();
        JSONObject user3 = new JSONObject();
        JSONObject user4 = new JSONObject();
        JSONObject user5 = new JSONObject();
        try {
            user1.put("firstName", "John");
            user1.put("lastName", "Doe");
            user2.put("firstName", "Anna");
            user2.put("lastName", "Smith");
            user3.put("firstName", "Nauman");
            user3.put("lastName", "Ashraf");
            user4.put("firstName", "Angelia");
            user4.put("lastName", "Julie");
            user5.put("firstName", "Sunil");
            user5.put("lastName", "Kumar");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONArray usersJsonArray = new JSONArray();
        usersJsonArray.put(user1);
        usersJsonArray.put(user2);
        usersJsonArray.put(user3);
        usersJsonArray.put(user4);
        usersJsonArray.put(user5);

        return usersJsonArray;
    }

    public void addNewName(String firstName, String lastName){
        mUsers.add(new User(firstName, lastName));
        mUserAdapter.notifyDataSetChanged();
    }
}

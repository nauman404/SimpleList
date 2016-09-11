package com.simplelist.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.simplelist.R;
import com.simplelist.utils.StaticMethod;

/**
 * Dialog Fragment to add First and Last Name
 * @author by Nauman Ashraf on 9/11/2016.
 */
public class AddNameFragment extends DialogFragment implements View.OnClickListener {

    private final static String TAG = AddNameFragment.class.getCanonicalName();

    private EditText etFirstName;
    private EditText etLastName;
    private Button btnAdd;
    private Button btnCancel;

    private View viewAddName = null;
    private Context localContext=null;
    private OnAddNameListener mCallback;
    private Activity activity;

    public AddNameFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            activity = context instanceof Activity ? (Activity) context : null;
            mCallback = (OnAddNameListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAddNameListener");
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                mCallback = (OnAddNameListener) activity;

            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnAddNameListener");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //setLocalContext(container.getContext());

        viewAddName = inflater.inflate(R.layout.fragment_add_name, container);

        initializeUI();

        return viewAddName;
    }

    /**
     *
     */
    private void initializeUI(){

        try {

            getDialog().setTitle("Enter Name");

            etFirstName = (EditText) viewAddName.findViewById(R.id.etFirstName);
            etLastName = (EditText) viewAddName.findViewById(R.id.etLastName);
            btnAdd = (Button) viewAddName.findViewById(R.id.btnAdd);
            btnCancel = (Button) viewAddName.findViewById(R.id.btnCancel);
            btnAdd.setOnClickListener(this);
            btnCancel.setOnClickListener(this);

            // Show soft keyboard automatically
            etFirstName.requestFocus();

        } catch (Exception e) {
            Log.e(TAG, "Error while initialize UI components and message ="
                    + e.getMessage());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                if (validate()) {
                    try{
                        String firstName = StaticMethod.capitalize(etFirstName.getText().toString());
                        String lastName = StaticMethod.capitalize(etLastName.getText().toString());
                        mCallback.onNameAdded(firstName,lastName);
                        this.dismiss();
                    }catch (ClassCastException cce) {
                        Log.e(TAG, cce.getMessage());
                    }
                }
                break;
            case R.id.btnCancel:
                this.dismiss();
                break;
            default:
                break;
        }

    }


    /**
     * To Validate input fields
     * @return
     */
    private boolean validate() {
        boolean validated = true;

        if (etFirstName.getText().toString().trim().equals("") || etFirstName.getText().toString().trim().length() == 0) {
            etFirstName.setError("Please enter First Name");
            validated = false;
        }

        if (etLastName.getText().toString().trim().equals("") || etLastName.getText().toString().trim().length() == 0) {
            etLastName.setError("Please enter Last Name");
            validated = false;
        }

        return validated;
    }


    /**
     * @return the localContext
     */
    public Context getLocalContext() {
        return localContext;
    }

    /**
     * @param localContext the localContext to set
     */
    public void setLocalContext(Context localContext) {
        this.localContext = localContext;
    }

    /**
     *
     */
    public interface OnAddNameListener{
        public void onNameAdded(String firstName, String lastName);
    }
}

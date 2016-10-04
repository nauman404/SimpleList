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

import static com.simplelist.R.id.etFirstName;

/**
 * Dialog Fragment to add First and Last Name
 * @author by Nauman Ashraf on 9/11/2016.
 */
public class AddNameFragment extends DialogFragment implements View.OnClickListener {

    private final static String TAG = AddNameFragment.class.getCanonicalName();

    private EditText mFirstNameEdit;
    private EditText mLastNameEdit;
    private Button mAddBtn;
    private Button mCancelBtn;

    private View mAddNameView = null;
    private Context mContext = null;
    private OnAddNameListener mCallback;
    private Activity mActivity;

    public AddNameFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container mActivity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mActivity = context instanceof Activity ? (Activity) context : null;
            mCallback = (OnAddNameListener) mActivity;

        } catch (ClassCastException e) {
            throw new ClassCastException(mActivity.toString()
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

        //setmContext(container.getContext());

        mAddNameView = inflater.inflate(R.layout.fragment_add_name, container);

        initializeUI();

        return mAddNameView;
    }

    /**
     *
     */
    private void initializeUI(){

        try {

            getDialog().setTitle("Enter Name");

            mFirstNameEdit = (EditText) mAddNameView.findViewById(etFirstName);
            mLastNameEdit = (EditText) mAddNameView.findViewById(R.id.etLastName);
            mAddBtn = (Button) mAddNameView.findViewById(R.id.btnAdd);
            mCancelBtn = (Button) mAddNameView.findViewById(R.id.btnCancel);
            mAddBtn.setOnClickListener(this);
            mCancelBtn.setOnClickListener(this);

            // Show soft keyboard automatically
            mFirstNameEdit.requestFocus();

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
                        String firstName = StaticMethod.capitalize(mFirstNameEdit.getText().toString());
                        String lastName = StaticMethod.capitalize(mLastNameEdit.getText().toString());
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

        if (mFirstNameEdit.getText().toString().trim().equals("") || mFirstNameEdit.getText().toString().trim().length() == 0) {
            mFirstNameEdit.setError("Please enter First Name");
            validated = false;
        }

        if (mLastNameEdit.getText().toString().trim().equals("") || mLastNameEdit.getText().toString().trim().length() == 0) {
            mLastNameEdit.setError("Please enter Last Name");
            validated = false;
        }

        return validated;
    }


    /**
     * @return the mContext
     */
    public Context getmContext() {
        return mContext;
    }

    /**
     * @param mContext the mContext to set
     */
    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    /**
     *
     */
    public interface OnAddNameListener{
        public void onNameAdded(String firstName, String lastName);
    }
}

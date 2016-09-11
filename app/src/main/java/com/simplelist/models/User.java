package com.simplelist.models;

import java.io.Serializable;

/**
 * The User model class
 * @author Nauman Ashraf
 */
public class User implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -6732971786342725319L;

    private String mFirstName;
    private String mLastName;

    public User(String firstName, String lastName) {
        mFirstName = firstName;
        mLastName = lastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName = lastName;
    }

}

package com.averageloser.shipping;

import android.support.annotation.Nullable;

/**
 * Created by tom on 12/11/2015.
 * <p/>
 * As you can guess, this POJO represents a Person, encapsulating a first and last name.
 */

public class Person {
    private String firstName;
    private String lastName;

    public Person(String fName, String lName) {
        setFirstName(fName);
        setLastName(lName);
    }

    public Person() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (!firstName.isEmpty()) {
            this.firstName = firstName;
        } else {
            throw new IllegalStateException("Input must not be empty!");
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (!lastName.isEmpty()) {
            this.lastName = lastName;
        } else {
            throw new IllegalStateException("Input must not be empty!");
        }
    }

    public String toStringLast() {
        return getLastName() + " " + getFirstName();
    }

    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}

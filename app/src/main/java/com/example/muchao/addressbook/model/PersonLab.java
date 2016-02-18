package com.example.muchao.addressbook.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by muchao on 16/2/18.
 */
public class PersonLab {
    private static final String TAG = PersonLab.class.getSimpleName();
    private static final String FILE_NAME = "persons.json";

    private ArrayList<Person> mPersons;

    private static PersonLab mPersonLab;
    private Context mContext;

    private PersonLab(Context appContext) {
        mContext = appContext;
        mPersons = new ArrayList<Person>();

        load();
    }

    public static PersonLab getInstance(Context appContext) {
        if (mPersonLab == null) {
            mPersonLab = new PersonLab(appContext);
        }
        return mPersonLab;
    }

    public Person getPerson(UUID personId) {
        for (Person person : mPersons
                ) {
            if (person.getId().equals(personId)) {
                return person;
            }
        }
        return null;
    }

    public ArrayList<Person> getmPersons() {
        return mPersons;
    }

    public boolean load() {
        // TODO need to implement
        for (int i = 0; i < 10; i++) {
            Person person = new Person();
            person.setName("Name #" + i);
            person.setPhone("13310001000");
            mPersons.add(person);
        }
        return true;
    }

    public void add(Person person) {
        mPersons.add(person);
    }

    public boolean store() {
        // TODO need to implement
        return true;
    }
}

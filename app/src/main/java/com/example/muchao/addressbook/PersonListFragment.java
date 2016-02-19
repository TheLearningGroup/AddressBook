package com.example.muchao.addressbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.muchao.addressbook.model.Person;
import com.example.muchao.addressbook.model.PersonLab;

import java.util.ArrayList;

/**
 * Created by muchao on 16/2/18.
 */
public class PersonListFragment extends ListFragment implements View.OnTouchListener {
    public static final int INTENT_REQUEST_ITEM = 0;
    private static String TAG = PersonListFragment.class.getSimpleName();

    private ArrayList<Person> mPersons;

    private EditText mSearch_et;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.list_title);

        mPersons = PersonLab.getInstance(getActivity().getApplicationContext()).getmPersons();
        ArrayAdapter<Person> adapter = new PersonsListAdapter(mPersons);
        setListAdapter(adapter);
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person_list, container, false);
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
//            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
//        }
        mSearch_et = (EditText) v.findViewById(R.id.search_et);
        mSearch_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mSearch_et.setText("");
                } else {
                    mSearch_et.setText(R.string.search_input_box);
                }
            }
        });
        mSearch_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchPerson(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 恢复activity时刷新视图
        ((PersonsListAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        mSearch_et.setText("");
        return true;
    }

    @Override
    public void onListItemClick(ListView lv, View v, int position, long id) {
        Person person = mPersons.get(position);
        Intent i = new Intent(getActivity(), PersonActivity.class);
        i.putExtra(PersonFragment.EXTRA_PERSON_ID, person.getId());
        startActivity(i);
    }

    private void searchPerson(String s) {
        // TODO: 16/2/18 search person
        Log.d(TAG, "searchPerson: " + s);
    }

    private class PersonsListAdapter extends ArrayAdapter<Person> {

        private TextView mName_tv;

        public PersonsListAdapter(ArrayList<Person> persons) {
            super(getActivity(), 0, persons);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_person_list_item, null);
            }

            mName_tv = (TextView) convertView.findViewById(R.id.name_tv);
            Person person = getItem(position);
            mName_tv.setText(person.getName());
            return convertView;
        }
    }
}

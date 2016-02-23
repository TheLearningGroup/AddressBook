package com.example.muchao.addressbook;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
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

    private PersonLab mPersons;
    private ArrayList<Person> mPersonsList;

//    private EditText mSearch_et;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.list_title);

        mPersons = PersonLab.getInstance(getActivity().getApplicationContext());
        mPersonsList = new ArrayList<Person>();
        mPersonsList.addAll(mPersons.clone());

        ArrayAdapter<Person> adapter = new PersonsListAdapter(mPersonsList);
        setListAdapter(adapter);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person_list, container, false);
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
//            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//        mSearch_et = (EditText) v.findViewById(R.id.search_et);
//        mSearch_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    mSearch_et.setText("");
//                } else {
//                    mSearch_et.setText(R.string.search_input_box);
//                }
//            }
//        });
//        mSearch_et.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                searchPerson(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
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
        Person person = mPersonsList.get(position);
        Intent i = new Intent(getActivity(), PersonActivity.class);
        i.putExtra(PersonFragment.EXTRA_PERSON_ID, person.getId());
        startActivity(i);
    }

    private void searchPerson(String s) {
        // TODO: 16/2/18 search person
        Log.d(TAG, "searchPerson: " + s);
        ArrayList<Person> persons = mPersons.filter(s);
        mPersonsList.clear();
        mPersonsList.addAll(persons);

        ((PersonsListAdapter) getListAdapter()).notifyDataSetChanged();
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
            if (person.getName() == null) {
                mName_tv.setText("");
            } else {
                mName_tv.setText(person.getName());
            }
            return convertView;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_person_list, menu);

        // 搜索框
        final SearchView searchView = (SearchView) menu.getItem(0).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchPerson(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchPerson(newText);
                return true;
            }
        });
        // 添加按钮
        menu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Person person = new Person();
                Intent i = new Intent(getActivity(), PersonActivity.class);
                i.putExtra(PersonFragment.EXTRA_PERSON_ID, person.getId());
                startActivity(i);
                return false;
            }
        });
    }
}

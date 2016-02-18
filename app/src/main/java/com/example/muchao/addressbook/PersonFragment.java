package com.example.muchao.addressbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.muchao.addressbook.model.Person;
import com.example.muchao.addressbook.model.PersonLab;

import java.util.UUID;

/**
 * Created by muchao on 16/2/18.
 */
public class PersonFragment extends Fragment implements View.OnClickListener {
    public static final String EXTRA_PERSON_ID = "person_id";
    private Person mPerson;

    private TextView mName_tv;
    private TextView mPhone_tv;
    private Button mMessage_btn;
    private Button mPhone_btn;
    private Button mShare_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.item_title);
        UUID personId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_PERSON_ID);
        mPerson = PersonLab.getInstance(getActivity().getApplication()).getPerson(personId);
        if (mPerson == null) {
            mPerson = new Person();
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person, parent, false);
        mName_tv = (TextView) v.findViewById(R.id.name_tv);
        mPhone_tv = (TextView) v.findViewById(R.id.phone_tv);
        mMessage_btn = (Button) v.findViewById(R.id.message_btn);
        mPhone_btn = (Button) v.findViewById(R.id.call_btn);
        mShare_btn = (Button) v.findViewById(R.id.share_person_btn);

        mName_tv.setText(mPerson.getName());
        mPhone_tv.setText(mPerson.getPhone());

        mMessage_btn.setOnClickListener(this);
        mPhone_btn.setOnClickListener(this);
        mShare_btn.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_btn:
            case R.id.message_btn:
            case R.id.share_person_btn:
                break;
            default:
                break;
        }
    }
}

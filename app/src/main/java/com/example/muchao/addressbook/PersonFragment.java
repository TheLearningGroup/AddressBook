package com.example.muchao.addressbook;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private Button mDelete_btn;
    private LinearLayout mEdit_layout;
    private LinearLayout mScan_layout;
    private LinearLayout mPhoto_layout;


    private EditText mName_et;
    private EditText mPhone_et;
    private Button mChoosePhoto_bt;
    private Button mCatchPhoto_bt;

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

        mEdit_layout = (LinearLayout) v.findViewById(R.id.edit_layout);
        mScan_layout = (LinearLayout) v.findViewById(R.id.scan_layout);
        mPhoto_layout = (LinearLayout) v.findViewById(R.id.photo_layout);

        mName_tv = (TextView) v.findViewById(R.id.name_tv);
        mPhone_tv = (TextView) v.findViewById(R.id.phone_tv);
        mMessage_btn = (Button) v.findViewById(R.id.message_btn);
        mPhone_btn = (Button) v.findViewById(R.id.call_btn);
        mShare_btn = (Button) v.findViewById(R.id.share_person_btn);
        mDelete_btn = (Button) v.findViewById(R.id.delete_person_btn);

        mMessage_btn.setOnClickListener(this);
        mPhone_btn.setOnClickListener(this);
        mShare_btn.setOnClickListener(this);
        mDelete_btn.setOnClickListener(this);

        mChoosePhoto_bt = (Button) v.findViewById(R.id.choose_photo_btn);
        mCatchPhoto_bt = (Button) v.findViewById(R.id.catch_photo_btn);

        mChoosePhoto_bt.setOnClickListener(this);
        mCatchPhoto_bt.setOnClickListener(this);

        if (mPerson.isNew()) {
            initEditView(v);
        } else {
            initScanView(v);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return v;
    }

    private void initScanView(View v) {
        mScan_layout.setVisibility(View.VISIBLE);
        mEdit_layout.setVisibility(View.GONE);
        mPhoto_layout.setVisibility(View.GONE);

        if (mPerson.getName() == null) {
            mName_tv.setText("");
        } else {
            mName_tv.setText(mPerson.getName());
        }

        if (mPerson.getPhone() == null) {
            mPhone_tv.setText("");
        } else {
            mPhone_tv.setText(mPerson.getPhone());
        }
    }

    private void initEditView(View v) {
        mScan_layout.setVisibility(View.GONE);
        mPhoto_layout.setVisibility(View.VISIBLE);
        mEdit_layout.setVisibility(View.VISIBLE);

        if (!mPerson.isNew()) {
            if (mPerson.getName() == null) {
                mName_et.setText("");
            } else {
                mName_et.setText(mPerson.getName());
            }

            if (mPerson.getPhone() == null) {
                mPhone_et.setText("");
            } else {
                mPhone_et.setText(mPerson.getPhone());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_btn:
            case R.id.message_btn:
            case R.id.share_person_btn:
            case R.id.delete_person_btn:
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_person, menu);
        if (mPerson.isNew()) {
            // 隐藏编辑按钮
            menu.getItem(1).setVisible(false);
        } else {
            // 隐藏确定按钮
            menu.getItem(0).setVisible(false);
        }
    }
}

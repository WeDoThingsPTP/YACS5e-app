package com.ptpthingers.yacs5e_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.ptpthingers.synchronization.DBWrapper;

public class CharacterEditBasicInfoFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Character mCurrentChar;
    private TextInputEditText charNameEdit;
    private EditText charLvlEdit;
    private Spinner spinner;
    private String currentClass;

    private Context mContext;

    public CharacterEditBasicInfoFragment() {
        // Required empty public constructor
    }

    public static CharacterEditBasicInfoFragment newInstance(String uuid) {
        CharacterEditBasicInfoFragment fragment = new CharacterEditBasicInfoFragment();
        Bundle args = new Bundle();
        args.putString(MainActivity.CHAR_UUID, uuid);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        currentClass = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        currentClass = "";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String uuid = getArguments().getString(MainActivity.CHAR_UUID);
        mCurrentChar = new Gson().fromJson(DBWrapper.getCharEntity(uuid).getData(), Character.class);

        mContext = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_character_edit_basic_info, container, false);

        spinner = rootView.findViewById(R.id.character_class_input);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.classes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        charNameEdit = rootView.findViewById(R.id.character_name_input);
        charNameEdit.setText(mCurrentChar.getCharName());

        charLvlEdit = rootView.findViewById(R.id.character_level_input);
        try{
            charLvlEdit.setText(mCurrentChar.getLevels().get(currentClass));
        }
        catch (NullPointerException npe) {
            charLvlEdit.setText("0");
        }

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

        mCurrentChar.setCharName(charNameEdit.getText().toString());
        SharedPreferences accountSharedPreferences = getContext().getSharedPreferences("account", Context.MODE_PRIVATE);
        String username = accountSharedPreferences.getString("username", "");
        mCurrentChar.post(username);
    }
}

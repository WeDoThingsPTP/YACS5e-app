package com.ptpthingers.yacs5e_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptpthingers.synchronization.DBWrapper;

import java.util.LinkedList;

public class CharacterListFragment extends Fragment {

    public static final String CHARACTER_LIST = "character_list";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private int mCurrentVisiblePosition;
    private static CharacterAdapter mAdapter;
    private static LinkedList<String> mCharacterList;

    public CharacterListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentVisiblePosition = 0;
        mCharacterList = new LinkedList<>();
        try {
            mCharacterList.addAll(DBWrapper.getUuidList());
        } catch (NullPointerException npe) {
            Log.d("CharacterList", "onCreate: Empty List!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_character_list, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.character_recycler);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CharacterAdapter(mCharacterList);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_add_character);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCharacterList.add(new Character().post());
                mAdapter.notifyItemInserted(mCharacterList.size());
            }
        });

        return rootView;
    }

    public static void deleteItem(int position) {
        mCharacterList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public static void addItem(int position, String item) {
        mCharacterList.add(position, item);
        mAdapter.notifyItemInserted(position);
    }

    public static void refresh() {
        mAdapter.notifyDataSetChanged();
    }
}

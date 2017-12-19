package com.ptpthingers.yacs5e_app;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ptpthingers.synchronization.DBWrapper;

import java.util.LinkedList;
import java.util.List;

public class CharacterSheetTraitsFragment extends Fragment {

    public static final String CHAR_UUID = "character_uuid";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private static TraitsAdapter mAdapter;
    private static LinkedList<Feature> mTraitsList;
    private static Character mCurrentChar;

    public CharacterSheetTraitsFragment() {
        // Required empty public constructor
    }

    public static CharacterSheetTraitsFragment newInstance(String uuid) {
        CharacterSheetTraitsFragment fragment = new CharacterSheetTraitsFragment();
        Bundle args = new Bundle();
        args.putString(CHAR_UUID, uuid);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTraitsList = new LinkedList<>();
        String uuid = getArguments().getString(CHAR_UUID);
        mCurrentChar = new Gson().fromJson(DBWrapper.getCharEntity(uuid).getData(), Character.class);
        try {
            mTraitsList.addAll(mCurrentChar.getTraits());
        } catch (NullPointerException npe) {
        }

        for(int i = 0; i<4; i++)
            mTraitsList.add(new Feature());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_character_sheet_traits, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.traits_recycler);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TraitsAdapter(mTraitsList);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public class TraitsAdapter extends RecyclerView.Adapter<TraitsAdapter.TraitsViewHolder> {
        private List<Feature> mTraitsList;

        public TraitsAdapter(List<Feature> traitsList) {
            mTraitsList = traitsList;
        }

        @Override
        public void onBindViewHolder(TraitsViewHolder holder, int position) {
            holder.mTraitName.setText(mTraitsList.get(position).getName());
            holder.mTraitDesc.setText(mTraitsList.get(position).getDescription());
        }

        @Override
        public TraitsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.
                    from(parent.getContext())
                    .inflate(R.layout.trait_item, parent, false);

            return new TraitsViewHolder(itemView);
        }

        @Override
        public int getItemCount() {
            return mTraitsList.size();
        }

        public class TraitsViewHolder extends RecyclerView.ViewHolder {

            private TextView mTraitName;
            private TextView mTraitDesc;

            public TraitsViewHolder(final View v) {
                super(v);

                mTraitName = v.findViewById(R.id.trait_item_name);
                mTraitDesc = v.findViewById(R.id.trait_item_desc);
            }

        }
    }
}

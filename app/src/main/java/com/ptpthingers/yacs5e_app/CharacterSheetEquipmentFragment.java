package com.ptpthingers.yacs5e_app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

public class CharacterSheetEquipmentFragment extends Fragment {

    public static final String CHAR_UUID = "character_uuid";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private static EquipmentAdapter mAdapter;
    private static LinkedList<Item> mItemList;
    private static Character mCurrentChar;

    public CharacterSheetEquipmentFragment() {
        // Required empty public constructor
    }

    public static CharacterSheetEquipmentFragment newInstance(String uuid) {
        CharacterSheetEquipmentFragment fragment = new CharacterSheetEquipmentFragment();
        Bundle args = new Bundle();
        args.putString(CHAR_UUID, uuid);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemList = new LinkedList<>();
        String uuid = getArguments().getString(CHAR_UUID);
        mCurrentChar = new Gson().fromJson(DBWrapper.getCharEntity(uuid).getData(), Character.class);
        try {
            mItemList.addAll(mCurrentChar.getEquipment());
        } catch (NullPointerException npe) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_character_sheet_equipment, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.equipment_recycler);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new EquipmentAdapter(mItemList);
        mRecyclerView.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_add_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemList.add(new Item());
                mAdapter.notifyItemInserted(mItemList.size());
                mCurrentChar.setEquipment(mItemList);
                mCurrentChar.post(getContext().getSharedPreferences("account", Context.MODE_PRIVATE).getString("username", ""));
            }
        });

        return rootView;
    }

    public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder> {
        private List<Item> mItemList;

        public EquipmentAdapter(List<Item> items) {
            mItemList = items;
        }

        @Override
        public void onBindViewHolder(EquipmentViewHolder holder, int position) {
            holder.mItemName.setText(mItemList.get(position).getName());
            holder.mItemDesc.setText(mItemList.get(position).getDescription());
        }

        @Override
        public EquipmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.
                    from(parent.getContext())
                    .inflate(R.layout.trait_item, parent, false);

            return new EquipmentViewHolder(itemView);
        }

        @Override
        public int getItemCount() {
            return mItemList.size();
        }

        public class EquipmentViewHolder extends RecyclerView.ViewHolder {

            private final TextView mItemName;
            private final TextView mItemDesc;

            public EquipmentViewHolder(final View v) {
                super(v);

                mItemName = v.findViewById(R.id.trait_item_name);
                mItemDesc = v.findViewById(R.id.trait_item_desc);
            }
        }
    }
}

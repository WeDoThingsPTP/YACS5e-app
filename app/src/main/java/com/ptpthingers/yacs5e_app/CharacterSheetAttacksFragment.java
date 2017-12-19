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

public class CharacterSheetAttacksFragment extends Fragment {

    public static final String CHAR_UUID = "character_uuid";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private static AttacksAdapter mAdapter;
    private static LinkedList<Attack> mAttackList;
    private static Character mCurrentChar;

    public CharacterSheetAttacksFragment() {
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
        mAttackList = new LinkedList<>();
        String uuid = getArguments().getString(CHAR_UUID);
        mCurrentChar = new Gson().fromJson(DBWrapper.getCharEntity(uuid).getData(), Character.class);
        try {
            mAttackList.addAll(mCurrentChar.getmAttacks());
        } catch (NullPointerException npe) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_character_sheet_attacks, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.attacks_recycler);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AttacksAdapter(mAttackList);
        mRecyclerView.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_add_attack);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAttackList.add(new Attack());
                mAdapter.notifyItemInserted(mAttackList.size());
                mCurrentChar.setmAttacks(mAttackList);
                mCurrentChar.post(getContext().getSharedPreferences("account", Context.MODE_PRIVATE).getString("username", ""));
            }
        });

        return rootView;
    }

    public class AttacksAdapter extends RecyclerView.Adapter<AttacksAdapter.AttacksViewHolder> {
        private List<Attack> mAttackList;

        public AttacksAdapter(List<Attack> attackList) {
            mAttackList = attackList;
        }

        @Override
        public void onBindViewHolder(AttacksViewHolder holder, int position) {
            holder.mAttackName.setText(mAttackList.get(position).getName());
            holder.mAttackBonus.setText(Integer.toString(mAttackList.get(position).getmToHit()));
            holder.mAttackDamage.setText(mAttackList.get(position).getmDiceCount()+"d"+mAttackList.get(position).getmDiceType()+"+"+mAttackList.get(position).getmFlatBonus());
            holder.mAttackDamageType.setText(mAttackList.get(position).getmDamageType().toString());
            holder.mAttackDamageType.setAllCaps(false);
            holder.mAttackRange.setText(mAttackList.get(position).getmRange());
            holder.mAttackAbility.setText(Integer.toString(mAttackList.get(position).getmAbility().getModifier()));
        }

        @Override
        public AttacksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.
                    from(parent.getContext())
                    .inflate(R.layout.attack_item, parent, false);

            return new AttacksViewHolder(itemView);
        }

        @Override
        public int getItemCount() {
            return mAttackList.size();
        }

        public class AttacksViewHolder extends RecyclerView.ViewHolder {

            private final TextView mAttackName;
            private final TextView mAttackBonus;
            private final TextView mAttackDamage;
            private final TextView mAttackDamageType;
            private final TextView mAttackRange;
            private final TextView mAttackAbility;

            public AttacksViewHolder(final View v) {
                super(v);

                mAttackName = v.findViewById(R.id.attack_name);
                mAttackBonus = v.findViewById(R.id.attack_bonus);
                mAttackDamage = v.findViewById(R.id.attack_damage);
                mAttackDamageType = v.findViewById(R.id.attack_damage_type);
                mAttackRange = v.findViewById(R.id.attack_range);
                mAttackAbility = v.findViewById(R.id.attack_ability);
            }
        }
    }
}

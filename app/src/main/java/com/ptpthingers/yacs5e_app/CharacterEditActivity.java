package com.ptpthingers.yacs5e_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.ptpthingers.synchronization.DBWrapper;

public class CharacterEditActivity extends AppCompatActivity {

    public static final String CHAR_UUID = "character_uuid";
    private Character thisChar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle bundle = new Bundle();
            Fragment fragment;
            Class fragmentClass = null;
            bundle.putString(CHAR_UUID, thisChar.getmUuid());
            switch (item.getItemId()) {
                case R.id.navigation_basic_info:
                    fragmentClass = CharacterEditBasicInfoFragment.class;
                    break;
                case R.id.navigation_ability_scores:
                    fragmentClass = CharacterEditAbilityScoresFragment.class;
                    break;
                case R.id.navigation_proficiencies:
                    fragmentClass = CharacterEditProficienciesFragment.class;
                    break;
            }

            try {
                fragment = (Fragment) fragmentClass.newInstance();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.active_edit, fragment).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String uuid = getIntent().getStringExtra(CHAR_UUID);
        thisChar = new Gson().fromJson(DBWrapper.getCharEntity(uuid).getData(), Character.class);
        setContentView(R.layout.activity_character_edit);

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            Fragment fragment = null;
            Class fragmentClass = CharacterEditBasicInfoFragment.class;
            bundle.putString(CHAR_UUID, thisChar.getmUuid());

            try {
                fragment = (Fragment) fragmentClass.newInstance();
                fragment.setArguments(bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.active_edit, fragment)
                    .commit();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}

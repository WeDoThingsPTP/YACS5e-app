package com.ptpthingers.yacs5e_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.ptpthingers.synchronization.DBWrapper;

public class CharacterSheetActivity extends AppCompatActivity {

    public static final String CHAR_UUID = "character_uuid";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Character thisChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String uuid = getIntent().getStringExtra(CHAR_UUID);
        thisChar = new Gson().fromJson(DBWrapper.getCharEntity(uuid).getData(), Character.class);
        setContentView(R.layout.activity_character_sheet);

        Toolbar toolbar = (Toolbar) findViewById(R.id.sheet_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(thisChar.getCharName());
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.character_sheet_section);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_character_sheet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_character_sheet, container, false);
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            Fragment fragment = null;
            Class fragmentClass = null;
            bundle.putString(CHAR_UUID, getIntent().getStringExtra(CHAR_UUID));
            switch (position) {
                case 0:
                    fragmentClass = CharacterSheetAbilityScoresFragment.class;
                    break;
                case 1:
                    fragmentClass = CharacterSheetSkillsFragment.class;
                    break;
                case 2:
                    fragmentClass = CharacterSheetAttacksFragment.class;
                    break;
                case 3:
                    fragmentClass = CharacterSheetTraitsFragment.class;
                    break;
                case 4:
                    fragmentClass = CharacterSheetEquipmentFragment.class;
            }

            try {
                fragment = (Fragment) fragmentClass.newInstance();
                fragment.setArguments(bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }
    }
}

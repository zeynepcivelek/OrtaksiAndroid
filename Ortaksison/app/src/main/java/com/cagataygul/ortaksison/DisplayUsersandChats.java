package com.cagataygul.ortaksison;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cagataygul.ortaksison.Fragments.ChatsFragment;
import com.cagataygul.ortaksison.Fragments.UsersFragment;

import java.util.ArrayList;

public class DisplayUsersandChats extends AppCompatActivity {
TabLayout tab ;
ViewPager viewPager;
Intent intent;
String userid;
android.app.FragmentManager fragment;
    UsersFragment usersFragment=new UsersFragment();
FragmentManager fragmentManager;
    ViewvPagerAdapter viewvPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_usersand_chats);
        intent= getIntent();
        userid = intent.getStringExtra("userid");

        tab=(TabLayout) findViewById(R.id.tablayout);
        viewPager=(ViewPager) findViewById(R.id.viewpager);
        viewvPagerAdapter= new ViewvPagerAdapter(getSupportFragmentManager());
        viewvPagerAdapter.addfragment(new ChatsFragment(),"Chats");
        viewvPagerAdapter.addfragment(new UsersFragment(),"Users");
        viewPager.setAdapter(viewvPagerAdapter);
        tab.setupWithViewPager(viewPager);


        usersFragment.setData(userid);

    }




    class ViewvPagerAdapter extends FragmentPagerAdapter
    {



      private ArrayList<Fragment> fragments;
      private ArrayList<String> titles;
      ViewvPagerAdapter(FragmentManager fm)
      {
          super(fm);
          this.fragments=new ArrayList<>();
          this.titles= new ArrayList<>();
      }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        public void addfragment(Fragment fragment,String title)
        {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
    public String getUserid() {
        return userid;
    }
}

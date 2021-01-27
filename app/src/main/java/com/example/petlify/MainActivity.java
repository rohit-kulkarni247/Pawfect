package com.example.petlify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.TextureView;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Animation top,bottom;
    ImageView image,back;
    TextView logo;
    
    private static final int num_pages=3;
    private ViewPager viewPager;
    private ScreenSlide pageAdapter;

    private static int SPLASH_TIME=4500;
    SharedPreferences mshared;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        top= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        image=findViewById(R.id.pet);
        logo=findViewById(R.id.name);
        back=findViewById(R.id.backcolor);

        image.setAnimation(top);
        logo.setAnimation(bottom);

        back.animate().translationY(-2500).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        image.animate().translationY(2000).setDuration(1000).setStartDelay(4000);

        viewPager=findViewById(R.id.pager);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mshared=getSharedPreferences("SharedPref",MODE_PRIVATE);
                boolean isFirstTime=mshared.getBoolean("firstTime",true);
                if(isFirstTime){
                    pageAdapter=new ScreenSlide(getSupportFragmentManager());
                    viewPager.setAdapter(pageAdapter);
                    SharedPreferences.Editor editor=mshared.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();
                }
                else {
                    Intent intent=new Intent(MainActivity.this,Signup.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_TIME);

    }

    private class ScreenSlide extends FragmentStatePagerAdapter {


        public ScreenSlide(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull

        @Override
        public Fragment getItem(int position){
            switch (position){
                case 0:
                    Onboardingfragment1 tab1=new Onboardingfragment1();
                    return tab1;
                case 1:
                    Onboardingfragment2 tab2=new Onboardingfragment2();
                    return tab2;
                case 2:
                    Onboardingfragment3 tab3=new Onboardingfragment3();
                    return tab3;
            }
            return null;
        }


        @Override
        public int getCount() {
            return num_pages;
        }
    }
}
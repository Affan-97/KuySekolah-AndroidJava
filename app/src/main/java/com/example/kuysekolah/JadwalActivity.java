package com.example.kuysekolah;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.kuysekolah.databinding.ActivityJadwalBinding;

public class JadwalActivity extends AppCompatActivity {

    private ActivityJadwalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityJadwalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TabAdapter tabAdapter = new TabAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(tabAdapter);
        com.google.android.material.tabs.TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

    }
}
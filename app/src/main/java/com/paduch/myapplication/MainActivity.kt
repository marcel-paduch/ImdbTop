package com.paduch.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paduch.myapplication.view.TopMoviesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, TopMoviesFragment())
                .commit()
        }
    }


}

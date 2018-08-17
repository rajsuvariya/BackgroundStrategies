package com.rajsuvariya.backgroundstrategies

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
    }

    fun openAsyncTaskActivity(view: View) {
        val intent = Intent(this, AsyncTaskActivity::class.java)
        startActivity(intent)
    }
}

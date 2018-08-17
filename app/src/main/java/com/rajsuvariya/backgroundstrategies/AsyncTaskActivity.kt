package com.rajsuvariya.backgroundstrategies

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors


/**
 * This activity provides different implementation of AsyncTask and the total time taken by the execution.
 *
 * Author : Raj Suvariya
 */

/**
 * TODO
 * 1. Different kind of AsyncTask
 * 2. Screen Rotation
 * 3. Memory Leaks
 * 4. Memory de-referencing
 */

class AsyncTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun asyncTaskonDefaultThread(v: View) {
        val startTime = System.currentTimeMillis()
        var endTime: Long
        val asyncTask1 = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Void, Unit, Unit>() {
            override fun onPreExecute() {
                super.onPreExecute()
                showProcessRunning()
            }
            override fun doInBackground(vararg params: Void?): Unit {
                try {
                    printCurrentTime("Starting Async 1")
                    Thread.sleep(7000)
                    printCurrentTime("Ending Async 1")
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        val asyncTask2 = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Void, Unit, Unit>() {
            override fun doInBackground(vararg params: Void?): Unit {
                try {
                    printCurrentTime("Starting Async 2")
                    Thread.sleep(10000)
                    printCurrentTime("Ending Async 2")
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        val asyncTask3 = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Void, Unit, Unit>() {
            override fun doInBackground(vararg params: Void?): Unit {
                try {
                    printCurrentTime("Starting Async 3")
                    Thread.sleep(3000)
                    printCurrentTime("Ending Async 3")
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                endTime = System.currentTimeMillis()
                showTotalTime(startTime, endTime)
            }
        }
        asyncTask1.execute()
        asyncTask2.execute()
        asyncTask3.execute()
    }

    fun asyncTaskonThreadPoolExecutor(v: View) {
        var startTime: Long = System.currentTimeMillis()
        var endTime: Long = startTime
        val asyncTask1 = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Void, Unit, Unit>() {
            override fun onPreExecute() {
                super.onPreExecute()
                showProcessRunning()
            }
            override fun doInBackground(vararg params: Void?): Unit {
                try {
                    printCurrentTime("Starting Async 1")
                    Thread.sleep(7000)
                    printCurrentTime("Ending Async 1")
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                endTime = System.currentTimeMillis()
            }
        }
        val asyncTask2 = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Void, Unit, Unit>() {
            override fun doInBackground(vararg params: Void?): Unit {
                try {
                    printCurrentTime("Starting Async 2")
                    Thread.sleep(10000)
                    printCurrentTime("Ending Async 2")
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                endTime = System.currentTimeMillis()
            }
        }
        val asyncTask3 = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Void, Unit, Unit>() {
            override fun doInBackground(vararg params: Void?): Unit {
                try {
                    printCurrentTime("Starting Async 3")
                    Thread.sleep(3000)
                    printCurrentTime("Ending Async 3")
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                endTime = System.currentTimeMillis()
            }
        }
        val executor = Executors.newFixedThreadPool(3)
        asyncTask1.executeOnExecutor(executor)
        asyncTask2.executeOnExecutor(executor)
        asyncTask3.executeOnExecutor(executor)

        Handler().postDelayed(Runnable {
            showTotalTime(startTime, endTime)
        }, 20000)
    }

    private fun showTotalTime(startTime: Long, endTime: Long) {
        val time : Float = ((endTime - startTime)/1000).toFloat()
        tv_result.text = "Total time taken: $time s"
    }

    private fun showProcessRunning() {
        tv_result.text = "Executing AsyncTasks ..."
    }

    @SuppressLint("SimpleDateFormat")
    private fun printCurrentTime(s: String) {
        val yourmilliseconds = System.currentTimeMillis()
        val sdf = SimpleDateFormat("HH:mm:ss")
        val resultdate = Date(yourmilliseconds)
        Log.d("Timerr", s + "   " + sdf.format(resultdate))
    }
}

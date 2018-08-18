package com.rajsuvariya.backgroundstrategies

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ThreadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)
    }

    fun threadsWithoutExecutor(v: View) {
        val startTime = System.currentTimeMillis()
        var endTime: Long = startTime
        var thread1 = Thread(Runnable {
            Thread.sleep(7000)
            endTime = System.currentTimeMillis()
        })
        var thread2 = Thread(Runnable {
            Thread.sleep(10000)
            endTime = System.currentTimeMillis()
        })
        var thread3 = Thread(Runnable {
            Thread.sleep(3000)
            endTime = System.currentTimeMillis()
        })

        showProcessRunning()
        thread1.start()
        thread2.start()
        thread3.start()

        Thread(Runnable {
            thread1.join()
            thread2.join()
            thread3.join()
            showTotalTime(startTime, endTime)
        }).start()
    }

    fun handlerWithHandlerThread(v: View) {
        val startTime = System.currentTimeMillis()
        var endTime: Long = startTime
        var handlerThread = HandlerThread("HandlerThread")
        handlerThread.start()
        showProcessRunning()
        var handler1 = Handler(handlerThread.looper).post(Runnable {
            Thread.sleep(7000)
            endTime = System.currentTimeMillis()
        })
        Handler(handlerThread.looper).post(Runnable {
            Thread.sleep(10000)
            endTime = System.currentTimeMillis()
        })
        Handler(handlerThread.looper).post(Runnable {
            Thread.sleep(3000)
            endTime = System.currentTimeMillis()
        })

        Thread(Runnable {
            handlerThread.looper.queue.addIdleHandler {
                handlerThread.quitSafely()
            }
            handlerThread.join()
            showTotalTime(startTime, endTime)
        }).start()
    }

    fun threadsWithThreadPoolExecutor(v: View) {
        val startTime = System.currentTimeMillis()
        var endTime: Long = startTime
        var threadPoolExecutor = Executors.newFixedThreadPool(3)
        var thread1 = Thread(Runnable {
            Thread.sleep(7000)
            endTime = System.currentTimeMillis()
        })
        var thread2 = Thread(Runnable {
            Thread.sleep(10000)
            endTime = System.currentTimeMillis()
        })
        var thread3 = Thread(Runnable {
            Thread.sleep(3000)
            endTime = System.currentTimeMillis()
        })

        showProcessRunning()
        threadPoolExecutor.submit(thread1)
        threadPoolExecutor.submit(thread2)
        threadPoolExecutor.submit(thread3)

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

}

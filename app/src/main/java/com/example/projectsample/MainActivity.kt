package com.example.projectsample

import android.app.Dialog
import android.os.Bundle
import android.os.Environment
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.amankhan.youtubedl_android.DownloadProgressCallback
import com.example.projectsample.databinding.ActivityMainBinding
import io.reactivex.disposables.CompositeDisposable
import java.io.File


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var loadingDialog: Dialog
    lateinit var progressDialog: Dialog

    var progressPercent : TextView?=null
    var progressBar : ProgressBar?=null
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.download.setOnClickListener{
            loadingDialog()
            progressDialog()
            val start = com.amankhan.ffmpeg.Startscript()
            start.startDownload(this,"hello1234",binding.link.text.toString(),"success","failed",loadingDialog,progressDialog,compositeDisposable,File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"test"),callback)
        }
    }
    fun progressDialog() {
        progressDialog = Dialog(this)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setCancelable(false)
        progressDialog.setContentView(R.layout.progress_dialog_layout)
        progressPercent = progressDialog.findViewById(R.id.progress) as TextView
         progressBar = progressDialog.findViewById(R.id.progress_bar) as ProgressBar
        val cancel = progressDialog.findViewById(R.id.cancel) as Button
        cancel.setOnClickListener {
            compositeDisposable.dispose()
            progressDialog.dismiss()
        }
    }
    fun loadingDialog() {
        loadingDialog = Dialog(this)
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDialog.setCancelable(false)
        loadingDialog.setContentView(R.layout.loading_dialog_layout)
        val cancel = loadingDialog.findViewById(R.id.cancel) as Button
        cancel.setOnClickListener {
            compositeDisposable.dispose()
            loadingDialog.dismiss()
        }

        loadingDialog.show()

    }
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
    private val callback = DownloadProgressCallback { progress, etaInSeconds, line ->
            runOnUiThread {
                loadingDialog.dismiss()
                progressDialog.show()

                if (progress.toInt()==-1){
                    progressBar?.setProgress(0)
                    progressPercent?.setText("0")
                }
                else{
                    progressBar?.setProgress(progress.toInt())
                    progressPercent?.setText(progress.toInt().toString())
                }



            }
        }

}
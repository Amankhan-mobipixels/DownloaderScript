package com.example.projectsample

import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.abdellatif.youtubedl_android.DownloadProgressCallback
import com.abdellatif.youtubedl_android.Startscript
import com.example.projectsample.databinding.ActivityMainBinding
import io.reactivex.disposables.CompositeDisposable
import java.io.File


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.download.setOnClickListener{
            val start = Startscript()
            start.startDownload(this,binding.link.text.toString(),binding.progressCircular,binding.progressBar,compositeDisposable,File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"test"),callback)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
    private val callback =
        DownloadProgressCallback { progress, etaInSeconds, line ->
            runOnUiThread {
                binding.progressCircular.visibility=View.GONE
                binding.progressBar.setProgress(
                    progress.toInt()
                )
            }
        }

}
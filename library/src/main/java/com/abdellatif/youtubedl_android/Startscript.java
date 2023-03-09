package com.abdellatif.youtubedl_android;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.abdellatif.youtubedl_android.DownloadProgressCallback;
import com.abdellatif.youtubedl_android.YoutubeDL;
import com.abdellatif.youtubedl_android.YoutubeDLRequest;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Startscript {

     public void startDownload(Context context, String url, ProgressBar circular, ProgressBar progressBar, CompositeDisposable compositeDisposable,File  filePAth,DownloadProgressCallback callback) {
        if (!filePAth.exists()) filePAth.mkdir();

        YoutubeDLRequest request = new YoutubeDLRequest(url);
        request.addOption("--no-mtime");
        request.addOption("-f", "bestvideo[ext=mp4]+bestaudio[ext=m4a]/best[ext=mp4]/best");
        request.addOption("-o", filePAth.getAbsolutePath() + "/%(title)s.%(ext)s");
         circular.setVisibility(View.VISIBLE);
        Disposable disposable = Observable.fromCallable(() -> YoutubeDL.getInstance().execute(request, callback))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(youtubeDLResponse -> {
                    progressBar.setProgress(100);
                    Toast.makeText(context, "Download successful", Toast.LENGTH_LONG).show();
                }, e -> {
//                    circular.setVisibility(View.GONE);
                    //Toast.makeText(DownloadingExampleActivity.this, "download failed", Toast.LENGTH_LONG).show();
                    Toast.makeText(context, "Download failed", Toast.LENGTH_LONG).show();
                });
        compositeDisposable.add(disposable);

    }
}

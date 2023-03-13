package com.amankhan.ffmpeg;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import com.amankhan.youtubedl_android.DownloadProgressCallback;
import com.amankhan.youtubedl_android.YoutubeDL;
import com.amankhan.youtubedl_android.YoutubeDLRequest;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Startscript {

     public void startDownload(String name, String url,CompositeDisposable compositeDisposable,File filePAth, DownloadProgressCallback callback,DownloadCompleteNotifier notify) {
        if (!filePAth.exists()) filePAth.mkdir();

        YoutubeDLRequest request = new YoutubeDLRequest(url);
        request.addOption("--no-mtime");
        request.addOption("-f", "bestvideo[ext=mp4]+bestaudio[ext=m4a]/best[ext=mp4]/best");
//        request.addOption("-o", filePAth.getAbsolutePath() + "/%(title)s.%(ext)s");
        request.addOption("-o", filePAth.getAbsolutePath() + "/"+name+".mp4");
        Disposable disposable = Observable.fromCallable(() -> YoutubeDL.getInstance().execute(request, callback))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(youtubeDLResponse -> {
                    notify.onProgressUpdate(true);
                }, e -> {
                    notify.onProgressUpdate(false);
                });
        compositeDisposable.add(disposable);

    }
}

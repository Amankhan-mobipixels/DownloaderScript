package com.amankhan.ffmpeg;

public interface DownloadCompleteNotifier {
    void onProgressUpdate(Boolean complete);
}
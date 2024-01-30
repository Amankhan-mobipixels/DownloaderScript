````
dependencie
{
implementation 'com.github.Amankhan-mobipixels.DownloaderScript:DownloadLibrary:1.4'
}
````
Working:
````
Tiktok
SnackVideo
ShareChat
Likee
Moj
Vimeo

````
Code
````
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var loadingDialog: Dialog
    lateinit var progressDialog: Dialog

    var progressPercent: TextView? = null
    var progressBar: ProgressBar? = null
    private var compositeDisposable : CompositeDisposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.download.setOnClickListener {
            loadingDialog()
            progressDialog()
            compositeDisposable=CompositeDisposable()
            val start = com.amankhan.ffmpeg.Startscript()
            start.startDownload("hello123456", binding.link.text.toString(), compositeDisposable,
                File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "test"
                ), callback, completeNotifier
            )
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
            compositeDisposable?.dispose()
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
            compositeDisposable?.dispose()
            loadingDialog.dismiss()
        }

        loadingDialog.show()

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.dispose()
    }

    private val callback = DownloadProgressCallback { progress, etaInSeconds, line ->
        runOnUiThread {
            loadingDialog.dismiss()
            progressDialog.show()

            if (progress.toInt() == -1) {
                progressBar?.setProgress(0)
                progressPercent?.setText("0")
            } else {
                progressBar?.setProgress(progress.toInt())
                progressPercent?.setText(progress.toInt().toString())
            }


        }
    }
    private val completeNotifier = DownloadCompleteNotifier {complete ->
        if (complete) {
            progressDialog.dismiss()
            Toast.makeText(this@MainActivity, "success", Toast.LENGTH_LONG).show()
        } else {
            loadingDialog.dismiss()
            progressDialog.dismiss()
            Toast.makeText(this@MainActivity, "failed", Toast.LENGTH_LONG).show()
        }
    }

}

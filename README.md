# IntercomApp
How To Save image after capture image from camera :

1 - declare these variables in fragment
<pre>
  <code>
    private lateinit var filePhoto: File
    private lateinit var photoUri: Uri
    var mCurrentPhotoPath: String? = null
    </code>
</pre>

 2 - declare the below methods for receving image intent from camera and capture , permissions
<pre>
  <code>
     private val startForResult_CaptureFromCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                    //hide tips and show image
                    photoUri = filePhoto.toUri()
                    galleryAddPic()
                    binding.fullImageImg.visibility = View.VISIBLE
                    binding.fullImageImg.setImageURI(photoUri)

            }
        }
  </code>
</pre>
        
<pre>
  <code>
 private fun captureImage() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                0
            )
        } else {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
                // Create the File where the photo should go
                try {
                    filePhoto = createImageFile()
                    // Continue only if the File was successfully created
                    if (filePhoto != null) {
                        val photoURI = FileProvider.getUriForFile(
                            requireContext(),
                            "${BuildConfig.APPLICATION_ID}.fileprovider",
                            filePhoto
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startForResult_CaptureFromCamera.launch(takePictureIntent)
                    }
                } catch (ex: Exception) {
                    // Error occurred while creating the File
                    displayMessage(requireContext(), ex.message.toString())
                }

            } else {
                displayMessage(requireContext(), "Null")
            }
        }

    }
    
     @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
    }

    private fun galleryAddPic() {
        val file = File(mCurrentPhotoPath)
        MediaScannerConnection.scanFile(
            context, arrayOf(file.toString()),
            null, null
        )
    }
    </code>
</pre>

<pre>
  <code>
3 - in the manifict add this:
  <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="${applicationId}.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true"
            android:permission="android.permission.MANAGE_DOCUMENTS">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/provider_paths" />
            <intent-filter>
                <action android:name="android.content.action.DOCUMENTS_PROVIDER" />
            </intent-filter>
        </provider>
        </code>
</pre>

<pre>
  <code>
 4 - create file in xml folder "provider_paths.xml" and put these code inside it 
 
<?xml version="1.0" encoding="utf-8"?>
<paths>
    <external-path
        name="external"
        path="." />
    <external-files-path
        name="external_files"
        path="." />
    <cache-path
        name="cache"
        path="." />
    <external-cache-path
        name="external_cache"
        path="." />
    <files-path
        name="files"
        path="." />
</paths
         </code>
</pre>
<pre>
  <code>
 <img width="531" alt="Screen Shot 2022-08-14 at 1 40 01 AM" src="https://user-images.githubusercontent.com/31186483/184516787-3f37604e-6520-48c5-a7e0-f9c1c8d9c8bc.png">
         </code>
</pre>
    <pre>
  <code>
<img width="1072" alt="Screen Shot 2022-08-14 at 1 40 55 AM" src="https://user-images.githubusercontent.com/31186483/184516806-6c7838b2-a23d-43cf-b60c-aab1f1004e27.png">
         </code>
</pre>

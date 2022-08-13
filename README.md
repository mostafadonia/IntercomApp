# IntercomApp
How To Save image after capture image from camera :
1 - declare these variables in fragment
    private lateinit var filePhoto: File
    private lateinit var photoUri: Uri
    var mCurrentPhotoPath: String? = null
 2- declare the below methods for receving image intent from camera and capture , permissions
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

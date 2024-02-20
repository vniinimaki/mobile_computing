package com.example.composetutorial

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import java.io.File



@Composable
fun BackScreen(
    onNavigateToMessages: () -> Unit,
    db: AppDatabase
) {
    val context = LocalContext.current
    val service = NotificationHandler(context)
    var isDark by remember{
        mutableStateOf(false)
    }

    val sensorService = LightSensor(context)

    sensorService.startListening()
    sensorService.setOnSensorValuesChangedListener { values ->
        val lux = values[0]
        Log.d(TAG, lux.toString())
        isDark = lux < 60f
        if(isDark) service.showNotification("Light level is " + lux.toString() + " lux")
    }



    var selectedImageUri: String by remember {
        mutableStateOf("")
    }
    val imageFile by remember { mutableStateOf(File(context.filesDir, "selectedImage")) }

    var userName: String by remember {
        mutableStateOf(if (db.dao.getRowCount() > 0) db.dao.getUser().userName else "")
    }
    if (imageFile.exists() && imageFile.readBytes().toString() != "") {
        selectedImageUri = imageFile.toUri().toString()
    }

    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            // Process the selected image URI
            Log.d("PhotoPicker", "Selected URI: $uri")
            selectedImageUri = uri.toString()

            // Save the image to internal storage
            val inputStream = context.contentResolver.openInputStream(uri)
            val outputStream = context.openFileOutput("selectedImage", Context.MODE_PRIVATE)
            inputStream?.use { input ->
                outputStream?.use { output ->
                    input.copyTo(output)
                }
            }
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }
    Column(modifier = Modifier.padding(all = 8.dp)){
        Button(onClick = onNavigateToMessages) {
            Text("Go to messages")
        }
        Button(onClick = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
            Text("Pick a photo")
        }
        Button(onClick = onNavigateToMessages) {
            Text("Go to messages")
        }
        TextField(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 8.dp)
                .width(220.dp),
            value = userName,
            onValueChange = { userName = it },
            label = { Text(text = "Enter your name") },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )
        Button(onClick = {
            if (userName != "") onNavigateToMessages()
            db.dao.addUser(User(userName))
            }){
            Text("Choose")
        }
        Button(onClick = {
            service.showNotification("test")
        }){
            Text("Notify")
        }



        }


}

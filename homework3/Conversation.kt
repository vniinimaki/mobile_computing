package com.example.composetutorial

import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.io.File


data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message, db: AppDatabase) {
    val profilePicture = File(LocalContext.current.filesDir, "selectedImage")
    Row(modifier = Modifier.padding(all = 8.dp)) {
        val paint = painterResource(R.drawable.profile_picture)
        /*
        Image(
            painter = paint,
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )*/
        AsyncImage(

            model = ImageRequest.Builder(LocalContext.current)
                .data(Uri.fromFile(profilePicture))
                .build(),
            contentDescription = "Contact profile picture",
            contentScale = ContentScale.Inside,

            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )




        Spacer(modifier = Modifier.width(8.dp))
        var isExpanded by remember{ mutableStateOf(false) }
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )
        Column (modifier =  Modifier.clickable { isExpanded = !isExpanded}) {
            Text(
                text = if (db.dao.getRowCount() > 0) db.dao.getUser().userName else msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
/*
@Composable
fun Conversation(messages: List<Message>){
    LazyColumn{

        items(messages) { message -> MessageCard(msg = message, db)

        }
    }
}
*/
@Composable
fun ConversationScreen(messages: List<Message>, onNavigateToBack: () -> Unit, db: AppDatabase){
    LazyColumn{
        items(messages) {message -> MessageCard(msg = message, db)

        }
    }
    Button(
        onClick = onNavigateToBack,
        modifier = Modifier.offset(250.dp)
    ) {
        Text("Go to back screen")
    }
}
/*
@Preview
@Composable
fun PreviewConversation() {
    ComposeTutorialTheme {
        Conversation(SampleData.conversationSample)
    }
}
*/

/*
@Preview
@Composable
fun PreviewMessageCard(){
    ComposeTutorialTheme {
        Surface {
            MessageCard(
                msg = Message("Lexi", "Hey, take a loot at Jetpack Compose, it's great"),
                db = AppDatabase
            )
        }
    }
}
*/
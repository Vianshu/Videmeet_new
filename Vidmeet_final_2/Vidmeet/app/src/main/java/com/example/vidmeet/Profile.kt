package com.example.vidmeet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.vidmeet.ui.theme.VidmeetTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class Profile: ComponentActivity() {

    val clr = Color(0xFF0095F0)

    private val uvm: User_vm by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    private lateinit var dis_name:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dis_name=intent.getStringExtra("displayname").toString()
        uvm.updateDisplayName(dis_name)
        setContent {
            VidmeetTheme {
                Box(modifier = Modifier.padding(20.dp, top = 50.dp)) {
                    Column {

                        IconButton(onClick = {
                            val i=Intent(baseContext,Jitsi::class.java)
                            i.putExtra("displayname",dis_name)
                            startActivity(i)
                        })
                        {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Dn_field()
                        Spacer(modifier = Modifier.height(20.dp))
                        mailtbox()
                        Spacer(modifier = Modifier.height(20.dp))
                        sign_out()
                    }
                }
            }
        }
    }
    @Composable
    fun mailtbox() {
        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .background(Color.White, shape = RoundedCornerShape(14.dp))
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "",
                tint = clr
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${Firebase.auth.currentUser?.email}",
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
    @Composable
    fun Dn_field(){
        Row {
            TextField(
                value = uvm.displayname ,
                onValueChange = { uvm.updateDisplayName(it) },
                modifier = Modifier.padding(top = 20.dp),
                label = { Text(text = "Display name") },
                textStyle = TextStyle(color = Color.Black),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person,
                        contentDescription ="",
                        tint = clr
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = clr,
//                    focusedTextColor = clr.copy(0.4f),
//                    unfocusedTextColor = clr.copy(0.4f),
                ),
                shape = CutCornerShape(14.dp),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = true,
                    imeAction = ImeAction.Done
                )
            )
            Icon(
                imageVector = Icons.Default.Create,
                contentDescription = "",

                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .align(Alignment.Bottom)
            )
        }
    }

    @Composable
    fun sign_out(){
        Button(onClick = {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
            Log.d("USER_","SIGNED OUT $")
        }) {
            Text("SIGN OUT")
        }
    }
}

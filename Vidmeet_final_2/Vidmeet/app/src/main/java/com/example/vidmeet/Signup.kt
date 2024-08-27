package com.example.vidmeet

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidmeet.ui.theme.VidmeetTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class Signup() :ComponentActivity(){
    private lateinit var auth: FirebaseAuth
    private lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VidmeetTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Signup_UI()
                }
            }
        }
        auth= Firebase.auth
        db=Firebase.firestore
    }

    @SuppressLint("LogNotTimber")
    @Composable
    private fun Signup_UI(){
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var conpass by remember { mutableStateOf("") }
        val fieldcolor = Color(0xFF1B24F0)

            //val backgroundColor = Color(0xFFEEEEEE)
        val accentColor = Color(0xFF0FB4FF)
        val textColor = Color.Black
        BackgroundAsImage()
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "SIGN UP",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                ),
                textAlign = TextAlign.Start,
            )

            Box(
                modifier = Modifier
                    .height(500.dp)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        color = Color.White, shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(16.dp))


                        TextField(
                            value = username ,
                            onValueChange = { username=it },
                            modifier = Modifier.padding(top = 20.dp),
                            label = { Text(text = "Email") },
                            textStyle = TextStyle(color = Color.Black),
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Email,
                                    contentDescription ="",
                                    tint = fieldcolor
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                cursorColor = fieldcolor,
                            ),
                            shape = CutCornerShape(14.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            value = password ,
                            onValueChange = { password=it },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.padding(top = 20.dp),
                            label = { Text(text = "Password") },
                            textStyle = TextStyle(color = Color.Black),
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Lock,
                                    contentDescription ="",
                                    tint = fieldcolor
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                cursorColor = fieldcolor,
                            ),
                            shape = CutCornerShape(14.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = conpass ,
                            onValueChange = { conpass=it },
                            modifier = Modifier.padding(top = 20.dp),
                            visualTransformation = PasswordVisualTransformation(),
                            label = { Text(text = "Confirm Password") },
                            textStyle = TextStyle(color = Color.Black),
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Lock,
                                    contentDescription ="",
                                    tint = fieldcolor
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                cursorColor = fieldcolor,
                            ),
                            shape = CutCornerShape(14.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        /*TextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password", color = textColor) },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth(),
                            textStyle = TextStyle(color = textColor),
                        )
                        TextField(
                            value = conpass,
                            onValueChange = { conpass = it },
                            label = { Text("Confirm Password", color = textColor) },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth(),
                            textStyle = TextStyle(color = textColor),
                        )

                        Spacer(modifier = Modifier.height(25.dp))*/
                        Button(
                            onClick = {
                                if(conpass==password) {
                                    Log.d("SIGNUP_", " SIGNUP DONE")
                                    createuser(username, password)

                                    var user = hashMapOf(
                                        "Username" to username
                                    )

//                                    db.collection("users")
//                                        .add(user)
//                                        .addOnSuccessListener { documentReference ->
//                                            Log.d(
//                                                "firestore",
//                                                "DocumentSnapshot added with ID: ${documentReference.id}"
//                                            )
//                                        }
//                                        .addOnFailureListener { e ->
//                                            Log.w("firstore", "Error adding document", e)
//                                        }
                                }
                                else{
                                    Toast.makeText(baseContext," Passwords do not match!",Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = accentColor
                            ),
                            modifier = Modifier
                                .width(200.dp)
                                .height(50.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 8.dp,
                                pressedElevation = 16.dp
                            )
                        ) {
                            Text(text = "Sign Up", color = Color.White)
                        }
                    }
                }
            }
        }
    }
    @Composable
    fun BackgroundAsImage(){
        Image(painter = painterResource(id = R.drawable.background), contentDescription = "Background Image", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds)
    }

    private fun createuser(email:String,password:String){
        Log.d("CREATE_USER_", "CreateUserCalled : $email : $password")
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            task ->
            if(task.isSuccessful){
                Log.d("CREATE_USER_", "createUserWithEmail:success")
                val user = auth.currentUser
                Toast.makeText(baseContext,"Account Created Successfully.\n   Please Login",Toast.LENGTH_SHORT).show()
                finish()
            } else {

                Log.w("CREATE_USER_", "createUserWithEmail:failure", task.exception)
                Toast.makeText(
                    this,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }
}
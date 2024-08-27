package com.example.vidmeet

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.vidmeet.ui.theme.VidmeetTheme
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity() {
//    private lateinit var auth: FirebaseAuth
    private lateinit var mainintent :Intent
    private lateinit var signupIntent :Intent

    private val uvm: User_vm by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Wrepo.initialize(this)
        val repo=Wrepo.get()
        signupIntent= Intent(this, Signup::class.java)
        mainintent=Intent(this, Jitsi::class.java)

        if(uvm.auth.currentUser!=null){
            startActivity(mainintent)
        }
        setContent {
            VidmeetTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier= Modifier
                        .fillMaxSize()
                        .background(color = Color(0xFFEBB4E9))){
                        Login()
                    }
                }
            }
        }
        Log.d("MAIN_", "STARTING NORMAL")
    }

    @SuppressLint("LogNotTimber")
    @Composable
    private fun Login() {
        var password by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        //val backgroundColor = Color(0xFFEEEEEE)
        val accentColor = Color(0xFF0FB4FF)
        val fieldcolor = Color(0xFF1B24F0)

        val textColor = Color.White
        val intxtcolor = Color.Black
        BackgroundAsImage()

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .padding(20.dp)

            ){
                Text(
                    text = "WELCOME TO \n VidMeet",
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.Serif,
                        color = textColor
                    ),
                    textAlign = TextAlign.Start
                )
            }

            Spacer(modifier = Modifier.height(25.dp))
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


                    Spacer(modifier = Modifier.height(32.dp))

                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            value = email ,
                            onValueChange = { email=it },
                            modifier = Modifier.padding(top = 20.dp),
                            label = { Text(text = "Email") },
                            textStyle = TextStyle(color = intxtcolor),
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
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.padding(top = 20.dp),
                            textStyle = TextStyle(color = intxtcolor),
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

                        Spacer(modifier = Modifier.height(25.dp))
                        Button(
                            onClick = {
                                Log.d("LOGIN__", "LOGIN_BUTTON_CLICKED")
                                if(email!="" && password!=""){
                                    authlogin(email, password)
                                }
                                else{
                                    Toast.makeText(baseContext," Fields are empty",Toast.LENGTH_SHORT).show()
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
                            Text(text = "Login", color = Color.White)
                        }

                        Spacer(modifier = Modifier.height(25.dp))
                        Button(
                            onClick = {
                                Toast.makeText(
                                    applicationContext,
                                    "NEW ACCOUNT",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(signupIntent)
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
                            Text(text = "Signup", color = Color.White)
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
    @SuppressLint("LogNotTimber")
    private fun authlogin(email:String, password:String) {
        Log.d("LOGGING_", "autologin called $email $password")
        uvm.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("LOGGING_", "signInWithEmail:success")
                    val user: FirebaseUser? = uvm.auth.currentUser
                    if (user != null) {
                        Log.d("LOGGING_", "USER EXISTS NICE")
                        mainintent.putExtra("email", email);
                        startActivity(mainintent)
                    } else {
                        Toast.makeText(
                            baseContext,
                            " Email does not exist. Please Sign Up.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Log.w("LOGGING_", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
    class Wrepo private constructor(context: Context){
        private val database:roomdb = Room.databaseBuilder(
            context.applicationContext,
            roomdb::class.java,
            "rhist"
        ).fallbackToDestructiveMigration().build()

        fun rinsert(rmname:String,rmtime:String,rmdate:String,rm_loc:String){
            val nd=room(0,r=rmname,t=rmtime,d=rmdate,l=rm_loc)
            Log.d("Roomdb_","Inserted roomname $rmname")
            database.roomDao().insertAll(nd)
        }

        fun clear(){
            database.roomDao().clear()
        }

        fun getrooms(): List<room> {
            return database.roomDao().getAll()
        }

        companion object{
            private var INSTANCE:Wrepo?=null

            fun initialize(context:Context){
                if(INSTANCE==null){
                    INSTANCE=Wrepo(context)
                }
            }

            fun get():Wrepo{
                return INSTANCE?:throw IllegalStateException("Repo must be initialized")
            }
        }
    }
}

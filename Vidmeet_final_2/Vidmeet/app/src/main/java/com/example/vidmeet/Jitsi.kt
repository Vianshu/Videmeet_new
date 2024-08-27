package com.example.vidmeet


import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jitsi.meet.sdk.BroadcastEvent
import org.jitsi.meet.sdk.BroadcastIntentHelper
import org.jitsi.meet.sdk.JitsiMeet
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import timber.log.Timber
import java.net.MalformedURLException
import java.net.URL
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


@OptIn(ExperimentalEncodingApi::class)
@Throws(Exception::class)
fun getPemPrivateKey(): RSAPrivateKey? {
    val pem="-----BEGIN PRIVATE KEY-----" +  "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCYpxXoGHwuv6EJ" + "TXj4beihG0J6G72xkuQGk7c/Bhu3irQhjO+9S+6dYyVfPyr5/oUvjutwNBqE76Wy" +
            "VE5HMDaWGGQGwFa9MwjyV52XVFpoXFav7gMKMan5Cgzg3uyVqx3uZWFEHfZKAt0D" +
            "hMvHTbnus56Cty6/1r7wDjScgsq+65+fLX9m4uWo8ucAlKcgCbxHPcLWQO4p2XlM" +
            "IyLOMqK+IZ6N0SPrVefZ1ZDAMKp3NCJuqg8i+SPNoqNLir10rI6+s5eH73SL4ioe" +
            "2aOP5OeQBVHcAvN4RqmHGmrrN5WTbcDES7Z+s2o4PghrfSu5pRdfHRsN02TTvbuJ" +
            "FXspLZyDAgMBAAECggEAC+cLSDuQgzyh283UntpDBzzO5CKBmfTwHqsp4JojUx5X" +
            "iT4p+0KOamsuoMyWF0QHMnwcU3Vck/zzmIIGUHqr5uwj8HrSyJHQIsU+HmyPWA/L" +
            "GpCeYA8bR7XG50m6fLkI7mutN9h2ObMbdIjIMtQWqUwMEfkihgbCgIk7BPEVyekJ" +
            "yuA1fg/3BVzowrLGEYHCpOVXmIOIzQyM8pGtu8Rj+zVV+Ooh1q6zIIyFBnFmrxOD" +
            "bB5DBsyaDDu2cwjpFPR0cIztW/UWqpq01cu99HV1Umn7KQ/r3XUHnKauPMgpXiZu" +
            "IHjo21oPQ965F2tBICbtBLHP7wmQd5Qh4HHok5kXQQKBgQD12ZKxeC4DhZcUd9G5" +
            "4kCecxJIqN4dA7yxljQ0CBFsRJnLcdvt2SaPPEkarJHXa51KomFv18NvLsrCYDq/" +
            "mekgYLX5pQuM6besZC/110nFWez/7MC8gqb8Q4D13jCnvrZPWOSO47eq/ixlzvyH" +
            "0LgfImGQxtV8pelGf28SDN2VswKBgQCe9H8IIe8K85GfDaTuB2NPZI1xqvmLrxzH" +
            "wTlc6rHocT+f5YveKJoQaXbwxKUUjKGjnU7G6wpg6k15YSy3hh8KUkys+LXiPP/v" +
            "oGHzcn+XPwweFMcgv0XZEMkbGiH5cCFWBVoQC9unSJkNNmLBMl3U4x2zUKLA8fup" +
            "NfKCFYQV8QKBgQDzUViL6GOx/J9gFI4vKKvHph+sZeUeVjqEkCIudOW5f9OutIt0" +
            "RXySOponmi/lQo6z9S32IVc9TNRDuDWst8kw0EaKQzzBQ/cS2T1WVLKgbbXQPSMr" +
            "7ysRdEXIERWaj3ej5FigyuNWiqSqRkdMq9fuiHqDbSaRbSsJKMpm1PDhXQKBgBCA" +
            "qb+aZkroxrGQpRLhSXowok6uxYGvCHnkoaP36ciMGWLsXf0OkhAqIuMUR18+ynlG" +
            "F6unM0ikiq6TXCUhk49qw/MhTyDR3HBwYjr4JC77qLJmzdWkhgfnKjnaGLTg41Ue" +
            "8R7BXGidrOYmQ1JzfDJh8e9v67XEabBDnY3L1g7BAoGABDHTCzZtHxH3i7pqJKYy" +
            "9L51S7bNpNsbotccWUiLzr7ynoYfjpYPSXvgDjc2uKVtaob2Sz4nMUOh5OWuHs+p" +
            "4oe3FbHkjQz6g6triRnD0pwtSvUKNcJpB7tDaIj75gnCFYrdZf3X+MYNWx5aORix" +
            "moxNdach3P4l0hT+WbI3Hp4=" +
            "-----END PRIVATE KEY-----"

    val privKey = pem.replace(Main.BEGIN_PRIVATE_KEY, Main.EMPTY).replace(Main.END_PRIVATE_KEY, Main.EMPTY)
    var decoded: ByteArray? = ByteArray(0)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        decoded=Base64.decode(privKey)
//        decoded = Base64.getDecoder().decode(privKey)
    }
    val spec = PKCS8EncodedKeySpec(decoded)
    val kf = KeyFactory.getInstance(Main.RSA)
    return kf.generatePrivate(spec) as RSAPrivateKey
}
class Jitsi : ComponentActivity(){
    lateinit var repo:MainActivity.Wrepo
    lateinit var hist:List<room>
    var dis_name=""

    private val uvm: User_vm by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }
//    val t=Intent(baseContext,Profile::class.java)
    private val req_code=101
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var geocoder: Geocoder


    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            onBroadcastReceived(intent)
        }
    }

    private fun gettoken(): String {
        val privateKey = getPemPrivateKey()
        val token = Main.JaaSJwtBuilder.builder()
            .withDefaults() // Set default/most common values
            .withApiKey("vpaas-magic-cookie-b8085594dd1c45e098dd2b70c63dea94/37ff07") // Set the api key
            .withUserName(dis_name) // Set the user name
            .withUserEmail(uvm.auth.currentUser?.email) // Set the user email
            .withModerator(true) // Enable user as moderator
            .withOutboundEnabled(true) // Enable outbound calls
            .withTranscriptionEnabled(true) // Enable transcription
            .withAppID("vpaas-magic-cookie-b8085594dd1c45e098dd2b70c63dea94") // Set the AppID
            .withUserAvatar("")
            .signWith(privateKey) // Sign the JWT with the private key
        return token
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = this.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val ds=intent.getStringExtra("displayname")
        if(ds!=null){
            editor.putString("dis_name", ds)
            editor.apply()
            dis_name=ds
        }else{
            dis_name=sharedPreferences.getString("dis_name", "")!!
        }
        Log.d("JITSI__","${dis_name}")

        dis_name=intent.getStringExtra("displayname").toString()
        geocoder=Geocoder(this.applicationContext)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.applicationContext)

        Log.d("Jitsi__","STARTED")
        MainActivity.Wrepo.initialize(this)
        repo= MainActivity.Wrepo.get()

        Log.d("JITSI__",repo.toString())
        CoroutineScope(Dispatchers.IO).launch {
            hist=repo.getrooms()
            Log.d("Jitsi__","GOT DATA : ${hist.toString()}")
        }
        setContent{
            Column(){
                MyScreen(hist)
            }
        }
        val serverURL: URL
        serverURL = try {
            URL("https://8x8.vc")
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            throw RuntimeException("Invalid server URL!")
        }

        val defaultOptions = JitsiMeetConferenceOptions.Builder()
            .setServerURL(serverURL)
//            .setToken()
            .setFeatureFlag("welcomepage.enabled", false)
            .build()

        JitsiMeet.setDefaultConferenceOptions(defaultOptions)
        registerForBroadcastMessages()
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }


    private fun registerForBroadcastMessages() {
        val intentFilter = IntentFilter()
        for (type in BroadcastEvent.Type.entries) {
            intentFilter.addAction(type.action)
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter)
    }

    private fun onBroadcastReceived(intent: Intent?) {
        if (intent != null) {
            val event = BroadcastEvent(intent)
            when (event.type) {
                BroadcastEvent.Type.CONFERENCE_JOINED -> Timber.i("Conference Joined with url%s", event.getData().get("url"))
                BroadcastEvent.Type.PARTICIPANT_JOINED -> Timber.i("Participant joined%s", event.getData().get("name"))
                else -> Timber.i("Received event: %s", event.type)
            }
        }
    }

    private fun hangUp() {
        val hangupBroadcastIntent: Intent = BroadcastIntentHelper.buildHangUpIntent()
        LocalBroadcastManager.getInstance(this.applicationContext).sendBroadcast(hangupBroadcastIntent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun MyScreen(rm:List<room>) {
        var rooms by remember { mutableStateOf(
            rm
        ) }
        var conferenceName by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            IconButton(onClick = {

                val i =Intent(baseContext,Profile::class.java)
                i.putExtra("displayname",dis_name)
                startActivity(i)
            })
            {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                )
            }
            OutlinedTextField(
                value = conferenceName,
                onValueChange = { conferenceName = it },
                label = { Text("Enter Conference Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            Button(
                onClick = {
                    if (conferenceName.isNotEmpty()) {
                        val options = JitsiMeetConferenceOptions.Builder()
                            .setRoom("vpaas-magic-cookie-b8085594dd1c45e098dd2b70c63dea94/${conferenceName}")
                            .setAudioMuted(true)
                            .setToken(gettoken())
                            .setVideoMuted(true)
//                            .setUserInfo(user)
                            .build()

                        val calendar = Calendar.getInstance()
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                        val dateStr = dateFormat.format(calendar.time)
                        val timeStr = timeFormat.format(calendar.time)

                        chk { t ->
                            val tokens = t.split(",").map { it.trim() }
                            val tmp = tokens.takeLast(3)
                            CoroutineScope(Dispatchers.IO).launch {
                                repo.rinsert(conferenceName, timeStr, dateStr, tmp.joinToString(","))
                            }
                            rooms = rooms.toMutableList().apply {
                                add(room(0,conferenceName, timeStr, dateStr, tmp.joinToString(",") ))
                            }
                        }




                        CoroutineScope(Dispatchers.Main).launch {
                            JitsiMeetActivity.launch(baseContext, options)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Join")
            }
        }
        Box(){
            var rm:String?=null
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(rooms) { idx, roomName ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
//                            .height(100.dp)
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    ) {
                        Column {
                             Text(
                                    text = roomName.r ?: "",
                                    textAlign = TextAlign.Center,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            Row() {
                                Text(
                                    text = roomName.t ?: "",
                                    textAlign = TextAlign.End,
                                    fontSize = 16.sp,
                                )
                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = roomName.d ?: "",
                                    textAlign = TextAlign.End,
                                    fontSize = 16.sp,
                                )
                            }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = roomName.l ?: "",
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                        )
                            }
                    }
                }
            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun chk(callback: (String) -> Unit) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    Toast.makeText(
                        baseContext,
                        "Latitude: ${location.latitude}, Longitude: ${location.longitude}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(baseContext, "Location is null", Toast.LENGTH_SHORT).show()
                }
            }
        val currlo = CurrentLocationRequest.Builder().setDurationMillis(10000).build()

        fusedLocationClient.getCurrentLocation(currlo, null)
            .addOnSuccessListener { location ->
                if (location != null) {

                    val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    val addressText = if (address != null && address.isNotEmpty()) {
                        address[0].getAddressLine(0)
                    } else {
                        ""
                    }
                    callback(addressText)
                } else {
                    Toast.makeText(this, "Location is null", Toast.LENGTH_SHORT).show()
                    callback("")
                }
            }
            .addOnFailureListener { e ->
                Log.d("Location", "Error getting location: ${e.message}")
                Toast.makeText(
                    this,
                    "Error getting current location",
                    Toast.LENGTH_SHORT
                ).show()
                callback("")
            }
    }
}

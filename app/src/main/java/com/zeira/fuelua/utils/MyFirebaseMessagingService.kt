package com.zeira.fuelua.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.FirebaseException
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.zeira.fuelua.R
import com.zeira.fuelua.app.App
import com.zeira.fuelua.data.storage.FavoritesDaoImpl
import com.zeira.fuelua.domain.models.RealmFavoritesModel
import com.zeira.fuelua.presentation.MainActivity
import kotlinx.coroutines.*

class MyFirebaseMessagingService() : FirebaseMessagingService() {

    companion object {
        var token: String? = null

        suspend fun subscribeTopic(topic: String) {
            withTimeout(5000) {
                Log.i("Subscribe", "subscribeTopic")
                subscribeToTopic(topic)
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        private suspend fun subscribeToTopic(topic: String) =
            suspendCancellableCoroutine<Boolean> { emitter ->
                FirebaseMessaging.getInstance().subscribeToTopic(topic).addOnSuccessListener {
                    Log.i("Subscribe", "Subscribed $topic")
                    emitter.resume(true) {}
                }.addOnFailureListener {
                    Log.e("Subscribe", "Failed to Subscribe $topic")
                    emitter.cancel(FirebaseException("Failed to Subscribe $topic"))
                }.addOnCanceledListener {
                    emitter.cancel(FirebaseException("token"))
                }

            }

        fun unsubscribeTopic(context: Context, topic: String) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topic).addOnSuccessListener {
            }.addOnFailureListener {
                Toast.makeText(context, "Failed to Unsubscribe $topic", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Log.i("onMessageReceived: ", p0.data.toString())

        var isStationInFavorites = false

        val title = getTitle(p0.data.get(Constants.MESSAGE))
        val content = p0.data.get(Constants.MESSAGE)?.let { getContent(it, p0.data) }

        content?.let {
            if (title == resources.getString(R.string.station_update)) {  //  show push notifications, if the station is located in the favorites
                CoroutineScope(Dispatchers.IO).launch {
                    // check if the station located in the favorites
                    isStationInFavorites = checkStations(p0.data)

                    if (isStationInFavorites) {
                        sendMessage(title, it)
                    }
                }
            } else {
                sendMessage(title, it)
            }
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendMessage(title: String, content: String) {
        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            checkNotificationChannel(Constants.FUEL_NOTIFICATION_CHANNEL_ID)
        }

//        val person = Person.Builder().setName("test").build()
        val notification = NotificationCompat.Builder(applicationContext, Constants.FUEL_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(title)
            .setContentText(content)
//                .setStyle(NotificationCompat.MessagingStyle(person)
//                        .setGroupConversation(false)
//                        .addMessage(title,
//                                currentTimeMillis(), person)
//                )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(defaultSound)

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification.build())
    }


    private fun getTitle(title: String?): String {
        return when (title) {
            Constants.COMPANY_UPDATE -> {
                resources.getString(R.string.company_update)
            }
            Constants.STATION_UPDATE -> {
                resources.getString(R.string.station_update)
            }
            else -> {
                resources.getString(R.string.app_name)
            }
        }
    }

    private fun getContent(title: String, data: Map<String, String>): String {
        return when (title) {
            Constants.COMPANY_UPDATE -> {
                val companyName = data[Constants.COMPANY_NAME]
                "${resources.getString(R.string.company_update)} $companyName"
            }
            Constants.STATION_UPDATE -> {
                val companyName = data[Constants.COMPANY_NAME]
                val stationAddress = data[Constants.ADDRESS]
                "${resources.getString(R.string.station_update_by_address)} $stationAddress, $companyName"
            }
            else -> {
                " "
            }
        }
    }

    // if station located in favorites return true
    private suspend fun checkStations(data: Map<String, String>): Boolean {
        val favorites = try {
            FavoritesDaoImpl().findAll()
        } catch (e: Exception) {
            App().initRealm(this)
            FavoritesDaoImpl().findAll()
        }
        val id = data.get(Constants.STATION_ID)

        val model = favorites.find { it.gasStationId == id }

        return model != null

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkNotificationChannel(CHANNEL_ID: String) {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            getString(R.string.app_name),
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.description = resources.getString(R.string.channel_description)
        notificationChannel.enableLights(true)
        notificationChannel.enableVibration(true)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }

    override fun onNewToken(p0: String) {
        token = p0
        super.onNewToken(p0)
    }
}
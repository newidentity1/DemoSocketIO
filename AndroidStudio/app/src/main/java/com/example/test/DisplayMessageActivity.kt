package com.example.test

import android.app.ApplicationErrorReport
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter

class DisplayMessageActivity : AppCompatActivity() {

    lateinit var mSocket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)

        val message = intent.getStringExtra("com.example.test.MESSAGE")
        val textView = findViewById<TextView>(R.id.textView2).apply {
            text = message
        }

        try {
            //This address is the way you can connect to localhost with AVD(Android Virtual Device)
            mSocket = IO.socket("http://10.0.2.2:3000")
            mSocket.on(Socket.EVENT_CONNECT, onConnect)
            mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            mSocket.connect()
            Log.d("waiting", "waiting to connect")
            mSocket.emit("chat message", "allo")
            //Register all the listener and callbacks here.

        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("fail", "Failed to connect")
        }

    }
    var onConnectError = Emitter.Listener {
        Log.d("sadddd", it[0].toString())
    }
    var onConnect = Emitter.Listener {
        //After getting a Socket.EVENT_CONNECT which indicate socket has been connected to server,
        //send userName and roomName so that they can join the room.
        Log.d("connect", "connected")
    }

}
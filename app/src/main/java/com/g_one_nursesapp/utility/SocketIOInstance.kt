package com.g_one_nursesapp.utility

import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import java.lang.RuntimeException
import java.net.URISyntaxException

private const val URI = "https://g-one-db.an.r.appspot.com/"

class SocketIOInstance {
    private var mSocket: Socket? = null

    fun initSocket() {
        try {
            val opts = IO.Options()
            opts.forceNew = true
            opts.reconnection = true
            mSocket = IO.socket(URI, opts)
        } catch(e: URISyntaxException) {
            throw RuntimeException(e)
        }
    }

    fun connectToSocket() {
        if (!mSocket?.connected()!!) {
            mSocket!!.connect()
        }
    }

    fun getSocket(): Socket? = mSocket
}
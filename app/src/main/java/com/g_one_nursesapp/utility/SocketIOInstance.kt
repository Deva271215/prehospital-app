package com.g_one_nursesapp.utility

import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import java.lang.RuntimeException
import java.net.URISyntaxException

private const val URI = "http://192.168.43.84:8080"

class SocketIOInstance {
    private var mSocket: Socket? = null

    fun connectToSocketServer() {
        try {
            val opts = IO.Options()
            opts.forceNew = true
            opts.reconnection = true
            mSocket = IO.socket(URI, opts)
        } catch(e: URISyntaxException) {
            throw RuntimeException(e)
        }
    }

    fun getSocket(): Socket? = mSocket
}
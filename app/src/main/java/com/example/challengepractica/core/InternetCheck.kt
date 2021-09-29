package com.example.challengepractica.core

import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.net.InetSocketAddress
import java.net.Socket

object InternetCheck {

    suspend fun isNetworkAvailable() = coroutineScope {
        //return@coroutineScope (retorna la ultima linea como true o false)
        return@coroutineScope try {
            val sock = Socket()
            val socketAdress = InetSocketAddress("8.8.8.8", 53)
            sock.connect(socketAdress, 2000)
            sock.close()
            true
        } catch (e: Exception) {
            false
        }
    }
}
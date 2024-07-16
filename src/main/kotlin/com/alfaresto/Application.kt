package com.alfaresto

import com.alfaresto.plugins.*
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import java.util.Base64


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureMonitoring()
    configureRouting()


    val base64EncodedKey = System.getenv("encoded_service_account_key")
    val serviceAccountBytes = Base64.getDecoder().decode(base64EncodedKey)
    val serviceAccountStream = serviceAccountBytes.inputStream()

//    val serviceAccountStream = this::class.java.classLoader.getResourceAsStream("service_account_key.json")
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
        .build()

    FirebaseApp.initializeApp(options)
}

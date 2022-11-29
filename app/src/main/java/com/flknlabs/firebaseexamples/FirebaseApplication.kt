package com.flknlabs.firebaseexamples

import android.app.Application
import com.google.firebase.FirebaseApp

class FirebaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}
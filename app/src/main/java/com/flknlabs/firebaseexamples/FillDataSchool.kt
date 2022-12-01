package com.flknlabs.firebaseexamples

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_fill_data_school.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btnRegister

class FillDataSchool : AppCompatActivity() {
    private val db = Firebase.firestore

    companion object {
        val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_data_school)

        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btnRegister.setOnClickListener {
            saveRealtimeDB()//saveHardcoded() }
        }
    }
    private fun saveHardcoded() {
        val user = hashMapOf(
            "name" to "Ada Ava",
            "age" to 29,
            "isStudent" to true
        )

        val studentsRef: CollectionReference =
            db.collection("users")
                .document("students")
                .collection("isMajor")



        readAStoredData("Perez", studentsRef) {
            val a = it

            /*it?.let {
                writeStorage(it) {
                }
            }*/

        }

    }

    private fun saveRealtimeDB(){
        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")

        //guardo un objeto
        val student = Student(
            name = "Gabriel villarreal",
            age = 44,
            isStudent = false,
        )
        val newRef = database.getReference("student")
        newRef.setValue(newRef)

        //guardo un arreglo
        val teacher = listOf<Teacher>(
            Teacher("gabriel",34,true),
            Teacher("ana",34,false)

        )
        val newRef2 = database.getReference("teachers")
        newRef2.setValue(teacher)

    }

    private fun readRealtimeDB(){

    }

    private fun writeStorage(
        ref: DocumentReference,
        callback: (Boolean) -> Unit) {

        ref.set(mapOf("Student" to Student(
            name = "David Perez",
            age = 29,
            isStudent = false,
        )))
            .addOnSuccessListener {
                Log.d(TAG, "Snapshot added")
                callback(true)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                callback(false)
            }
    }

    private fun readAStoredData(
        containsName: String,
        ref: CollectionReference,
        callback: (Student?) -> Unit) {

        ref.get()
            .addOnSuccessListener { result ->
                result.metadata?.let {
                    val auxStudent = Gson().toJson(it)//["Student"])
                    val student = Gson().fromJson(auxStudent, Student::class.java)

                    if (student.name.contains(containsName)) callback(student)
                    else callback(null)
                }
                callback(null)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
                callback(null)
            }
    }
}

data class Student(
    val name: String,
    val age: Int,
    @SerializedName("student")
    val isStudent: Boolean
)
data class Teacher(
    val name: String,
    val age: Int,
    @SerializedName("teacher")
    val isStudent: Boolean
)



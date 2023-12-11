package com.example.passwordmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class DeletePage : AppCompatActivity() {
    private lateinit var db:DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_page)

        val deleteAppNameET = findViewById<EditText>(R.id.deleteAppNameET)
        val deleteUsernameET = findViewById<EditText>(R.id.deleteUsernameET)
        val backBtn = findViewById<Button>(R.id.deletePageBackBtn)
        val deleteBtn = findViewById<Button>(R.id.deleteBtn)
        db = DBHelper(this)

        deleteBtn.setOnClickListener() {
            val appName = deleteAppNameET.text.toString()
            val username = deleteUsernameET.text.toString()

            if (appName.isNotBlank() && username.isNotBlank()) {
                val deletedRows = db.deletePassword(appName, username)
                if (deletedRows > 0) {
                    Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Problem encountered when deleting", Toast.LENGTH_SHORT).show()
                }
            }
        }

        fun toHomePage(view: View) {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
        backBtn.setOnClickListener() {
            toHomePage(it)
        }
    }
}
package com.example.passwordmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

class AddPage : AppCompatActivity() {
    private lateinit var db:DBHelper
    private lateinit var category:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_page)

        val appNameET = findViewById<EditText>(R.id.appNameET)
        val usernameET = findViewById<EditText>(R.id.usernameET)
        val passwordET = findViewById<EditText>(R.id.passwordET)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val generateRandomPasswordBtn = findViewById<Button>(R.id.generateRandomPasswordBtn)
        val saveBtn = findViewById<Button>(R.id.saveBtn)
        val backBtn = findViewById<ImageButton>(R.id.backBtn)
        db = DBHelper(this)

        generateRandomPasswordBtn.setOnClickListener() {
            val generatedPassword = generateRandomPassword()
            passwordET.setText(generatedPassword)
        }

        backBtn.setOnClickListener() {
            toMainPage(it)
        }

        saveBtn.setOnClickListener() {
            val appName = appNameET.text.toString()
            val username = usernameET.text.toString()
            val password = passwordET.text.toString()
            val checkedRadioButtonId = radioGroup.checkedRadioButtonId
            if (checkedRadioButtonId == -1) {
                Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            when (checkedRadioButtonId) {
                R.id.socialMediaRadio -> {
                    category = "socialMedia"
                }
                R.id.educationRadio -> {
                    category = "education"
                }
                R.id.financeRadio -> {
                    category = "finance"
                }
                R.id.personalRadio -> {
                    category = "personal"
                }
            }
            val saveData = db.savePasswordData(appName, username, password, category)
            // Validate the data
            if (TextUtils.isEmpty(appName) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(category)) {
                Toast.makeText(this, "Add missing credentials", Toast.LENGTH_SHORT).show()
            } else {
                // Save the data
                if (saveData) {
                    Toast.makeText(this, "Password saved", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Password exists", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun toMainPage(view: View) {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun generateRandomPassword():String {
        val charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_-+=<>?"
        val passwordLength = 12
        val random = java.util.Random()
        return (1..passwordLength)
            .map { charset[random.nextInt(charset.length)] }
            .joinToString("")
    }
}
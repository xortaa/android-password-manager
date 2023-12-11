package com.example.passwordmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast

class UpdatePage : AppCompatActivity() {
    private lateinit var db:DBHelper
    private lateinit var newCategory:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_page)

        val updateAppNameET = findViewById<EditText>(R.id.updateAppNameET)
        val updateUsernameET = findViewById<EditText>(R.id.updateUsernameET)
        val updateNewPasswordET = findViewById<EditText>(R.id.updateNewPasswordET)
        val radioGroup = findViewById<RadioGroup>(R.id.updateRadioGroup)
        val updateSocialMediaRadio = findViewById<RadioButton>(R.id.updateSocialMediaRadio)
        val updateFinanceRadio = findViewById<RadioButton>(R.id.updateFinanceRadio)
        val updateEducationRadio = findViewById<RadioButton>(R.id.updateEducationRadio)
        val updatePersonalRadio = findViewById<RadioButton>(R.id.updatePersonalRadio)
        val backBtn = findViewById<Button>(R.id.updatePageBackBtn)
        val updateBtn = findViewById<Button>(R.id.updateBtn)
        db = DBHelper(this)

        updateBtn.setOnClickListener() {
            val appName = updateAppNameET.text.toString()
            val username = updateUsernameET.text.toString()
            val newPassword = updateNewPasswordET.text.toString()
            when (radioGroup.checkedRadioButtonId) {
                R.id.updateSocialMediaRadio -> {
                    newCategory = "socialMedia"
                }
                R.id.updateEducationRadio -> {
                    newCategory = "education"
                }
                R.id.updateFinanceRadio -> {
                    newCategory = "finance"
                }
                R.id.updatePersonalRadio -> {
                    newCategory = "personal"
                }
            }
            val rowsAffected = db.updatePassword(appName, username, newPassword, newCategory)

            if (rowsAffected > 0) {
                Toast.makeText(this, "Updated Succesfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Encountered an error when updating", Toast.LENGTH_SHORT).show()
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
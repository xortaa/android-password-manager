package com.example.passwordmanager

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var db:DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addBtn = findViewById<FloatingActionButton>(R.id.addBtn)
        val socialMediaBtn = findViewById<Button>(R.id.socialMediaBtn)
        val educationBtn = findViewById<Button>(R.id.educationBtn)
        val financeBtn = findViewById<Button>(R.id.financeBtn)
        val personalBtn = findViewById<Button>(R.id.personalBtn)
        val toDeletePageBtn = findViewById<ImageButton>(R.id.toDeletePageBtn)
        val updatePageBtn = findViewById<ImageButton>(R.id.updatePageBtn)
        db = DBHelper(this)


        fun getPasswordsForCategory(category: String, title: String) {
            val cursor = db.getText()
            if (cursor?.count == -1) {
                Toast.makeText(this, "No Passwords", Toast.LENGTH_SHORT).show()
            }
            val stringBuffer = StringBuffer()
            while (cursor!!.moveToNext()) {
                val currentCategory = cursor.getString(3)
                if (currentCategory.equals(category, ignoreCase = true)) {
                    stringBuffer.append(cursor.getString(0).uppercase(Locale.getDefault()) + "\n")
                    stringBuffer.append("username: " + cursor.getString(1) + "\n")
                    stringBuffer.append("password: " + cursor.getString(2) + "\n\n")
                }
            }
            val builder = AlertDialog.Builder(this)
            builder.setCancelable(true)
            builder.setTitle(title)
            builder.setMessage(stringBuffer.toString())
            builder.show()
        }

        socialMediaBtn.setOnClickListener() {
            getPasswordsForCategory("socialMedia", "Social Media Passwords")
        }

        educationBtn.setOnClickListener() {
            getPasswordsForCategory("education", "Education Passwords")
        }

        financeBtn.setOnClickListener() {
            getPasswordsForCategory("finance", "Finance Passwords")
        }

        personalBtn.setOnClickListener() {
            getPasswordsForCategory("personal", "Personal Passwords")
        }

        fun toAddPage(view: View) {
            val i = Intent(this, AddPage::class.java)
            startActivity(i)
        }
        addBtn.setOnClickListener() {
            toAddPage(it)
        }

        fun toDeletePage(view: View) {
            val i = Intent(this, DeletePage::class.java)
            startActivity(i)
        }
        toDeletePageBtn.setOnClickListener() {
            toDeletePage(it)
        }

        fun toUpdatePage(view: View) {
            val i = Intent(this, UpdatePage::class.java)
            startActivity(i)
        }
        updatePageBtn.setOnClickListener() {
            toUpdatePage(it)
        }



    }
}
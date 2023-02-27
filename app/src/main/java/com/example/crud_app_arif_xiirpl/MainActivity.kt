package com.example.crud_app_arif_xiirpl

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.crud_app_arif_xiirpl.R.id.idProf
import com.example.crud_app_arif_xiirpl.helper.DBHelper
import com.google.android.material.textfield.TextInputEditText
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var inputName: TextInputEditText
    lateinit var inputAge: TextInputEditText
    lateinit var btnAdd: Button
    lateinit var btnPrint: Button
    lateinit var textID: TextView
    lateinit var textName: TextView
    lateinit var textAge: TextView
    var progressDialog: ProgressDialog? = null

    @SuppressLint("Range", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inputName = findViewById(R.id.inputName)
        inputAge = findViewById(R.id.inputAge)
        btnAdd = findViewById(R.id.btnAdd)
        btnPrint = findViewById(R.id.btnPrint)
        textID = findViewById(idProf)
        textName = findViewById(R.id.name)
        textAge = findViewById(R.id.age)

        loadHandler()
        btnAdd.setOnClickListener {
            val db = DBHelper(this, null)
            val name = inputName.text.toString()
            val age = inputAge.text.toString()

            db.addProfile(name, age)

            inputAge.text!!.clear()
            inputName.text!!.clear()
        }

        btnPrint.setOnClickListener {
        loadHandler()
        }
    }

        @SuppressLint("Range")
        fun loadHandler() {
            val db = DBHelper(this, null)
            val cursor = db.getProfile()
            progressDialog = ProgressDialog(this@MainActivity)
            progressDialog!!.setTitle("Loading")
            progressDialog!!.setMessage("Data akan muncul...")
            progressDialog!!.max = 100
            progressDialog!!.progress = 1
            progressDialog!!.show()

            Thread(Runnable {
                try {
                    Thread.sleep(1000)
                } catch (e:Exception){
                    e.printStackTrace()
                }
                progressDialog!!.dismiss()
            }).start()

            if (cursor!!.moveToFirst()) {
                textID.text = "id\n"
                textName.text = "Name\n"
                textAge.text = "Age\n"
                textID.append(
                    cursor.getString(
                        cursor.getColumnIndex(DBHelper.ID_COL)
                    ) + "\n"
                )
                textName.append(
                    cursor.getString(
                        cursor.getColumnIndex(DBHelper.NAME_COL)
                    ) + "\n"
                )
                textAge.append(
                    cursor.getString(
                        cursor.getColumnIndex(DBHelper.AGE_COL)
                    ) + "\n"
                )
            }
            if (cursor.moveToNext()) {
                while (cursor.moveToNext()) {
                    textID.append(
                        cursor.getString(
                            cursor.getColumnIndex(DBHelper.ID_COL)
                        ) + "\n"
                    )
                    textName.append(
                        cursor.getString(
                            cursor.getColumnIndex(DBHelper.NAME_COL)
                        ) + "\n"
                    )
                    textAge.append(
                        cursor.getString(
                            cursor.getColumnIndex(DBHelper.AGE_COL)
                        ) + "\n"
                    )
                }
                cursor.close()
            }

        }

}

package com.example.consultacep

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val edtCep = findViewById<TextInputEditText>(R.id.edtCep)

        val buttConsultar = findViewById<Button>(R.id.buttConsultar)
        buttConsultar.setOnClickListener {
            val CEP = edtCep.text.toString()
            if(CEP.length != 8){
                Toast.makeText(this, "CEP inválido", Toast.LENGTH_SHORT).show()
            }else   {
                Toast.makeText(this, "CEP válido", Toast.LENGTH_SHORT).show()
            }
        }

        val txtLogradouro = findViewById<EditText>(R.id.txtLogradouro)

        val txtBairro = findViewById<EditText>(R.id.txtBairro)

        val txtUF = findViewById<EditText>(R.id.txtUF)


        val txtDDD = findViewById<EditText>(R.id.txtDDD)
    }
}
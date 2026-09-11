package com.example.consultacep

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.consultacep.api.ViaCepClient
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

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

            val txtLogradouro = findViewById<EditText>(R.id.txtLogradouro)
            val txtBairro = findViewById<EditText>(R.id.txtBairro)
            val txtUF = findViewById<EditText>(R.id.txtUF)
            val txtDDD = findViewById<EditText>(R.id.txtDDD)
            val txtLocalidade = findViewById<EditText>(R.id.txtLocalidade)

            val CEP = edtCep.text.toString()
            if(CEP.length != 8){
                edtCep.error = "CEP inválido"
                return@setOnClickListener
            }
            lifecycleScope.launch {
                val logradouro = ViaCepClient.instance.buscarEndereco(CEP)
                txtLogradouro.setText(logradouro.logradouro)
            }
            lifecycleScope.launch {
                val bairro = ViaCepClient.instance.buscarEndereco(CEP)
                txtBairro.setText(bairro.bairro)
            }
            lifecycleScope.launch {
                val uf = ViaCepClient.instance.buscarEndereco(CEP)
                txtUF.setText(uf.uf)
            }
            lifecycleScope.launch {
                val ddd = ViaCepClient.instance.buscarEndereco(CEP)
                txtDDD.setText(ddd.ddd)
            }
            lifecycleScope.launch {
                val cidade = ViaCepClient.instance.buscarEndereco(CEP)
                txtLocalidade.setText(cidade.localidade)
            }
        }
    }
}
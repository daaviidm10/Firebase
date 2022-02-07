package com.kiskos.apprepaso2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Pantalla_Inicial : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_inicial)

        val botonCliente:Button = findViewById(R.id.bCliente)
        botonCliente.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
            }
            //Inicio el intent
            startActivity(intent)
        }
    }
}
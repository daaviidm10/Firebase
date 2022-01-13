package com.kiskos.apprepaso2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inicializar FireBase Auth
        auth = Firebase.auth
        //Creo el boton para darse de alta
        val darseDeAlta: Button = findViewById(R.id.botonLoguearse)
        darseDeAlta.setOnClickListener{
            //Declaro la variable de usuario para recojer el texto
            val usuario: EditText = findViewById(R.id.correoUsuario)
            //Declaro la variable de contraseña para recojer el texto
            val contraseña: EditText = findViewById(R.id.contraseñaUsuario)
            //Llamo al metodo donde de crear cuenta y le paso de variables el correo y la contraseña
            crearCuenta(usuario.text.toString(),contraseña.text.toString())
        }
        //creo el boton para darse de alta
        val accederCuenta: Button = findViewById(R.id.botonIniciarsesion)
        accederCuenta.setOnClickListener{
            //Declaro la variable de usuario para recojer el texto
            val usuario: EditText = findViewById(R.id.correoUsuario)
            //Declaro la variable de contraseña para recojer el texto
            val contraseña: EditText = findViewById(R.id.contraseñaUsuario)
            //Llamo al metodo donde accedo a la cuenta y le paso de variables el correo y la contraseña
            accederCuenta(usuario.text.toString(),contraseña.text.toString())
        }

    }
    public override fun onStart() {
        super.onStart()
        //Compruebe si el usuario ha iniciado sesión (no nulo) y actualice la interfaz de usuario en consecuencia.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }
    /**
     * @param email Correo del usuario
     * @param password Contraseña del usuario
     */
    private fun crearCuenta(email: String, password: String) {
        //Crear nuevo usuario
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Iniciar sesión correctamente, actualizar la interfaz de usuario con la información del usuario que inició sesión
                    Log.i("bien", "Crear Usuario Bien")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // Si el inicio de sesión falla, muestre un mensaje al usuario.
                    Log.i("mal", "Crear Usuario Mal", task.exception)
                    Toast.makeText(baseContext, "Fallo en autenticación.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
        // [Fin Crear Cuenta]
    }
    /**
     * @param email Correo del usuario
     * @param password Contraseña del usuario
     */
    //Este metodo no funciona bien en el emulador de android pero si cuando lo pruebo en mi telefono
    private fun accederCuenta (email: String, password: String) {
        // [acceder a cuenta]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Iniciar sesión correctamente, actualizar la interfaz de usuario con la información del usuario que inició sesión
                    Log.d("bien", "Acceso correcto")
                    val user = auth.currentUser
                    updateUI(user)
                    Toast.makeText(baseContext, "Bien.",
                        Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MapsActivity::class.java).apply {
                    }
                    //Inicio el intent
                    startActivity(intent)
                } else {
                    // Si el inicio de sesión falla, muestre un mensaje al usuario.
                    Log.w("mal", "Acceso fallido", task.exception)
                    Toast.makeText(baseContext, "Fallo en la autenticacion",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
        // [Fin de acceso a cuenta]
    }

    private fun updateUI(user: FirebaseUser?) {

    }

    private fun reload() {

    }
}
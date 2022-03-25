
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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        val Alta: Button = findViewById(R.id.botonLoguearse)
        Alta.setOnClickListener{

            val usuario: EditText = findViewById(R.id.correoUsuario)
            val contraseña: EditText = findViewById(R.id.contraseñaUsuario)
            Cuenta(usuario.text.toString(),contraseña.text.toString())
        }
        val acceso: Button = findViewById(R.id.botonIniciarsesion) //boton para darse de alta
        acceso.setOnClickListener{
            val usuario: EditText = findViewById(R.id.correoUsuario)
            /
            val contrasinal: EditText = findViewById(R.id.contraseñaUsuario)

            acceso(usuario.text.toString(),contrasinal.text.toString())
        }
    }

    private fun Cuenta(email: String, password: String) {
        //Crear nuevo usuario
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.i("log in correcto", "Usuario creado correctamente")// If para comprobar si inicia sesión correctamente
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // Si el inicio de sesión falla, muestre un mensaje al usuario.
                        Log.i("log in incorrecto", "Usuario no creado", task.exception)
                        Toast.makeText(baseContext, "Error en la autenticación.",
                                Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
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


    private fun accesoCuenta (email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Iniciar sesión correctamente, actualizar la interfaz de usuario con la información del usuario que inició sesión
                        Log.d("bien", "Acceso correcto")
                        val user = auth.currentUser
                        updateUI(user)
                        Toast.makeText(baseContext, "Bien.",
                                Toast.LENGTH_SHORT).show()

                        if (intent.getStringExtra(EXTRA_MESSAGE)=="Profesional") {
                            intent = Intent(this, ProfesionalActivity::class.java).apply {

                                finish()
                            }
                            Log.i("Usuario", "Logeado como Profesional")
                        }else if(intent.getStringExtra(EXTRA_MESSAGE)=="Cliente"){
                            intent = Intent(this, ClienteActivity::class.java).apply {

                                finish()
                            }
                            Log.i("Usuario", "Logeado como Cliente")
                        }

                        startActivity(intent) //Inicio el intent
                    } else {

                        Log.w("Error", "Acceso fallido", task.exception)
                        Toast.makeText(baseContext, "Fallo en la autenticacion",
                                Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }

    }


}
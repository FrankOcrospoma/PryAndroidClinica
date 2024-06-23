package com.example.pryandroidclinica

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.pryandroidclinica.response.LoginResponse
import com.example.pryandroidclinica.retrofit.ApiService
import com.example.pryandroidclinica.retrofit.RetrofitClient
import com.google.android.material.navigation.NavigationView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UserProfileActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var imageView: ImageView
    private lateinit var imageUri: Uri
    private lateinit var imagePath: String
    companion object {
        const val REQUEST_CODE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)

        // Configuración del DrawerLayout y NavigationView
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        imageView = findViewById(R.id.imagen_perfil_usuario)

        // Obtención de datos de SharedPreferences
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val id = sharedPreferences.getInt("id", 1)
        val user = sharedPreferences.getString("username", "")
        val estado = sharedPreferences.getInt("estado", 1)
        val token = sharedPreferences.getString("token", "")
        val estadoToken = sharedPreferences.getInt("estadoToken", 1)
        val nombre = sharedPreferences.getString("nombreUsuario", "")
        val apeCompleto = sharedPreferences.getString("ape_completo", "")
        val fechaNacimiento = sharedPreferences.getString("fecha_nac", " ")
        val documento = sharedPreferences.getString("documento", "")
        val tipo_documento_id = sharedPreferences.getInt("tipo_documento_id", 1)
        val sexo = sharedPreferences.getInt("sexo", 1)
        val direccion = sharedPreferences.getString("direccion", "")
        val telefono = sharedPreferences.getString("telefono", "")
        val foto = sharedPreferences.getString("foto", "")
        val rolId = sharedPreferences.getInt("rolId", 1)

        val nombre2 = sharedPreferences.getString("nombreUsuario", "Jane Doe")
        val correoUsuario = sharedPreferences.getString("email", "jane.doe@example.com")

        // Configuración del header del NavigationView
        val headerView = navView.getHeaderView(0)
        val navHeaderTitle: TextView = headerView.findViewById(R.id.nav_header_title)
        val navHeaderSubtitle: TextView = headerView.findViewById(R.id.nav_header_subtitle)
        navHeaderTitle.text = nombre2
        navHeaderSubtitle.text = correoUsuario

        // Llenado de campos con datos del usuario
        findViewById<EditText>(R.id.txtPerfilNickUsuario).setText(nombre)
        findViewById<EditText>(R.id.txtApellido).setText(apeCompleto)
        findViewById<EditText>(R.id.nombre_completo_perfil_usuario).setText(user)
        findViewById<EditText>(R.id.txtPerfilFechaNacimiento).setText(fechaNacimiento)
        findViewById<EditText>(R.id.txtPerfilDocumento).setText(documento)
        findViewById<EditText>(R.id.txtPerfilEmail).setText(correoUsuario)
        findViewById<EditText>(R.id.txtPerfilDireccion).setText(direccion)
        findViewById<EditText>(R.id.txtPerfilTelefono).setText(telefono)

        // Inicialización de imagePath con el valor de foto
        imagePath = (foto ?: "")

        // Cargar la imagen usando Glide
        val imageUrl = RetrofitClient.URL_API_SERVICE + "/" + (foto ?: "")
        Glide.with(this)
            .load(imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e("UserProfileActivity", "Error al cargar la imagen: ${e?.message}")
                    Log.e("UserProfileActivity", imageUrl)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(imageView)

        // Configuración del botón de guardar
        findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            val nuevouser = findViewById<EditText>(R.id.nombre_completo_perfil_usuario).text.toString()
            val nuevoapellido = findViewById<EditText>(R.id.txtApellido).text.toString()
            val nuevoNombre = findViewById<EditText>(R.id.txtPerfilNickUsuario).text.toString()
            val nuevaFechaNac = findViewById<EditText>(R.id.txtPerfilFechaNacimiento).text.toString()
            val nuevoDocumento = findViewById<EditText>(R.id.txtPerfilDocumento).text.toString()
            val nuevoEmail = findViewById<EditText>(R.id.txtPerfilEmail).text.toString()
            val nuevaDireccion = findViewById<EditText>(R.id.txtPerfilDireccion).text.toString()
            val nuevoTelefono = findViewById<EditText>(R.id.txtPerfilTelefono).text.toString()

            val apiService = RetrofitClient.createService()
            val call = apiService.actualizarUsuario(
                id,
                nuevouser,
                nuevoEmail,
                estado,
                token ?: "",
                estadoToken,
                nuevoNombre,
                nuevoapellido,
                nuevaFechaNac,
                nuevoDocumento,
                tipo_documento_id,
                sexo,
                nuevaDireccion,
                nuevoTelefono,
                imagePath,
                rolId
            )

            call.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful && response.body()?.isStatus == true) {
                        Toast.makeText(this@UserProfileActivity, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show()

                        val editor = sharedPreferences.edit()
                        editor.putString("username", nuevouser)
                        editor.putString("fecha_nac", nuevaFechaNac)
                        editor.putString("documento", nuevoDocumento)
                        editor.putString("email", nuevoEmail)
                        editor.putString("foto", imagePath)
                        editor.putString("direccion", nuevaDireccion)
                        editor.putString("telefono", nuevoTelefono)
                        editor.apply()
                    } else {
                        Log.e("UserProfileActivity", "Error al actualizar datos: ${response.errorBody()?.string()}")
                        Toast.makeText(this@UserProfileActivity, "Error al actualizar datos", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("UserProfileActivity", "Error: ${t.message}")
                    Toast.makeText(this@UserProfileActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // Agregar listener al botón de retroceder
        val btnRetroceder: ImageButton = findViewById(R.id.btn_retroceder)
        btnRetroceder.setOnClickListener {
            onBackPressed()
        }

        findViewById<ImageView>(R.id.imagen_perfil_usuario).setOnClickListener {
            seleccionarImagen()
        }

        findViewById<Button>(R.id.btnCambiarContraseña).setOnClickListener {
            val intent = Intent(this, RestablecerContrasenaActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun seleccionarImagen() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data!!
            imageView.setImageURI(imageUri)

            imagePath = "img/"+ File(getRealPathFromURI(imageUri)).name

            val file = File(getRealPathFromURI(imageUri))
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body = MultipartBody.Part.createFormData("foto", file.name, requestFile)

            val apiService = RetrofitClient.createService()
            val call = apiService.subirFoto(body)
            call.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful && response.body()?.isStatus == true) {
                        Toast.makeText(this@UserProfileActivity, "Foto actualizada correctamente", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@UserProfileActivity, "Error al actualizar foto", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@UserProfileActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun getRealPathFromURI(contentUri: Uri): String {
        val cursor = contentResolver.query(contentUri, null, null, null, null)
        cursor?.moveToFirst()
        val idx = cursor?.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        val realPath = cursor?.getString(idx!!)
        cursor?.close()
        return realPath!!
    }
}

package fr.epita.mytodolist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val validUserIds = setOf(1, 2, 3, 4, 9) // Set of valid user IDs
    private val validUsers = mapOf(1 to "MOM", 2 to "DAD", 3 to "ME", 4 to "SISTER", 9 to "GUEST")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton: Button = findViewById(R.id.btnLogin)
        val usernameEditText: EditText = findViewById(R.id.etLogin)
        val epitaImageView: ImageView = findViewById(R.id.ivEpitaLogo)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val userId = validUsers.filterValues { it == username }.keys.firstOrNull()

            if (userId != null && userId in validUserIds) {
                // Start ListActivity and pass the user ID
                val intent = Intent(this, ListActivity::class.java)
                intent.putExtra("userId", userId)
                intent.putExtra("username", username)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid credentials.", Toast.LENGTH_LONG).show()
            }
        }

        epitaImageView.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.epita.fr"))
            startActivity(browserIntent)
        }
    }
}

package com.example.useless_facts_otter
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainLayout: ConstraintLayout
    private lateinit var factTextView: TextView
    private lateinit var otterImageView: ImageView
    private lateinit var factBubble: ConstraintLayout
    private var factJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainLayout = findViewById(R.id.mainLayout)
        factTextView = findViewById(R.id.factText) // assuming you have a TextView with id factText in your factBubble layout
        factBubble = findViewById(R.id.factBubble)
        otterImageView = findViewById(R.id.localImageView)

        Picasso.get().load("https://www.clipartmax.com/png/middle/274-2740304_sea-otter-clipart-otter-transparent-background.png").into(otterImageView)

        mainLayout.setOnClickListener {
            fetchFact()
        }

        fetchFact()
    }

    private fun fetchFact() {
        factJob?.cancel()
        factJob = CoroutineScope(Dispatchers.IO).launch {
            val fact = ApiClient.getFact()
            withContext(Dispatchers.Main) {
                factTextView.text = fact
                factBubble.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        factJob?.cancel()
    }
}

package com.example.digimonlist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imageViewDetail: ImageView = findViewById(R.id.imageViewDetail)
        val textViewName: TextView = findViewById(R.id.textViewName)
        val textViewType: TextView = findViewById(R.id.textViewType)
        val wikiButton: Button = findViewById(R.id.wikiButton)

        val digimon: Digimon? = intent.getParcelableExtra("digimon", Digimon::class.java)

        digimon?.let {
            textViewName.text = it.name
            textViewType.text = it.type
            imageViewDetail.setImageResource(getImageResource(it.imageResId))

            val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
            imageViewDetail.startAnimation(fadeInAnimation)

            imageViewDetail.setOnClickListener {
                val intent = Intent(this, ImageViewActivity::class.java).apply {
                    putExtra("digimon_image", digimon.imageResId)
                }
                startActivity(intent)
            }

            wikiButton.text = getString(R.string.view_on_wiki)
            wikiButton.setOnClickListener {
                val wikiUrl = "https://digimon.fandom.com/wiki/${digimon.name}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl))
                startActivity(intent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.share_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                showShareDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showShareDialog() {
        AlertDialog.Builder(this)
            .setTitle("Share Digimon")
            .setMessage("Do you want to share this digimon?")
            .setPositiveButton("Yes") { dialog, _ ->
                sendShareBroadcast()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun sendShareBroadcast() {
        val intent = Intent("com.example.digimonlist.SHARE_CONTENT")
        intent.putExtra("content", "Share this digimon")
        sendBroadcast(intent)
    }

    private fun getImageResource(imageName: String): Int {
        return resources.getIdentifier(imageName, "drawable", packageName)
    }
}

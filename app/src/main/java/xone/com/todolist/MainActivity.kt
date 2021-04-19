package xone.com.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xone.com.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* val window: Window = this@MainActivity.window
         window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
         window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
         window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.white_primary)*/
    }
}
package hsj.shahram.film.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import hsj.shahram.film.R
import hsj.shahram.film.databinding.ActivityMainBinding


private lateinit var binding : ActivityMainBinding;
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main)


    }


}
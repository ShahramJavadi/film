package hsj.shahram.film.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import hsj.shahram.film.AppController
import hsj.shahram.film.R
import hsj.shahram.film.databinding.ActivityMainBinding


private lateinit var binding: ActivityMainBinding;

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        init()

    }


    private fun init() {

       (application as AppController).appComponent.inject(this)
        setupNavigation()


    }


    private fun setupNavigation() {

       val navController = findNavController(R.id.fragment)
        binding.bottomNavigationView.setupWithNavController(navController)







    }

}

package com.example.recipeapp

import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.R
import com.example.recipeapp.CategoriesListFragment
import com.example.recipeapp.SearchFragment
import com.example.recipeapp.MenuFragment
import com.example.recipeapp.FavoritesFragment

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var homeButton: ImageButton
    private lateinit var searchButton: ImageButton
    private lateinit var flagButton: ImageButton
    private lateinit var menuButton: ImageButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        homeButton = binding.root.findViewById(R.id.homeButton)
        searchButton = binding.root.findViewById(R.id.searchButton)
        flagButton = binding.root.findViewById(R.id.flagButton)
        menuButton = binding.root.findViewById(R.id.menuButton)



        supportFragmentManager.commit {
            replace(R.id.mainContainer, CategoriesListFragment(), null)
            setReorderingAllowed(true)
        }


        homeButton.setOnClickListener {
            selectButton(homeButton)
            supportFragmentManager.commit {
                replace(R.id.mainContainer, CategoriesListFragment(), null)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

        searchButton.setOnClickListener {
            selectButton(searchButton)

            supportFragmentManager.commit {
                replace(R.id.mainContainer, SearchFragment(), null)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

        flagButton.setOnClickListener {
            selectButton(flagButton)

            supportFragmentManager.commit {
                replace(R.id.mainContainer, FavoritesFragment(), null)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

        menuButton.setOnClickListener {
            selectButton(menuButton)
            supportFragmentManager.commit {
                replace(R.id.mainContainer, MenuFragment(), null)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

    }



    private fun selectButton(selectedButton: ImageButton) {
        homeButton.isSelected = false
        searchButton.isSelected = false
        flagButton.isSelected = false
        menuButton.isSelected = false
        selectedButton.isSelected = true
    }
}


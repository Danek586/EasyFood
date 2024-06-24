package com.example.recipeapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace

class SearchFragment : Fragment(), RecipesListAdapter.OnItemClickListener {

    private lateinit var editTextSearch: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipesListAdapter
    private var recipes: List<Recipe> = STUB.getAllRecipes()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        editTextSearch = view.findViewById(R.id.editTextSearch)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = RecipesListAdapter(recipes)
        recyclerView.adapter = adapter

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                performSearch(query)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Установка текущего фрагмента в качестве слушателя для кликов по элементам списка
        adapter.setOnItemClickListener(this)

        return view
    }

    private fun performSearch(query: String) {
        val results = if (query.isEmpty()) {
            recipes
        } else {
            recipes.filter { it.title.contains(query, ignoreCase = true) }
        }
        adapter.updateData(results)
    }

    override fun onItemClick(recipeId: Int) {
        openRecipeByRecipeId(recipeId)
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val recipe = STUB.getRecipeById(recipeId)
        val bundle = bundleOf(ARG_RECIPE to recipe)
        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}

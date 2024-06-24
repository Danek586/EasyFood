package com.example.recipeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.fragment.app.commit

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.databinding.FragmentListCategoriesBinding
import java.util.concurrent.TimeUnit

class CategoriesListFragment : Fragment() {

    private var _binding: FragmentListCategoriesBinding? = null
    private val binding get() = _binding!!

    private lateinit var recommendedAdapter: RecipesListAdapter
    private val handler = android.os.Handler(android.os.Looper.getMainLooper())
    private val updateInterval = TimeUnit.HOURS.toMillis(24)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecommendedRecipes()
        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        handler.removeCallbacksAndMessages(null)
    }

    private fun initRecommendedRecipes() {
        binding.recommendedTitle.visibility = View.VISIBLE

        binding.rvRecommended.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recommendedAdapter = RecipesListAdapter(emptyList())
        binding.rvRecommended.adapter = recommendedAdapter

        handler.post(object : Runnable {
            override fun run() {
                updateRecommendedRecipes()
                handler.postDelayed(this, updateInterval)
            }
        })
    }

    private fun updateRecommendedRecipes() {
        val allRecipes = STUB.getAllRecipes()
        val randomRecipes = allRecipes.shuffled().take(7)
        recommendedAdapter.updateData(randomRecipes)
    }

    private fun initRecycler() {
        val dataSet = STUB.getCategories()
        val categoriesListAdapter = CategoriesListAdapter(dataSet)

        binding.rvCategories.adapter = categoriesListAdapter

        categoriesListAdapter.setOnItemClickListener(object :
            CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })
    }

    fun openRecipesByCategoryId(categoryId: Int) {
        val categories: List<Category> = STUB.getCategories()
        val category = categories.find { it.id == categoryId }

        val categoryName = category?.title
        val categoryImageUrl = category?.imageUrl

        val bundle = bundleOf(
            ARG_CATEGORY_ID to categoryId,
            ARG_CATEGORY_NAME to categoryName,
            ARG_CATEGORY_IMAGE_URL to categoryImageUrl,
        )

        parentFragmentManager.commit {
            replace(R.id.mainContainer, RecipesListFragment::class.java, bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

}

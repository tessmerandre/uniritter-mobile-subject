package com.tessmerandre.listsndetails.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.tessmerandre.listsndetails.data.ListItem
import com.tessmerandre.listsndetails.databinding.ActivityListBinding
import com.tessmerandre.listsndetails.ui.detail.DetailActivity

class ListActivity : AppCompatActivity() {

    companion object {
        const val ARG_LIST_TYPE = "LIST_TYPE"
    }

    private val adapter = ListAdapter(::onItemClick)
    private val viewModel: ListViewModel by lazy {
        defaultViewModelProviderFactory.create(ListViewModel::class.java)
    }

    private val listType: ListType by lazy {
        intent.extras?.getSerializable(ARG_LIST_TYPE) as? ListType ?: ListType.TODOS
    }

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.getItems(listType)

        setupViews()
        setupBindings()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    private fun setupViews() {
        supportActionBar?.title = listType.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.rvItems.adapter = adapter
    }

    private fun setupBindings() {
        viewModel.uiState.observe(this) { uiState ->
            when (uiState) {
                is ListUiState.Loading -> showLoader()
                is ListUiState.Error -> showError()
                is ListUiState.Idle -> showItems(uiState.items)
            }
        }
    }

    private fun showLoader() {
        binding.progressIndicator.isVisible = true
        binding.rvItems.isVisible = false
    }

    private fun showItems(items: List<ListItem>) {
        adapter.setItems(items)
        binding.rvItems.isVisible = true
        binding.progressIndicator.isVisible = false
    }

    private fun showError() {
        // error view...
    }

    private fun onItemClick(item: ListItem) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.ARG_DETAIL_ARGUMENT, item.getDetailArgument())
        startActivity(intent)
    }

}
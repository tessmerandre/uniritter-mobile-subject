package com.tessmerandre.listsndetails.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import coil.load
import com.tessmerandre.listsndetails.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val ARG_DETAIL_ARGUMENT = "DETAIL_ARGUMENT"
    }

    private lateinit var binding: ActivityDetailBinding

    private val detailArgument: DetailArgument? by lazy {
        intent.extras?.getParcelable(ARG_DETAIL_ARGUMENT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onBackPressed() // should check of menu item id
        return super.onOptionsItemSelected(item)
    }

    private fun setupViews() {
        supportActionBar?.title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.tvTitle.text = detailArgument?.title.orEmpty()

        binding.tvOverline.isVisible = detailArgument?.overline != null
        binding.tvOverline.text = detailArgument?.overline.orEmpty()

        binding.tvDescription.isVisible = detailArgument?.description != null
        binding.tvDescription.text = detailArgument?.description.orEmpty()

        binding.ivPhoto.isVisible = detailArgument?.photoUrl != null
        binding.ivPhoto.load(detailArgument?.photoUrl)
    }

}
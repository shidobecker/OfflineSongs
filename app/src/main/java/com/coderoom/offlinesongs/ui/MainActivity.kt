package com.coderoom.offlinesongs.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.coderoom.offlinesongs.R
import com.coderoom.offlinesongs.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    lateinit var adapter: ArtistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupAdapter()
        setupAddButton()
        fetchArtists()
    }


    private fun fetchArtists() {
        viewModel.fetchAllArtists()

        viewModel.artistList.observe(this, Observer { artistList ->
            if (artistList.isEmpty()) {
                no_artist_text.visibility = View.VISIBLE
                artists_recycler.visibility = View.GONE
            } else {
                no_artist_text.visibility = View.GONE
                artists_recycler.visibility = View.VISIBLE
            }

            adapter.artistList = artistList
        })
    }

    private fun setupAdapter() {
        adapter = ArtistAdapter()
        artists_recycler.adapter = adapter
        artists_recycler.layoutManager = LinearLayoutManager(this)
    }

    private fun setupAddButton() {
        add_artist_button.setOnClickListener {
            val addFragment = ArtistAddDialog.newInstance()
            addFragment.show(supportFragmentManager, ArtistAddDialog.TAG)
        }
    }


}

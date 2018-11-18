package com.coderoom.offlinesongs.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModelProviders
import com.coderoom.offlinesongs.R
import com.coderoom.offlinesongs.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.add_artist_dialog.*

class ArtistAddDialog : AppCompatDialogFragment() {


    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(MainActivityViewModel::class.java)
    }

    companion object {
        val TAG: String = ArtistAddDialog::class.java.canonicalName

        fun newInstance(): ArtistAddDialog {
            return ArtistAddDialog()
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.add_artist_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        add_save.setOnClickListener {
            if(!artist_edit.text.isNullOrEmpty()){
                viewModel.saveNewArtist(artist_edit.text.toString())
                dismiss()
            }
        }
    }



}
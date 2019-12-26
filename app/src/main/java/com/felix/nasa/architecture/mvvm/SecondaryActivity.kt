package com.felix.nasa.architecture.mvvm

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.felix.nasa.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class SecondaryActivity : AppCompatActivity() {

    private val viewModel by this.currentScope.viewModel(this, SecondaryViewModel::class)
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getAPOD()
        mBtnRetray.setOnClickListener { viewModel.getAPOD() }

        subscribers()
    }

    private fun subscribers() {
        compositeDisposable.add(viewModel.data.subscribe { model ->
            mTitle.text = model.getTitle()
            mDescription.text = model.getDescription()
            if (model.hasImage()) {
                Glide
                    .with(this)
                    .load(model.getUrl())
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .into(mImage)
            } else {
                mImage.visibility = View.GONE
                mLinkVideo.visibility = View.VISIBLE
                mLinkVideo.setOnClickListener {
                    this.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(model.getUrl())))
                }
            }
        })

        compositeDisposable.add(viewModel.state.subscribe { state ->
            when (state) {
                SecondaryViewModel.StateRequest.INITIAL -> {
                    mLayoutError.visibility = View.GONE
                    mProgress.visibility = View.VISIBLE
                }
                SecondaryViewModel.StateRequest.SUCCESS -> {
                    mProgress.visibility = View.GONE
                    mProgress.visibility = View.GONE
                    mContent.visibility = View.VISIBLE
                }
                else -> {
                    mProgress.visibility = View.GONE
                    mLayoutError.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}
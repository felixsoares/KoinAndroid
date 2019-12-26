package com.felix.nasa.architecture.mvp.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.felix.nasa.R
import com.felix.nasa.architecture.model.ApodModel
import com.felix.nasa.architecture.mvp.MainContract
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.scope.currentScope
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), MainContract.View {

    private val mPresenter by lazy {
        this.currentScope.get<MainContract.Presenter> {
            parametersOf(this@MainActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPresenter.getAPOD()
        mBtnRetray.setOnClickListener { mPresenter.getAPOD() }
    }

    override fun showLoading() {
        mProgress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        mProgress.visibility = View.GONE
    }

    override fun showError() {
        mLayoutError.visibility = View.VISIBLE
    }

    override fun hideSubViews() {
        mLayoutError.visibility = View.GONE
        mContent.visibility = View.GONE
    }

    override fun showData(model: ApodModel) {
        mContent.visibility = View.VISIBLE
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
    }

    override fun onDestroy() {
        mPresenter.dispose()
        super.onDestroy()
    }
}

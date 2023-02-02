package kedaiapps.projeku.testandroidperqara.ui.home

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kedaiapps.projeku.testandroidperqara.R
import kedaiapps.projeku.testandroidperqara.common.UiState
import kedaiapps.projeku.testandroidperqara.databinding.ActivityHomeDetailBinding
import kedaiapps.projeku.testandroidperqara.db.table.FavoriteTable
import kedaiapps.projeku.testandroidperqara.ext.observe
import kedaiapps.projeku.testandroidperqara.ext.toast
import kedaiapps.projeku.testandroidperqara.modules.base.BaseActivity
import kedaiapps.projeku.testandroidperqara.services.entity.ResponseHomeDetail
import kedaiapps.projeku.testandroidperqara.viewmodel.MainViewModel
import kedaiapps.projeku.testandroidperqara.viewmodel.RepoViewModel

class HomeDetailActivity : BaseActivity() {
    lateinit var mBinding: ActivityHomeDetailBinding
    private val viewModel by viewModels<MainViewModel>()
    private val viewModelRepo by viewModels<RepoViewModel>()

    private var id = ""
    private var background_image = ""
    private var name = ""
    private var released = ""
    private var rating = ""
    private var status = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeDetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initToolbar()
        initView()
        handleState()
    }

    private fun initToolbar() {
        mBinding.tlbr.ivBack.setOnClickListener {
            onBackPressed()
        }

        mBinding.tlbr.tvTitle.setOnClickListener {
            onBackPressed()
        }

        mBinding.tlbr.ivFavorite.isVisible = true
    }

    private fun initView() {
        id = intent.getStringExtra("id").toString()

        viewModel.homeDetail(id)
        getFavorite(id)

        mBinding.tlbr.ivFavorite.setOnClickListener {
            if (status != "") {
                viewModelRepo.updateFavorite(id, if (status == "1") "0" else "1")

                if(status == "1"){
                    toast("Favorite berhasil dihapus")
                }else{
                    toast("Favorite berhasil ditambahkan")
                }
            }else{
                viewModelRepo.setFavorite(id, background_image, name, released, rating, "1")
                toast("Favorite berhasil ditambahkan")
            }
        }
    }

    private fun handleState() {

        observe(viewModel.responseHomeDetail) {
            if (it != null) {
                setData(it)
            }
        }

        // loading
        observe(viewModel.loadState) {
            when (it) {
                UiState.Loading -> mBinding.progress.progress.isVisible = true
                UiState.Success -> {
                    mBinding.progress.progress.isVisible = false
                }
                is UiState.Error -> {
                    mBinding.progress.progress.isVisible = false
                    toast(it.message)
                }
            }
        }
    }

    private fun getFavorite(id: String){
        observe(viewModelRepo.getFavoriteId(id)) {
            if (it != null) {
                status = it.status
                mBinding.tlbr.ivFavorite.setImageDrawable(getDrawable(
                    if (status == "1") R.drawable.ic_favorite2
                    else R.drawable.ic_favorite)
                )
            }
        }
    }

    private fun setData(data: ResponseHomeDetail) {
        background_image = data.background_image
        name = data.name
        released = data.released
        rating = data.rating

        mBinding.tlbr.tvTitle.text = name

        Glide.with(this).load(background_image)
            .apply(
                RequestOptions()
                    .transform(RoundedCorners(16))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .dontAnimate()
            ).into(mBinding.image)

        mBinding.slug.text = data.slug
        mBinding.judul.text = name
        mBinding.date.text = "Release date $released"
        mBinding.rating.text = rating

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mBinding.description.text =
                Html.fromHtml(data.description, Html.FROM_HTML_MODE_COMPACT);
        } else {
            mBinding.description.text = Html.fromHtml(data.description);
        }
    }
}
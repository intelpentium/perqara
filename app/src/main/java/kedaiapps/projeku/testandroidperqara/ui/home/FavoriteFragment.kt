package kedaiapps.projeku.testandroidperqara.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kedaiapps.projeku.testandroidperqara.databinding.FragmentFavoriteBinding
import kedaiapps.projeku.testandroidperqara.db.table.FavoriteTable
import kedaiapps.projeku.testandroidperqara.ext.observe
import kedaiapps.projeku.testandroidperqara.modules.base.BaseFragment
import kedaiapps.projeku.testandroidperqara.ui.home.adapter.AdapterFavorite
import kedaiapps.projeku.testandroidperqara.viewmodel.RepoViewModel

class FavoriteFragment : BaseFragment() {
    lateinit var mBinding: FragmentFavoriteBinding
    private val viewModel by viewModels<RepoViewModel>()

    private val adapter by lazy(LazyThreadSafetyMode.NONE){
        AdapterFavorite(::onClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        handleState()
    }

    private fun initView() {
        mBinding.rv.adapter = adapter
    }

    private fun handleState() {
        observe(viewModel.getFavorite()){
            if(it != null){
                adapter.clearData()
                adapter.insertData(it)
            }
        }
    }

    private fun onClick(data: FavoriteTable) {
        val intent = Intent(requireActivity(), HomeDetailActivity::class.java)
        intent.putExtra("id", data.fav_id)
        startActivity(intent)
    }
}
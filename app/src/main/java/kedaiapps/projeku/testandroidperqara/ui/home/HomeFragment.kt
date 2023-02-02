package kedaiapps.projeku.testandroidperqara.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kedaiapps.projeku.testandroidperqara.common.UiState
import kedaiapps.projeku.testandroidperqara.databinding.FragmentHomeBinding
import kedaiapps.projeku.testandroidperqara.ext.hideKeyboard
import kedaiapps.projeku.testandroidperqara.ext.observe
import kedaiapps.projeku.testandroidperqara.ext.toast
import kedaiapps.projeku.testandroidperqara.modules.base.BaseFragment
import kedaiapps.projeku.testandroidperqara.services.entity.ResponseHome
import kedaiapps.projeku.testandroidperqara.ui.home.adapter.AdapterHome
import kedaiapps.projeku.testandroidperqara.viewmodel.MainViewModel

class HomeFragment : BaseFragment() {
    lateinit var mBinding: FragmentHomeBinding
    private val viewModel by viewModels<MainViewModel>()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        AdapterHome(::onClick)
    }

    // paggination
    private var search = ""
    private var page = 1
    private var pageSize = 5
    private var totalPage = 10
    private var isLoading = true
    private val layoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        handleState()
    }

    private fun initView() {
//        viewModel.home()
        viewModel.paggination(page, pageSize, search)

        mBinding.search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                page = 1
                search = mBinding.search.text.toString()
//            viewModel.search(mBinding.search.text.toString())

                viewModel.paggination(page, pageSize, search)
                hideKeyboard()
                return@setOnEditorActionListener true
            }
            false
        }

        mBinding.ivSearch.setOnClickListener {
            page = 1
            search = mBinding.search.text.toString()
//            viewModel.search(mBinding.search.text.toString())

            viewModel.paggination(page, pageSize, search)
            hideKeyboard()
        }

        // paggination
        mBinding.rv.layoutManager = layoutManager
        mBinding.rv.adapter = adapter
        mBinding.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (isLoading && layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1 && page < totalPage) {
                    page++
                    isLoading = false
//                    mBinding.loader.visibility = View.VISIBLE
                    mBinding.itemProgress.isVisible = true

                    viewModel.paggination(page, pageSize, search)
                }
            }
        })
    }

    private fun handleState() {

//        observe(viewModel.responseHome) {
//            if (it != null) {
//                adapter.clearData()
//                adapter.insertData(it)
//            }
//        }

        //paggination
        observe(viewModel.responsePagination){
            if(it != null){
                if(page == 1) {
                    adapter.clearData()
                    adapter.insertData(it)
                }else{
                    adapter.insertData(it)
                    isLoading = true
                    mBinding.itemProgress.isVisible = false
                }
            }
        }

        // loading
        observe(viewModel.loadState) {
            when (it) {
                UiState.Loading -> mBinding.itemProgress.isVisible = true
                UiState.Success -> {
                    mBinding.itemProgress.isVisible = false
                }
                is UiState.Error -> {
                    mBinding.itemProgress.isVisible = false
                    requireActivity().toast(it.message)
                }
            }
        }
    }

    private fun onClick(data: ResponseHome) {
        val intent = Intent(requireActivity(), HomeDetailActivity::class.java)
        intent.putExtra("id", data.id)
        startActivity(intent)
    }
}
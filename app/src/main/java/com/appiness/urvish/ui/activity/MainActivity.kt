package com.appiness.urvish.ui.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.appiness.urvish.R
import com.appiness.urvish.databinding.ActivityMainBinding
import com.appiness.urvish.network.RepositoryFactory
import com.appiness.urvish.ui.BaseActivity
import com.appiness.urvish.ui.adapter.RecyclerDataAdapter
import com.appiness.urvish.viewmodel.DataViewModel
import com.appiness.urvish.viewmodel.ViewModelFactory

class MainActivity : BaseActivity<ActivityMainBinding>(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var dataViewModel: DataViewModel

    private lateinit var searchView: SearchView

    private lateinit var mDataAdapter: RecyclerDataAdapter

    private var isSwipeRefresh = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView(R.layout.activity_main)

        init()
    }

    private fun init() {

        initToolbar(
            mBinding.toolbar.toolbar,
            R.string.app_name,
            mBinding.toolbar.txtHeaderTitle,
            showBackButton = false
        )

        mBinding.swipeRefreshLayout.setOnRefreshListener(this)

        // initialize viewModel class object
        dataViewModel =
            ViewModelProviders.of(this, ViewModelFactory(RepositoryFactory.createDataRepository()))
                .get(DataViewModel::class.java)

        // API call to get data
        webCallGetData()
    }

    private fun webCallGetData() {

        if (!isNetworkAvailable(this)) {
            displayShortToast(getString(R.string.msg_no_internet))
            isSwipeRefresh = false
            mBinding.swipeRefreshLayout.isRefreshing = false
            return
        }

        if (isSwipeRefresh) {
            mBinding.swipeRefreshLayout.isRefreshing = true
        } else {
            // start loader
            showLoader(this)
        }

        // make an API call
        dataViewModel.getDataFromServer()

        dataViewModel.responseModel.observe(this, Observer { it ->

            if (isSwipeRefresh) {
                isSwipeRefresh = false
                mBinding.swipeRefreshLayout.isRefreshing = false
            } else {
                // dismiss loader
                dismissLoader()
            }

            displayShortToast("Data Received Successfully...")

            val sortedList = it.sortedBy {
                it.title
            }

            mDataAdapter = RecyclerDataAdapter(this@MainActivity, sortedList)
            mBinding.recyclerViewDataList.adapter = mDataAdapter

            showNoData(
                mBinding.recyclerViewDataList,
                mBinding.layoutNoData,
                it.isNullOrEmpty()
            )
        })
    }

    override fun onRefresh() {

        isSwipeRefresh = true

        // API call to get data
        webCallGetData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_header, menu)

        val myActionMenuItem = menu?.findItem(R.id.menu_search)

        searchView = myActionMenuItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {

                if (!searchView.isIconified) {
                    searchView.isIconified = true
                }

                myActionMenuItem.collapseActionView()

                return false
            }

            override fun onQueryTextChange(s: String): Boolean {

                if (::mDataAdapter.isInitialized) {
                    mDataAdapter.filter.filter(s)
                }

                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}

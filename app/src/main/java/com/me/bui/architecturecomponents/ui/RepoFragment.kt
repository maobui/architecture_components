package com.me.bui.architecturecomponents.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

import com.crashlytics.android.Crashlytics
import com.me.bui.architecturecomponents.api.RepoSearchResponse
import com.me.bui.architecturecomponents.api.RepoSearchResponseAndUser
import com.me.bui.architecturecomponents.data.model.Repo
import com.me.bui.architecturecomponents.data.model.RepoSearchResult
import com.me.bui.architecturecomponents.data.model.Resource
import com.me.bui.architecturecomponents.data.model.User
import com.me.bui.architecturecomponents.databinding.FragmentRepoBinding
import com.me.bui.architecturecomponents.di.Injectable
import com.me.bui.architecturecomponents.viewmodel.RepoViewModel

import org.reactivestreams.Publisher

import javax.inject.Inject

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import kotlinx.android.synthetic.main.fragment_repo.*
import retrofit2.Response

/**
 * Created by mao.bui on 9/1/2018.
 */
class RepoFragment : Fragment(), Injectable {


    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var binding: FragmentRepoBinding
    private lateinit  var viewModel: RepoViewModel

    private val mRepoPageAdapter = RepoPageAdapter()
    private val mRepoAdapter = RepoAdapter(ArrayList())

    private val mCompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRepoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edtQuery.setOnKeyListener(View.OnKeyListener { view, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                doSearch()
                return@OnKeyListener true
            }
            false
        })

        btnSearch.setOnClickListener {
            doSearch()
            // Test Crashlytics.
            //                Crashlytics.getInstance().crash();
        }

        recyclerView.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = mRepoPageAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(RepoViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        viewModel.repos.observe(this, Observer { resource -> mRepoPageAdapter.submitList(resource!!.data) })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mCompositeDisposable.dispose()
    }

    private fun doSearch() {
        val query = edtQuery.text.toString()
        viewModel.searchRepo(query)
        dismissKeyboard()
    }

    private fun dismissKeyboard() {
        val view = activity!!.currentFocus
        if (view != null) {
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun flatMapExample() {
        val query = "android"
        mCompositeDisposable.add(viewModel.searchRepoRX(query)
                .subscribeOn(Schedulers.io())
                .flatMap { response ->
                    val repos = response.body()!!.items
                    Observable.fromIterable(repos!!)
                }
                .flatMap { repo -> viewModel.getUser(repo.owner.login) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Response<User>>() {
                    override fun onNext(response: Response<User>) {
                        Log.d(TAG, "name " + response.body()!!.name)
                    }

                    override fun onError(e: Throwable) {}
                    override fun onComplete() {}
                }))
    }

    private fun zipExample() {
        Observable.zip(viewModel.searchRepoRX("google"), viewModel.getUser("google"),
                BiFunction<Response<RepoSearchResponse>, Response<User>, RepoSearchResponseAndUser> { response, response2 ->
                    val repoSearchResponse = response.body()
                    val user = response2.body()
                    RepoSearchResponseAndUser(repoSearchResponse!!, user!!)
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableObserver<RepoSearchResponseAndUser>() {
                    override fun onNext(repoSearchResponseAndUser: RepoSearchResponseAndUser) {
                        Log.d(TAG, "RepoSearchResponseAndUser name " + if (repoSearchResponseAndUser.user == null) "" else repoSearchResponseAndUser.user.name)
                    }

                    override fun onError(e: Throwable) {}
                    override fun onComplete() {}
                })
    }

    private fun rxDoSearch() {
        recyclerView.adapter = mRepoAdapter
        val query = edtQuery.text.toString()
        mCompositeDisposable.add(viewModel.rxSearch(query)
                .subscribeOn(Schedulers.io())
                .flatMap { result ->
                    Log.d(TAG, "apply " + result.totalCount)
                    viewModel.rxLoadById(result.repoIds!!)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSubscriber<List<Repo>>() {
                    override fun onNext(repos: List<Repo>) {
                        mRepoAdapter.swapItems(repos)
                        Log.d(TAG, "onNext " + repos.size)
                    }

                    override fun onError(t: Throwable) {
                        Log.d(TAG, "onError " + t.toString())
                    }

                    override fun onComplete() {
                        Log.d(TAG, "onComplete ")
                    }
                })
        )
        dismissKeyboard()
    }

    private fun syncQueryExample() {
        val result = viewModel.rxSearchSync("android")
                .subscribeOn(Schedulers.io())
                .blockingGet()
    }

    companion object {

        val TAG = RepoFragment::class.java.simpleName

        fun newInstance(): RepoFragment {
            return RepoFragment()
        }
    }

}

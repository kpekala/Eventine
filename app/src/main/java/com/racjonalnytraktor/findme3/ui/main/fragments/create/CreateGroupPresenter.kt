package com.racjonalnytraktor.findme3.ui.main.fragments.create

import android.util.Log
import com.racjonalnytraktor.findme3.data.repository.create.CreateRepositoryImpl
import com.racjonalnytraktor.findme3.ui.base.BasePresenter
import com.racjonalnytraktor.findme3.utils.SchedulerProvider

class CreateGroupPresenter<V: CreateGroupMvp.View>: BasePresenter<V>(),CreateGroupMvp.Presenter<V> {

    val repo = CreateRepositoryImpl()

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)

        compositeDisposable.add(repo.getFriends()
                .flatMapIterable { t -> t }
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .subscribe {item ->
                    view.updateList(item)
                    Log.d("friends",item.fullName)
                })

    }

    override fun onDetach() {
        super.onDetach()
        compositeDisposable.clear()
    }
}
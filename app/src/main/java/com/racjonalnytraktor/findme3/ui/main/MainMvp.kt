package com.racjonalnytraktor.findme3.ui.main

import com.racjonalnytraktor.findme3.ui.base.MvpPresenter
import com.racjonalnytraktor.findme3.ui.base.MvpView

interface MainMvp {
    interface View: MvpView{
        fun changeProfileIcon(url: String)
    }
    interface Presenter<V: View>: MvpPresenter<V>{

    }
}
package com.racjonalnytraktor.findme3.ui.map.fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.iconics.IconicsDrawable
import com.racjonalnytraktor.findme3.R
import com.racjonalnytraktor.findme3.ui.base.BaseFragment
import com.racjonalnytraktor.findme3.ui.map.MapActivity
import com.racjonalnytraktor.findme3.ui.map.MapMvp
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.fragment_options.*


class SettingsFragment: BaseFragment<MapMvp.View>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_options,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iconBack.setImageDrawable(IconicsDrawable(parentMvp.getCtx())
                .sizeDp(18)
                .color(Color.WHITE)
                .icon(GoogleMaterial.Icon.gmd_arrow_back))

        iconBack.setOnClickListener {
            parentMvp.getPresenter().onBackInFragmentClick("options")
        }
    }
}
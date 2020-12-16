package com.example.bleapplication.presentation.ui.details.adapter

class ServiceListAdapterPresenter {

    private var expandStatusList: MutableList<Boolean> = mutableListOf()

    fun getExpandStatus(position: Int): Boolean = expandStatusList[position]

    fun fillExpandStatusList(size: Int) {
        for (i in 0..size) expandStatusList.add(false)
    }

    fun clearExpandStatusList() {
        expandStatusList.clear()
    }

    fun serviceClicked(position: Int) {
        expandStatusList[position] = !expandStatusList[position]
    }
}
package com.example.compose.unit2.lemonade

import com.example.compose.R

class RestartState : State {
    override fun imageId() = R.drawable.lemon_restart

    override fun descriptionId() = R.string.lemonade_restart_description

    override fun textId() = R.string.lemonade_restart_text

    override fun next() = TreeState()
}

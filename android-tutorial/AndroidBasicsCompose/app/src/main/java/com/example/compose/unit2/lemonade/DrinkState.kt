package com.example.compose.unit2.lemonade

import com.example.compose.R

class DrinkState : State {
    override fun imageId() = R.drawable.lemon_drink

    override fun descriptionId() = R.string.lemonade_drink_description

    override fun textId() = R.string.lemonade_drink_text

    override fun next() = RestartState()
}

package com.example.compose.unit2.lemonade

import com.example.compose.R

class SqueezeState(private val remainingCount: Int) : State {
    override fun imageId() = R.drawable.lemon_squeeze

    override fun descriptionId() = R.string.lemonade_squeeze_description

    override fun textId() = R.string.lemonade_squeeze_text

    override fun next() =
        if (remainingCount <= 1) {
            DrinkState()
        } else {
            SqueezeState(remainingCount - 1)
        }
}

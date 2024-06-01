package com.example.compose.unit2.lemonade

import com.example.compose.R

class TreeState : State {
    override fun imageId() = R.drawable.lemon_tree

    override fun descriptionId() = R.string.lemonade_tree_description

    override fun textId() = R.string.lemonade_tree_text

    // 絞る回数は 2 回以上 4 回以下でランダムに決める
    override fun next() = SqueezeState((2..4).random())
}

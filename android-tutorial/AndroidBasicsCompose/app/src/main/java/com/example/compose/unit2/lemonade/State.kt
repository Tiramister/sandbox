package com.example.compose.unit2.lemonade

sealed interface State {
    /**
     * 表示される画像のリソース ID
     */
    fun imageId(): Int

    /**
     * 画像の description になる文字列のリソース ID
     */
    fun descriptionId(): Int

    /**
     * 画像の下に表示される文字列のリソース ID
     */
    fun textId(): Int

    /**
     * ボタンを押したときに遷移する State を返す
     */
    fun next(): State
}

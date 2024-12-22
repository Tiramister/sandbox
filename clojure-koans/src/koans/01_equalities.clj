(ns koans.01-equalities
  (:require [koan-engine.core :refer :all]))

(meditations
 "We shall contemplate truth by testing reality, via equality"
 (= true true)

 "To understand reality, we must compare our expectations against reality"
 (= 2 (+ 1 1))

 "You can test equality of many things"
 (= (+ 3 4) 7 (+ 2 5))

 "Some things may appear different, but be the same"
 (= true (= 2 2/1))

 "You cannot generally float to heavens of integers"
 (= false (= 2 2.0))

 "But a looser equality is also possible"
 ;; は?
 (= true (== 2.0 2))

 "Something is not equal to nothing"
 (= true (not (= 1 nil)))

 "Strings, and keywords, and symbols: oh my!"
 (= false (= "hello" :hello 'hello))

 "Make a keyword with your keyboard"
 (= :hello (keyword "hello"))

 "Symbolism is all around us"
 (= 'hello (symbol "hello"))

 "What could be equivalent to nothing?"
 (= nil nil)

 "When things cannot be equal, they must be different"
 ;; 何が言いたい?
 (not= :fill-in-the-blank "hoge"))

;; まとめ
;; 等価性は = 関数で比較する
;; 引数は 3 つ以上渡せる
;; 第 1 引数は緩い型にキャストできるが、第 2 引数はキャストできないっぽい
;; : で始まるものはキーワード
;; ' で始まるものはシンボル

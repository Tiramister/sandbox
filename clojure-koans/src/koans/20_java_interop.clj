(ns koans.20-java-interop
  (:require [koan-engine.core :refer :all]))

(meditations
 "You may have done more with Java than you know"
 (= java.lang.String (class "warfare")) ; hint: try typing (javadoc "warfare") in the REPL

 "The dot signifies easy and direct Java interoperation"
 (= "SELECT * FROM" (.toUpperCase "select * from"))

 "But instance method calls are very different from normal functions"
 (= ["SELECT" "FROM" "WHERE"] (map #(.toUpperCase %) ["select" "from" "where"]))

 "Constructing might be harder than breaking"
 (= 10 (let [latch (java.util.concurrent.CountDownLatch. 10)]
         (.getCount latch)))

 "Static methods are slashing prices!"
 (== 1024 (Math/pow 2 10)))

;; まとめ
;; Java の機能も普通に使える
;; (.<field> <instance>) でフィールドにアクセスできる
;; ただし .<field> は関数ではなくマクロなので、関数としては使えない
;; (<class>. <args) でコンストラクタを呼び出し、インスタンスを生成する
;; 静的メソッドは <class>/<method> で呼び出せる

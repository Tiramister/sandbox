(ns koans.22-group-by
  (:require [koan-engine.core :refer :all]))

(defn get-odds-and-evens [coll]
  (let [{odds true evens false} (group-by #(not= 0 (mod % 2)) coll)]
    [odds evens]))

(meditations
 "To categorize a collection by some function, use group-by"
 (= {3 ["foo" "bar"] 5 ["hello" "world"]} (group-by count ["hello" "world" "foo" "bar"]))

 "You can simulate filter + remove in one pass"
 (= (get-odds-and-evens [1 2 3 4 5])
    ((juxt filter remove) odd? [1 2 3 4 5])
    [[1 3 5] [2 4]])

 "You can also group by a primary key"
 (= {1 [{:id 1 :name "Bob"} {:id 1 :last-name "Smith"}]
     2 [{:id 2 :name "Jennifer"}]}
    (group-by :id [{:id 1 :name "Bob"}
                   {:id 2 :name "Jennifer"}
                   {:id 1 :last-name "Smith"}]))

 "But be careful when you group by a non-required key"
 (= {"Bob" [{:name "Bob" :id 1}]
     "Jennifer" [{:name "Jennifer" :id 2}]
     nil [{:last-name "Smith" :id 1}]}
    (group-by :name [{:id 1 :name "Bob"}
                     {:id 2 :name "Jennifer"}
                     {:id 1 :last-name "Smith"}]))

 "The true power of group-by comes with custom functions"
 (= {:naughty-list [{:name "Jimmy" :bad true} {:name "Joe" :bad true}]
     :nice-list [{:name "Jane" :bad false}]}
    (group-by #(if (:bad %) :naughty-list :nice-list)
              [{:name "Jimmy" :bad true}
               {:name "Jane" :bad false}
               {:name "Joe" :bad true}])))

;; まとめ
;; group-by は他の言語のものと大体同じ使用感
;; juxt で、1 つの値に複数の関数を適用できる (map の逆というか対)

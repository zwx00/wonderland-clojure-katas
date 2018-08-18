(ns magic-square.puzzle
  (:require [clojure.math.combinatorics :as combo])
  (:require [clojure.set :refer [map-invert union]])
  (:require [clojure.data :refer [diff]]))

(def values [1.0 1.5 2.0 2.5 3.0 3.5 4.0 4.5 5.0])

(defn is-valid-row [row] (= (apply + row) 9.0))

(defn get-valid-combos [rows] (remove (comp not is-valid-row) rows))

(def valid-combos
   (get-valid-combos
            (combo/combinations values 3)))

(def combo-freq
    (frequencies
                (apply concat valid-combos)))

(defn get-row-score [row] ;; in how many lines elements in this "row" appear
  (apply +
         (map
           #(get combo-freq %)
           row)))

(defn get-diagonal-rows [rows] (remove #(not (= (get-row-score %) 10)) rows))
(defn get-other-rows [rows] (remove #(not (= (get-row-score %) 8)) rows))

(defn find-third-element [a, b] 
  (first
  (get (diff #{a, b}
  (first (
  filter #(and (contains? % a) (contains? % b))
  (map set (get-other-rows valid-combos))))) 1)))

(get-other-rows valid-combos)
(find-third-element 4.5 1.5)
 ;; get central digit


(defn magic-square [values]
(let [my-diff (map #(into [] %) (apply diff (map set (get-diagonal-rows valid-combos))))]
  (let [upper-left-edge (get (nth my-diff 0) 1)
        lower-left-edge (get (nth my-diff 1) 0)
        upper-right-edge (get (nth my-diff 1) 1)
        lower-right-edge (get (nth my-diff 0) 0)]
    (let [center (get (nth my-diff 2) 0)]
     [
      [upper-left-edge (find-third-element upper-left-edge upper-right-edge) upper-right-edge]
      [(find-third-element upper-left-edge lower-left-edge) center (find-third-element upper-right-edge lower-right-edge)]
      [lower-left-edge (find-third-element lower-left-edge lower-right-edge) lower-right-edge]
     ]
     )
    ))
)



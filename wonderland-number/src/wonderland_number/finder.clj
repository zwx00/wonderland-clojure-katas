(ns wonderland-number.finder)

(defn get-multiples [num] (map #(* num %) [2, 3, 4, 5, 6]))

(defn check-num [num] (apply = (conj
                                 (map (comp set str)
                                       (get-multiples num))
                                 (set
                                   (str num)) )))
(defn wonderland-number []
  (loop [x 100000]
    (if (check-num x)
      x
      (recur (inc x))
    )
  )
)


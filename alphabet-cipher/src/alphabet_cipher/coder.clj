(ns alphabet-cipher.coder)

(use '[clojure.string :only [index-of, join]])
(use '[clojure.data :only [diff]])

(def alphabet (map char (range 97 123)))

(defn alphabet-index [l] (index-of (join alphabet) l))

(defn match-letters [a, b]
  (nth
   (cycle alphabet)
   (+
    (alphabet-index a)
    (alphabet-index b))))

(defn unmatch-letters [a, b]
  (nth
   (cycle alphabet)
   (+ 26
      (-
       (alphabet-index a)
       (alphabet-index b)))))

(defn extract-keyword [a, b]
  (nth
   (cycle alphabet)
   (+ 26
      (-
       (alphabet-index b)
       (alphabet-index a)))))

(defn encode [keyword message]
  (join
   (map match-letters
        message (cycle keyword))))

(defn decode [keyword message]
  (join
   (map unmatch-letters
        message (cycle keyword))))

(defn find-pattern
  [letters]
  (or (some #(when % %)
            (for [c (range (count letters)) :when (> c 1)]
              (let [partitions (partition-all c letters)]
                (if (apply = (butlast partitions))
                  (if (= (last (diff (first partitions) (last partitions))) (last partitions))
                    (first partitions))))))) letters)

(defn decipher [cipher message]
  (join (find-pattern
         (map extract-keyword
              message cipher))))

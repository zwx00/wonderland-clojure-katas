(ns alphabet-cipher.coder)

(use '[clojure.string :only [index-of, join]])

(def alphabet (map char (range 97 123)))


(defn alphabet-index [l] (index-of (join alphabet) l))

(defn match-letters [a, b]
  (nth
    (cycle alphabet)
      (+
        (alphabet-index a)
        (alphabet-index b)
      )
  )
)

(defn encode [keyword message]
  (join
    (map match-letters
       message (cycle keyword)

    )
  )
)


(defn decode [keyword message]
  "decodeme")

(defn decipher [cipher message]
  "decypherme")


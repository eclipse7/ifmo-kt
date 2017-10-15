(chapter "Churh Encoding")

(section "Numbers")

(println "zero and succ")
(example '(def zero (fn [f x] x)))
(example '(defn succ [n] (fn [f x] (f (n f x)))))
(example '(defn to-int [n] (n (partial + 1) 0)))

(example '(to-int zero))
(example '(to-int (succ zero)))
(example '(to-int (succ (succ zero))))
(example '(to-int (succ (succ (succ zero)))))

(println "values")
(example '(def one (succ zero)))
(example '(def two (succ one)))
(example '(def three (succ two)))
(example '(to-int zero))
(example '(to-int one))
(example '(to-int two))
(example '(to-int three))

(println "add")
(example '(defn add [n1 n2] (fn [f x] (n1 f (n2 f x)))))
(example '(to-int (add zero zero)))
(example '(to-int (add two three)))

(println "pred")
(example '(defn pred [n]
  (fn [f x]
    (last (n (fn [[a b]] [(f a) a]) [x x])))))
(example '(to-int (pred two)))
(example '(to-int (pred one)))
(example '(to-int (pred zero)))

(println "subtract")
(example '(defn subtract [n1 n2] (n2 pred n1)))
(example '(to-int (subtract three one)))
(example '(to-int (subtract three two)))
(example '(to-int (subtract one one)))
(example '(to-int (subtract one three)))

(section "Pairs")
(example '(defn pair [f s] (fn [p] (p f s))))
(example '(defn fst [p] (p (fn [f s] f))))
(example '(defn snd [p] (p (fn [f s] s))))

(example '(def pp (pair 10 20)))
(example '(fst pp))
(example '(snd pp))

(section "Booleans")
(println "values")
(example '(defn b-true [f s] f))
(example '(defn b-false [f s] s))
(example '(defn to-boolean [b] (b true false)))
(example '(to-boolean b-true))
(example '(to-boolean b-false))

(println "not")
(example '(defn b-not [b] (fn [f s] (b s f))))
(example '(to-boolean (b-not b-true)))
(example '(to-boolean (b-not b-false)))

(println "and")
(example '(defn b-and [b1 b2] (fn [f s] (b1 (b2 f s) s))))
(example '(to-boolean (b-and b-false b-false)))
(example '(to-boolean (b-and b-true b-false)))
(example '(to-boolean (b-and b-false b-true)))
(example '(to-boolean (b-and b-true b-true)))

(println "or")
(example '(defn b-or [b1 b2] (fn [f s] (b1 f (b2 f s)))))
(example '(to-boolean (b-or b-false b-false)))
(example '(to-boolean (b-or b-true b-false)))
(example '(to-boolean (b-or b-false b-true)))
(example '(to-boolean (b-or b-true b-true)))

(println "predicates")
(example '(defn is-zero? [n] (n (fn [x] b-false) b-true)))
(example '(defn less-or-equal? [n1 n2] (is-zero? (subtract n1 n2))))
(example '(defn equal? [n1 n2] (b-and (less-or-equal? n1 n2) (less-or-equal? n2 n1))))

(example '(to-boolean (is-zero? zero)))
(example '(to-boolean (is-zero? three)))
(example '(to-boolean (less-or-equal? one three)))
(example '(to-boolean (less-or-equal? one one)))
(example '(to-boolean (less-or-equal? three one)))
(example '(to-boolean (equal? one three)))
(example '(to-boolean (equal? one one)))
(example '(to-boolean (equal? three one)))

(section "Signed numbers")
(defn signed [n] (pair n zero))
(defn negate [n] (pair (snd n) (fst n)))
(defn signed-add [n1 n2] (pair (add (fst n1) (fst n2)) (add (snd n1) (snd n2))))
(defn signed-subtract [n1 n2] (signed-add n1 (negate n2)))
(defn signed-to-int [n] ((fst n) (partial + 1) ((snd n) (fn [x] (- x 1)) 0)))

(example '(signed-to-int (signed zero)))
(example '(signed-to-int (signed two)))
(example '(signed-to-int (negate (signed zero))))
(example '(signed-to-int (negate (signed two))))
(example '(signed-to-int (signed-add (signed one) (signed two))))
(example '(signed-to-int (signed-subtract (signed one) (signed two))))

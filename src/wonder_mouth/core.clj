(ns wonder-mouth.core
  (:gen-class)
  (:require [clojure.math.numeric-tower :refer [sqrt floor ]]
  )
)
; ---------------------
(def fibonacci_symbol "O")
(def prime_symbol "o")
(def prime_fibonacci_symbol "Oo")
(def chen_prime_fibonacci_symbol "OO")
(def regular_symbol "")
; ----------------------
(defn test_prime_with_prev_primes [number prev_primes]
; gives wrong answer for number=1 (gives true)
  (if (and (not= 2) (even? number)) false 
    (not-any? zero?
      (let [check_upper_limit (int (floor(sqrt number)))]
        (for [divisor prev_primes :while (<= divisor check_upper_limit)]
          (rem number divisor)
        )
      )
    )
  )
)

(defn test_prime_or_semi_prime [number prev_primes]
; gives wrong answer for number=4 (gives false); because prev_primes does not include '2'
  (if (test_prime_with_prev_primes number prev_primes) true 
    ; test for semi-prime
    (if (= 2

            (loop [
                  idx_prime 0
                  count_total_factor 0
                  ]     
              (
                let [
                  divisor (nth prev_primes idx_prime)
                  new_count_total_factor (+ count_total_factor 
                  (loop [num number
                    count_current_divisor 0]
                  (if (not= 0 (rem num divisor))
                    count_current_divisor
                    (recur (/ num divisor) (inc count_current_divisor))
                  ))
                )
                ]
                (if (and (<= new_count_total_factor 2) (< idx_prime (dec (count prev_primes)) ) ) 
                  (recur (inc idx_prime) new_count_total_factor)
                  new_count_total_factor
                ) 
              )
            
            )
        )
        true
        false
      )
    


  )
)

(defn number_to_symbol [current_num is_fibonacci is_prime prev_primes]
  (
    cond
    (and is_prime is_fibonacci) (
      let [is_chen_prime (test_prime_or_semi_prime (+ current_num 2) prev_primes)] 
      (if is_chen_prime chen_prime_fibonacci_symbol prime_fibonacci_symbol)
    )
    is_prime prime_symbol
    is_fibonacci fibonacci_symbol 
    :else regular_symbol
  )
)

(defn create_new_pointers [is_fibonacci is_prime current_num next_fib last_fib prev_primes]
  (
    let [updated_primes (if is_prime (conj prev_primes current_num) prev_primes)]
    (if is_fibonacci (list (+ next_fib last_fib) next_fib updated_primes) 
                    (list next_fib last_fib updated_primes))
  )
)

(defn range_gt2_to_symbol [current_num target_num next_fib last_fib prev_primes] ;for ranges (3, inf)
  (let [is_fibonacci (= current_num next_fib)
        is_prime (test_prime_with_prev_primes current_num prev_primes)
        ]
    (cons (number_to_symbol current_num is_fibonacci is_prime prev_primes)
      (if (= current_num target_num)
        nil
        (apply range_gt2_to_symbol (inc current_num) target_num 
              (create_new_pointers is_fibonacci is_prime current_num next_fib last_fib prev_primes)
        )
      )
    )
  )
)

(defn range_to_symbol [target_num] ;for all ranges (1, inf)
  (cond
    (= target_num 1) (list fibonacci_symbol)
    (= target_num 2) (list fibonacci_symbol chen_prime_fibonacci_symbol)
    :else (cons fibonacci_symbol (cons chen_prime_fibonacci_symbol 
      (
        range_gt2_to_symbol 3 target_num 3 2 [2]
      )
    ))
  )
)
; ---------------------
; (defn test_prime_or_semi_prime [number prev_primes]
;     ; test for semi-prime
;       (if (= 2

;             (loop [
;                   idx_prime 0
;                   count_total_factor 0
;                   ]     
;               (
;                 let [
;                   divisor (nth prev_primes idx_prime)
;                   new_count_total_factor (+ count_total_factor 
;                   (loop [num number
;                     count_current_divisor 0]
;                   (if (not= 0 (rem num divisor))
;                     count_current_divisor
;                     (recur (/ num divisor) (inc count_current_divisor))
;                   ))
;                 )
;                 ]
;                 (if (and (<= new_count_total_factor 2) (< idx_prime (dec (count prev_primes)) ) ) 
;                   (recur (inc idx_prime) new_count_total_factor)
;                   new_count_total_factor
;                 ) 
;               )
            
;             )
;         )
;         true
;         false
;       )
; )
;
(defn -main
  "reactions..."
  [& args]
  (println "Enter number:")
  (doseq [sym (range_to_symbol (Integer/parseInt (read-line)))]
   (print sym)
  )
  (println "")
;  (
;    println
;    (test_prime_or_semi_prime 9 [2 3 5 7])

;  )

)


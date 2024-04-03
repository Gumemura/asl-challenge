(ns cote.test
  (:require [asl-challenge.core :refer [overlapping-events]]
            [java-time.api :as jt]
            [midje.sweet :refer [=>
                                 contains
                                 fact
                                 facts
                                 just]]))

(def ^:private test-case-00
  "Generic test"
  [{:event-name "event 00"
    :start (jt/local-date 2024 04 2)
    :end (jt/local-date 2024 04 4)}
   {:event-name "event 03"
    :start (jt/local-date 2024 04 1)
    :end (jt/local-date 2024 04 6)}
   {:event-name "event 01"
    :start (jt/local-date 2024 04 7)
    :end (jt/local-date 2024 04 9)}
   {:event-name "event 02"
    :start (jt/local-date 2024 04 3)
    :end (jt/local-date 2024 04 6)}])

(def ^:private test-case-01
  "Generic test"
  [{:event-name "event 00"
    :start (jt/local-date 2024 04 2)
    :end (jt/local-date 2024 04 4)}
   {:event-name "event 01"
    :start (jt/local-date 2024 04 3)
    :end (jt/local-date 2024 04 5)}])

(def ^:private test-case-02
  "Many overlaps amid many events"
  [{:event-name "event 00"
    :start (jt/local-date 2024 04 1)
    :end (jt/local-date 2024 04 3)}
   {:event-name "event 01"
    :start (jt/local-date 2024 04 2)
    :end (jt/local-date 2024 04 4)}
   {:event-name "event 02"
    :start (jt/local-date 2024 04 3)
    :end (jt/local-date 2024 04 5)}
   {:event-name "event 03"
    :start (jt/local-date 2024 04 6)
    :end (jt/local-date 2024 04 8)}
   {:event-name "event 04"
    :start (jt/local-date 2024 04 7)
    :end (jt/local-date 2024 04 9)}
   {:event-name "event 05"
    :start (jt/local-date 2024 04 8)
    :end (jt/local-date 2024 04 10)}
   {:event-name "event 06"
    :start (jt/local-date 2024 04 9)
    :end (jt/local-date 2024 04 11)}
   {:event-name "event 07"
    :start (jt/local-date 2024 04 10)
    :end (jt/local-date 2024 04 12)}])

(def ^:private test-case-03
  "No overlap"
  [{:event-name "event 00"
    :start (jt/local-date 2024 04 1)
    :end (jt/local-date 2024 04 5)}
   {:event-name "event 01"
    :start (jt/local-date 2024 04 6)
    :end (jt/local-date 2024 04 10)}
   {:event-name "event 02"
    :start (jt/local-date 2024 04 11)
    :end (jt/local-date 2024 04 15)}
   {:event-name "event 03"
    :start (jt/local-date 2024 04 16)
    :end (jt/local-date 2024 04 20)}])

(def ^:private test-case-04
  "Only one overlap amid events"
  [{:event-name "event 00"
    :start (jt/local-date 2024 04 1)
    :end (jt/local-date 2024 04 3)}
   {:event-name "event 01"
    :start (jt/local-date 2024 04 5)
    :end (jt/local-date 2024 04 7)}
   {:event-name "event 02"
    :start (jt/local-date 2024 04 9)
    :end (jt/local-date 2024 04 12)}
   {:event-name "event 03"
    :start (jt/local-date 2024 04 10)
    :end (jt/local-date 2024 04 14)}
   {:event-name "event 04"
    :start (jt/local-date 2024 04 16)
    :end (jt/local-date 2024 04 18)}
   {:event-name "event 05"
    :start (jt/local-date 2024 04 19)
    :end (jt/local-date 2024 04 20)}
   {:event-name "event 06"
    :start (jt/local-date 2024 04 20)
    :end (jt/local-date 2024 04 22)}])

(def ^:private test-case-05
  "Two overlaps and the events are next to each other"
  [{:event-name "event 00"
    :start (jt/local-date 2024 04 1)
    :end (jt/local-date 2024 04 3)}
   {:event-name "event 01"
    :start (jt/local-date 2024 04 2)
    :end (jt/local-date 2024 04 4)}
   {:event-name "event 02"
    :start (jt/local-date 2024 04 6)
    :end (jt/local-date 2024 04 8)}
   {:event-name "event 03"
    :start (jt/local-date 2024 04 7)
    :end (jt/local-date 2024 04 10)}
   {:event-name "event 04"
    :start (jt/local-date 2024 04 11)
    :end (jt/local-date 2024 04 14)}
   {:event-name "event 05"
    :start (jt/local-date 2024 04 18)
    :end (jt/local-date 2024 04 20)}
   {:event-name "event 06"
    :start (jt/local-date 2024 04 25)
    :end (jt/local-date 2024 04 28)}])

(facts "Test overlapping-events function"
  (fact "Test overlapping for generic case 00"
    (overlapping-events test-case-00) =>
    (just (just [(contains {:event-name "event 00"})
                 (contains {:event-name "event 02"})] :in-any-order)
          (just [(contains {:event-name "event 00"})
                 (contains {:event-name "event 03"})] :in-any-order)
          (just [(contains {:event-name "event 02"})
                 (contains {:event-name "event 03"})] :in-any-order) :in-any-order))

  (fact "Test overlapping for generic case 01"
    (overlapping-events test-case-01) =>
    (just (just [(contains {:event-name "event 00"})
                 (contains {:event-name "event 01"})] :in-any-order)))

  (fact "Test overlapping for case 02: many overlaps"
    (overlapping-events test-case-02) =>
    (just  (just [(contains {:event-name "event 00"})
                  (contains {:event-name "event 01"})] :in-any-order)
           (just [(contains {:event-name "event 01"})
                  (contains {:event-name "event 02"})] :in-any-order)
           (just [(contains {:event-name "event 03"})
                  (contains {:event-name "event 04"})] :in-any-order)
           (just [(contains {:event-name "event 04"})
                  (contains {:event-name "event 05"})] :in-any-order)
           (just [(contains {:event-name "event 05"})
                  (contains {:event-name "event 06"})] :in-any-order)
           (just [(contains {:event-name "event 06"})
                  (contains {:event-name "event 07"})] :in-any-order)))

  (fact "Test overlapping for case 03: no overlaps"
    (overlapping-events test-case-03) =>
    '())

  (fact "Test overlapping for case 04: single overlap"
    (overlapping-events test-case-04) =>
    (just (just [(contains {:event-name "event 02"})
                 (contains {:event-name "event 03"})] :in-any-order)))

  (fact "Test overlapping for case 05: two next to each other overlaps"
    (overlapping-events test-case-05) =>
    (just (just [(contains {:event-name "event 00"})
                 (contains {:event-name "event 01"})] :in-any-order)
          (just [(contains {:event-name "event 02"})
                 (contains {:event-name "event 03"})] :in-any-order))))

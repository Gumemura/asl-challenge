(ns asl-challenge.core
  (:require [java-time.api :as jt]))

(defn overlapping-events
  [events]
  (filter (fn [[event1 event2]]
            (or (jt/before? (:start event1) (:start event2) (:end event1))
                (jt/before? (:start event2) (:start event1) (:end event2))))
          (for [i (range (count events))
                j (range (inc i) (count events))]
            [(nth events i) (nth events j)])))
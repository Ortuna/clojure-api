(ns west.db.users
  (:use [korma.core]
        [korma.db]))

(def pg (postgres {:db "west_dev"}))

(defdb westdb pg)

(defentity users
  (pk :id)
  (table :users)
  (database westdb))

(defn create []
  (insert users
    (values {:id (rand-int 50)})))


(ns blog.models.posts
  (:refer-clojure :exclude [get])
  (:require
     [clojure.java.jdbc :as j ]
  )
  )

(def mysql-db {:subprotocol "mysql"
               :subname "//127.0.0.1:3306/test"
               :user "root"
               :password "123456"
               :zeroDateTimeBehaviour "convertToNull"
               :character-encoding "utf8"}
  )

(def now
  (str (java.sql.Timestamp. (System/currentTimeMillis))))

(defn all []
  (j/query mysql-db
    ["select * from posts"] ))

(defn get [id]
  (first (j/query mysql-db
           ["select * from posts where id = ?" id]
           )))

(defn create [params]
  (j/insert! mysql-db :posts  (dissoc (merge params {:created_at now :updated_at now}) :__anti-forgery-token)))

(defn save [id params]
  (j/update! mysql-db :posts  (dissoc params :__anti-forgery-token) ["id=?" id]))

(defn delete [id]
  (j/delete! mysql-db :posts ["id=?" id]))
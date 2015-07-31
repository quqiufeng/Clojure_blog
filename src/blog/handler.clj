(ns blog.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.basic-authentication :refer  [wrap-basic-authentication]]
            [ring.util.response :as resp]
            [ring.middleware.defaults :refer :all]
            [blog.models.posts :as posts-model]
            [blog.controllers.posts :as posts-controller]
            [org.httpkit.server :as http-kit :refer [run-server]]
            [blog.controllers.admin.posts :as admin-posts-controller])
  (:gen-class)
  )

(defn authenticated? [name pass]
  (and (= name "quqiufeng")
    (= pass "8876332")))

(defroutes public-routes
  (GET "/" [] (posts-controller/index))
  (route/resources "/" ))

(defroutes protected-routes
  (GET "/admin" [] (admin-posts-controller/index))
  (GET "/admin" [id] (admin-posts-controller/show id))
  (GET "/admin/posts/new" [] (admin-posts-controller/new))
  (POST "/admin/posts/create" [& params]
    (do (posts-model/create params)
      (resp/redirect "/admin")))
  (GET "/admin/posts/:id" [id] (admin-posts-controller/show id))
  (GET "/admin/posts/:id/edit" [id] (admin-posts-controller/edit id))
  (POST "/admin/posts/:id/save" [& params]
    (do (posts-model/save (:id params) params)
      (resp/redirect "/admin")))
  (GET "/admin/posts/:id/delete" [id]
    (do (posts-model/delete id)
      (resp/redirect "/admin"))))

(defroutes app-routes
  public-routes
  (wrap-basic-authentication protected-routes authenticated?)
  (route/not-found "404 Not Found"))

;(def app
 ; (wrap-defaults  app-routes (assoc-in site-defaults [:security :anti-forgery] false)))
 ;(wrap-defaults  app-routes site-defaults)
 ;(run-server (wrap-defaults app-routes site-defaults) {:port 5000})
 ;)

(defn -main []
  (run-server (wrap-defaults app-routes site-defaults) {:port 5000})
  )




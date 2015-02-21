(ns west.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :as json]
            [liberator.core :refer [defresource]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [west.db.users :as users]))


(defresource list-users
  :available-media-types ["text/json"]
  :handle-ok (json/generate-string {:users (select users)}))

(defresource create-user
  :allowed-methods [:post]
  :available-media-types ["text/json"]
  :post! (fn [_] users/create)
  :handle-ok #(json/generate-string {:users []}))

(defroutes app-routes
  (GET  "/users" [] list-users)
  (POST "/users" [] create-user)
  (route/not-found "Not Found"))

(def defaults-without-csrf
  (assoc site-defaults :security { :anti-forgery false}))

(def app
  (wrap-defaults app-routes defaults-without-csrf))

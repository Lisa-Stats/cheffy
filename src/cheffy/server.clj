(ns cheffy.server
  (:require [environ.core :refer [env]]
            [integrant.core :as ig]
            [reitit.ring :as ring]
            [ring.adapter.jetty :as jetty]))

(defn app
  [_env] ;;config from cheffy/app becomes this
  (ring/ring-handler
   (ring/router
    [["/"
      {:get {:handler (fn [_req] {:status 200
                                  :body "Hello world"})}}]])))

(defmethod ig/init-key :cheffy/app
  [_ config]
  (println "\nStarted app")
  (app config))

(defmethod ig/init-key :db/postgres
  [_ config]
  (println "\nConfigured db")
  (:jdbc-url config))

(defmethod ig/prep-key :server/jetty
  [_ config]
  (merge config {:port (Integer/parseInt (env :port))}))

(defmethod ig/init-key :server/jetty
  [_ {:keys [handler port]}]
  (println (str "\n Server running on port " port))
  (jetty/run-jetty handler {:port port :join? false}))

(defmethod ig/halt-key! :server/jetty
  [_ jetty]
  (.stop jetty))

(defn -main
  [config-file]
  (let [config (-> config-file slurp ig/read-string)]
    (-> config ig/prep ig/init)))

(ns user
  (:require
   [cheffy.server]
   [integrant.core :as ig]
   [integrant.repl :as ig-repl]
   [integrant.repl.state :as state]
   [next.jdbc :as jdbc]
   [next.jdbc.sql :as sql]
   ))

(ig-repl/set-prep!
 (fn [] (-> "resources/config.edn"
            slurp
            ig/read-string)))

(def go ig-repl/go)
(def halt ig-repl/halt)
(def reset ig-repl/reset)
(def reset-all ig-repl/reset-all)

(def app (-> state/system :cheffy/app))
(def db (-> state/system :db/postgres))

(comment
  (app {:request-method :get   ;;helps test if all changes that we made to
        :uri "/swagger.json"}) ;;our app are working
  (jdbc/execute! db ["SELECT * FROM recipe WHERE public = true"])
  (sql/find-by-keys db :recipe {:public true})
  (time
   (with-open [conn (jdbc/get-connection db)]
     {:public (sql/find-by-keys conn :recipe {:public true})
      :drafts (sql/find-by-keys conn :recipe {:public false :uid "auth0|5ef440986e8fbb001355fd9c"})}))
  (go)
  (halt)
  (reset))

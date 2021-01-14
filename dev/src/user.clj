(ns user
  (:require
   [cheffy.server]
   [next.jdbc.sql :as sql]
   [integrant.core :as ig]
   [integrant.repl :as ig-repl]
   [integrant.repl.state :as state]))

(ig-repl/set-prep!
 (fn [] (-> "resources/config.edn"
            slurp
            ig/read-string)))

(def go ig-repl/go)
(def halt ig-repl/halt)
(def reset ig-repl/reset)
(def reset-all ig-repl/reset-all)

(comment
  (sql/update! (:db/postgres state/system) :recipe {:name "my-recipe"}
               {:recipe-id "a3dde84c-4a33-45aa-b0f3-4bf9ac997680"})

    ;;app is now
  (:cheffy/app state/system)

  ;;db is now
  (:db/postgres state/system)


  (-> ((:cheffy/app state/system)
       {:request-method :get
        :uri "/v1/recipes/1234-recipe"})
      :body
      (slurp))

  (-> ((:cheffy/app state/system)
       {:request-method :post
        :uri "/v1/recipes"
        :body-params {:name "my recipe"
                      :prep-time 49
                      :img "image-url"}})
      :body
      (slurp))

  (go)
  (halt)
  (reset))

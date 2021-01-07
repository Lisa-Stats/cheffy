(ns cheffy.recipe.db
  (:require [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]
            ))

(defn find-all-recipes
  [db uid]
  (with-open [conn (jdbc/get-connection db)]
    (let [public (sql/find-by-keys db :recipe {:public true})]
      (if uid
        (let [drafts (sql/find-by-keys conn :recipe {:public false :uid uid})]
          {:public public
           :drafts drafts})
        {:public public}))))

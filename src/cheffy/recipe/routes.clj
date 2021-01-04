(ns cheffy.recipe.routes
  (:require [cheffy.recipe.handlers :as recipe]))

(defn routes
  [_env]
  (let [db (:jdbc-url _env)]
    ["/recipes" {:swagger {:tags ["recipes"]}
                 :get {:handler (recipe/list-all-recipes db)
                       :summary "List all recipes"}}]))

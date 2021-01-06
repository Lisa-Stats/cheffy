(ns cheffy.recipe.routes
  (:require [cheffy.recipe.handlers :as recipe]))

(defn routes
  [config]
  (let [db (:jdbc-url config)]
    ["/recipes" {:swagger {:tags ["recipes"]}
                 :get {:handler (recipe/list-all-recipes db)
                       :summary "List all recipes"}}]))

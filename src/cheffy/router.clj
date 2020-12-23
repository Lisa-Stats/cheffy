(ns cheffy.router
  (:require [cheffy.recipe.routes :as recipe]
            [reitit.ring :as ring]))

(defn routes
  [env]
  (ring/ring-handler
    (ring/router
      [["/v1"
        (recipe/routes env)]])))

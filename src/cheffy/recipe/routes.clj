(ns cheffy.recipe.routes)

(defn routes
  [_env]
  ["/recipes" {:get {:handler (fn [_req] {:status 200
                                          :body {:hello "you"}})}}])

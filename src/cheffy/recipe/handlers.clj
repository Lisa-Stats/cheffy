(ns cheffy.recipe.handlers
  (:require [cheffy.recipe.db :as recipe-db]
            [cheffy.responses :as responses]
            [ring.util.response :as rr])
  (:import (java.util UUID)))

(defn list-all-recipes
  [db]
  (fn [_request]
    (let [uid "auth0|5ef440986e8fbb001355fd9c"
          recipes (recipe-db/find-all-recipes db uid)]
      (rr/response recipes))))

(defn create-recipe!
  [db]
  (fn [request]
    (let [recipe-id (str (UUID/randomUUID))
          uid "auth0|5ef440986e8fbb001355fd9c"
          recipe (-> request :parameters :body)]
      (recipe-db/insert-recipe! db (assoc recipe :recipe-id recipe-id :uid uid))
      (rr/created (str responses/base-url "/recipes/" recipe-id) {:recipe-id recipe-id}))))

(defn retrieve-recipe
  [db]
  (fn [_request]
    (let [recipe-id "a3dde84c-4a33-45aa-b0f3-4bf9ac997680"
          recipe (recipe-db/find-recipe-by-id db recipe-id)]
      (if recipe
        (rr/response recipe)
        (rr/not-found {:type "recipe-not-found"
                       :message "Recipe not found"
                       :data (str "recipe-id " recipe-id)})))))

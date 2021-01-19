(ns cheffy.test-system
  (:require [integrant.repl.state :as state]
            [muuntaja.core :as m]
            [ring.mock.request :as mock]))

(defn test-endpoint
  ([method uri]
   (test-endpoint method uri nil))
  ([method uri opts]
   (let [app (:cheffy/app state/system)
         request (app (-> (mock/request method uri)
                          (cond-> (:body opts)
                                  (mock/json-body (:body opts)))))]
     (update request :body (fn [data] (m/decode "application/json" data))))))

(comment
  (test-endpoint :get "/v1/recipes")
  (test-endpoint :post "/v1/recipes" {:img "string"
                                      :name "my name"
                                      :prep-time 30})
  )

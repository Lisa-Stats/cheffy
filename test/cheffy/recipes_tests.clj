(ns cheffy.recipes-tests
  (:require [cheffy.test-system :as ts]
            [clojure.test :refer [deftest testing is]]))

(deftest recipes-tests
  (testing "List recipes"
    (testing "with auth -- public and drafts"
      (let [{:keys [status body]} (ts/test-endpoint :get "/v1/recipes" {:auth true})]
        (is (= 200 status))
        (is (vector? (:public body)))
        (is (vector? (:drafts body))))))

  (testing "without auth -- public"
    (let [{:keys [status body]} (ts/test-endpoint :get "/v1/recipes" {:auth false})]
      (is (= 200 status))
      (is (vector? (:public body)))
      (is (nil? (:drafts body))))))

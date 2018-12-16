(ns cljs-drums.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(def drum-keys [{:id "boom" :letter "Q" :src "boom.wav"}
                {:id "clap" :letter "W" :src "clap.wav"}
                {:id "hihat" :letter "E" :src "hihat.wav"}
                {:id "kick" :letter "A" :src "kick.wav"}
                {:id "openhat" :letter "S" :src "openhat.wav"}
                {:id "ride" :letter "D" :src "ride.wav"}
                {:id "snare" :letter "Z" :src "snare.wav"}
                {:id "tink" :letter "X" :src "tink.wav"}
                {:id "tom" :letter "C" :src "tom.wav"}])

(defonce app-state (atom {:text "Drum Machine"}))

(defn drum-pad [{id :id
                 letter :letter
                 src :src} drum-key]
  [:div {:id id
         :key id}
   [:h3 letter]
   [:audio {:id id
            :src src}]])

(defn drum-machine []
  [:div#drum-machine
   [:h1 (:text @app-state)]
   (map drum-pad drum-keys)])

(reagent/render-component [drum-machine]
                          (. js/document (getElementById "app")))

(defn on-js-reload [])

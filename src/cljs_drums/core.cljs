(ns cljs-drums.core
  (:require [reagent.core :as reagent :refer [atom]]
            [clojure.string :as str]))

(enable-console-print!)

(def drum-keys [{:sound "boom" :letter "Q" :src "sounds/boom.wav"}
                {:sound "clap" :letter "W" :src "sounds/clap.wav"}
                {:sound "hihat" :letter "E" :src "sounds/hihat.wav"}
                {:sound "kick" :letter "A" :src "sounds/kick.wav"}
                {:sound "openhat" :letter "S" :src "sounds/openhat.wav"}
                {:sound "ride" :letter "D" :src "sounds/ride.wav"}
                {:sound "snare" :letter "Z" :src "sounds/snare.wav"}
                {:sound "tink" :letter "X" :src "sounds/tink.wav"}
                {:sound "tom" :letter "C" :src "sounds/tom.wav"}])

(defonce app-state (atom {:text "Click or Press key"}))

(defn play-audio [letter sound]
  (-> js/document
      (.querySelector (str "audio.clip#" letter))
      (.play))
  (swap! app-state assoc :text (str sound)))

(defn drum-pad [{sound :sound
                 letter :letter
                 src :src} drum-key]
  [:div.drum-pad {:id letter
                  :key letter
                  :on-click (fn [e] (play-audio letter sound))}
   [:h3 letter]
   [:audio.clip {:id letter
                 :src src}]])

(defn drum-machine []
  [:div#drum-machine
   [:h1 "Drum Machine"]
   [:h2#display (:text @app-state)]
   (map drum-pad drum-keys)])

(reagent/render-component [drum-machine]
                          (. js/document (getElementById "app")))

(defn on-js-reload [])

(defn in-drumset? [e] (some
                       #(= (.-key e) %)
                       (map
                        (fn [{l :letter} map] (str/lower-case l))
                        drum-keys)))

(defn play-audio-key [e]
  (play-audio
   (str/upper-case (.-key e))
   (get
    (reduce #(%)
            (filter (fn [{l :letter} map]
                      (= (.-key e)
                         (clojure.string/lower-case l))) drum-keys)) :sound)))

(defn handle-keypress [e]
  (if (in-drumset? e)
    (play-audio-key e)))

(-> js/document
    (.addEventListener "keydown" handle-keypress))
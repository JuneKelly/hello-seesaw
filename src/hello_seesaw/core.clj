(ns hello-seesaw.core
  (:use [seesaw.core]
        [markdown.core])
  (:import org.pushingpixels.substance.api.SubstanceLookAndFeel
           javax.swing.JEditorPane
           javax.swing.text.Document
           javax.swing.text.html.HTMLEditorKit
           javax.swing.text.html.StyleSheet
           )
  (:gen-class))

(def page (new JEditorPane))
(. page setEditable false)

(def kit (new HTMLEditorKit))
(. page setEditorKit kit)

(def stylesheet (. kit getStyleSheet))
(. stylesheet addRule
   "body { font-family: sans-serif; padding: 24px; }")
(. stylesheet addRule
   "img { display: block; margin-top: 12px; margin-bottom: 12px; }")
(. stylesheet addRule
   "pre { font-family: monospace; }")

(def doc (. kit createDefaultDocument))
(. page setDocument doc)

(def go-button (button :text "go"))
(def url-bar (text))

(defn update-page [url]
  (. page setText (md-to-html-string
                   (slurp url))))

(listen go-button :action (fn [e] (update-page (text url-bar))))

(update-page "https://raw.githubusercontent.com/ShaneKilkelly/hello-seesaw/master/README.md")

(native!)

(defn -main [& args]
  (invoke-later
   (->
    (frame
     :title "Wat"
     :on-close :exit
     :width 800
     :height 600
     :content (border-panel
               :north (horizontal-panel :items [go-button url-bar])
               :center page))
    ;; pack!
    show!)))

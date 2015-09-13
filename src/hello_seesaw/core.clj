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

(def html-text
  (md-to-html-string "
# Welcome

This is a cool thing.

![Doge](http://a3.mzstatic.com/us/r30/Purple6/v4/db/f5/c8/dbf5c8fe-593e-c35b-c3d1-02f70d703354/icon_128.png)

Don't you think?
  "))

(def editor (new JEditorPane))
(. editor setEditable false)

(def kit (new HTMLEditorKit))
(. editor setEditorKit kit)

(def stylesheet (. kit getStyleSheet))
(. stylesheet addRule
   "body { font-family: monospace; font-size: 1.2em; padding: 24px; }")
(. stylesheet addRule
   "img { display: block; margin-top: 12px; margin-bottom: 12px; }")

(def doc (. kit createDefaultDocument))
(. editor setDocument doc)
(. editor setText html-text)

(native!)


(defn -main [& args]
  (invoke-later
   (->
    (frame
     :title "Wat"
     :on-close :exit
     :width 800
     :height 600
     :content (vertical-panel
               :items [
                       :separator
                       editor
                       :separator
                       ]))
    ;; pack!
    show!)))

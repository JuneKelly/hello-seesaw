(ns hello-seesaw.core
  (:use [seesaw.core])
  (:import org.pushingpixels.substance.api.SubstanceLookAndFeel
           javax.swing.JEditorPane
           javax.swing.text.Document
           javax.swing.text.html.HTMLEditorKit
           javax.swing.text.html.StyleSheet
           )
  (:gen-class))

(def html-text "

  <html>

  <h1>Hello</h1>
  <p>
    <img src='http://i.imgur.com/wgmGKyQ.png' />
  </p>
  <p>
    <a href='#'>Wat</a>
  </p>

  </html>")

(def editor (new JEditorPane))
(. editor setEditable false)

(def kit (new HTMLEditorKit))
(. editor setEditorKit kit)

(def stylesheet (. kit getStyleSheet))
(. stylesheet addRule "body { font-family: monospace; font-size: 1.2em; padding: 24px; }")
(. stylesheet addRule "img { display: block; margin-top: 12px; margin-bottom: 12px; }")

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

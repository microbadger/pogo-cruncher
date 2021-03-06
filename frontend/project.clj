(defproject cruncher "0.3"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.6.1"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.198"]
                 [org.clojure/core.async "0.2.385" :exclusions [org.clojure/tools.reader]]
                 [org.omcljs/om "1.0.0-alpha40"]
                 [binaryage/devtools "0.8.1" :scope "dev"]
                 [cljs-ajax "0.5.8"]                        ; AJAX for om
                 [com.cognitect/transit-cljs "0.8.239"]     ; Better JSON support
                 [figwheel-sidecar "0.5.3-2" :scope "devcards"]
                 [devcards "0.2.1-7" :scope "devcards" :exclusions [org.clojure/clojurescript]]
                 [devcards-om-next "0.1.1" :scope "devcards"]]

  :plugins [[lein-figwheel "0.5.4-7"]
            [lein-cljsbuild "1.1.3" :exclusions [[org.clojure/clojure]]]
            [lein-ancient "0.6.10"]
            [lein-kibit "0.1.2"]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {:builds
              [{:id           "dev"
                :source-paths ["src/cruncher"]
                :figwheel     {:on-jsload "cruncher.core/on-js-reload"
                               :open-urls ["http://localhost:3449/index.html"]}
                :compiler     {:main                 cruncher.core
                               :preloads             [devtools.preload]
                               :asset-path           "js/compiled/out"
                               :output-to            "resources/public/js/compiled/cruncher.js"
                               :output-dir           "resources/public/js/compiled/out"
                               :source-map-timestamp true}}
               {:id           "devcards"
                :source-paths ["src/cruncher" "src/devcards"]
                :figwheel     {:devcards true}
                :compiler     {:main                 cruncher.devcards.core
                               :asset-path           "js/compiled/devcards/out"
                               :output-to            "resources/public/js/compiled/cruncher.js"
                               :output-dir           "resources/public/js/compiled/devcards/out"
                               :source-map-timestamp true}}
               ;; This next build is an compressed minified build for
               ;; production. You can build this with:
               ;; lein cljsbuild once min
               {:id           "min"
                :source-paths ["src"]
                :compiler     {:output-to     "resources/public/js/compiled/cruncher.js"
                               :main          cruncher.core
                               :optimizations :advanced
                               :pretty-print  false}}]}

  :figwheel {;; :http-server-root "public" ;; default and assumes "resources"
             ;; :server-port 3449 ;; default
             ;; :server-ip "127.0.0.1"

             :css-dirs ["resources/public/css"]             ;; watch and update CSS

             ;; Start an nREPL server into the running figwheel process
             ;; :nrepl-port 7888

             ;; Server Ring Handler (optional)
             ;; if you want to embed a ring handler into the figwheel http-kit
             ;; server, this is for simple ring servers, if this
             ;; doesn't work for you just run your own server :) (see lien-ring)

             ;; :ring-handler hello_world.server/handler

             ;; To be able to open files in your editor from the heads up display
             ;; you will need to put a script on your path.
             ;; that script will have to take a file path and a line number
             ;; ie. in  ~/bin/myfile-opener
             ;; #! /bin/sh
             ;; emacsclient -n +$2 $1
             ;;
             ;; :open-file-command "myfile-opener"

             ;; if you are using emacsclient you can just use
             ;; :open-file-command "emacsclient"

             ;; if you want to disable the REPL
             ;; :repl false

             ;; to configure a different figwheel logfile path
             ;; :server-logfile "tmp/logs/figwheel-logfile.log"
             }


  ;; setting up nREPL for Figwheel and ClojureScript dev
  ;; Please see:
  ;; https://github.com/bhauman/lein-figwheel/wiki/Using-the-Figwheel-REPL-within-NRepl


  :profiles {:dev {:dependencies [[figwheel-sidecar "0.5.4-7"]
                                  [com.cemerick/piggieback "0.2.1"]]
                   ;; need to add dev source path here to get user.clj loaded
                   :source-paths ["src" "dev"]
                   ;; for CIDER
                   ;; :plugins [[cider/cider-nrepl "0.12.0"]]
                   :repl-options {; for nREPL dev you really need to limit output
                                  :init             (set! *print-length* 50)
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}

  )

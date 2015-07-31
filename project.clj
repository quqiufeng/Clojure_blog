(defproject blog "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [
                  [org.clojure/clojure "1.7.0"]
                  [compojure "1.4.0"]
				          [http-kit "2.1.19"]
                  [de.ubercode.clostache/clostache "1.4.0"]
                  [org.clojure/java.jdbc "0.4.1"]
                  [mysql/mysql-connector-java "5.1.36"]
                  [ring/ring-defaults "0.1.5"]
                  [ring-basic-authentication "1.0.5"]
                  ]
  :plugins [[lein-ring "0.9.6"]]
  :ring {:handler blog.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})

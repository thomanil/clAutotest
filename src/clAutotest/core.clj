(ns clAutotest.core
  (:gen-class)
  (:use clojure.contrib.shell-out))

;; 1st param = command that launches a test. Example: 'rake test', 'lein test', etc.
;; 2nd param = filename extension of files to monitor (rb for ruby, clj for clojure, java for Java, etc)
;; 3rd param = strings which, if they show up in test output, denote a test failure
;; 4th param = absolute path of project which is tested, aka "where to run the test command"

(def test-run-command (nth *command-line-args* 0))
(def watched-filetype (nth *command-line-args* 1))
(def error-indicators (.split (nth *command-line-args* 2) "%"))
(def test-run-location (nth *command-line-args* 3))

(println "------------------------------------------------------")
(println "STARTING AUTOTEST")
(println "------------------------------------------------------")
(println "Will launch tests using the following command: " test-run-command)
(println "Will launch the tests from this directory: " test-run-location)
(println "Watching for changes in all *." watched-filetype "files.")
(println "If any of these strings show up in test output we'll consider it a failure: '"
	 (apply str (interpose "','"  error-indicators))
	 "'")

(defn cmd-line [command] (sh "bash" :in command))

(defn visually-indicate-test-running []
  (cmd-line "osascript -e 'tell application \"Terminal\" to set background color of first window to \"yellow\"' ")
  (cmd-line "osascript -e 'tell application \"Terminal\" to set normal text color of first window to \"black\"' ")
  (println "\n\n\n------------------------------------------------------")
  (println (str "RUNNING TESTS  " (cmd-line "date")))
  (println "------------------------------------------------------\n\n\n"))
(defn visually-indicate-failure [output]
  (cmd-line "osascript -e 'tell application \"Terminal\" to set background color of first window to \"red\"' ")
  (cmd-line "osascript -e 'tell application \"Terminal\" to set normal text color of first window to \"black\"' ")
  (println output)
  (println "FAIL!"))
(defn visually-indicate-success [output]
  (cmd-line "osascript -e 'tell application \"Terminal\" to set background color of first window to \"green\"' ")
  (cmd-line "osascript -e 'tell application \"Terminal\" to set normal text color of first window to \"black\"' ")
  (cmd-line "osascript -e 'tell application \"Terminal\" to set cursor color of first window to \"white\"' ")
  (println output)
  (println "ALL TESTS SUCCEED") )

(defn indicate-test-state [test-result]
  (let [failures (test-result 0)
	output (test-result 1)]
    (if failures
      (visually-indicate-failure output)
      (visually-indicate-success output))))

(defn tests-failed? [result]
  (let [failures (map #(.contains result %) error-indicators)]
    (some true? failures)))

(defn run-tests []
  (cmd-line (str "cd " test-run-location ";" test-run-command))) 

(defn determine-test-status []
  (let [console-output (run-tests)
	state (tests-failed? console-output)]
    [state, console-output]))

(defn run-test []
  (let [status (determine-test-status)]
    (visually-indicate-test-running)
    (indicate-test-state status)) )

(def watched-files (str "find " test-run-location " -name '*." watched-filetype "'"))
(defn all-files [] (seq (.split (cmd-line watched-files) "\n")))
(defn file-state [file-path] (cmd-line (str "ls -l -T " file-path)))
(defn state-of-all-files [] (map #(file-state %) (all-files)))
(def monitored-files (ref ())) ;Will hold a map with keys = file path, value = time of last file change

(defn files-changed? []
  (let [current-state (apply str (state-of-all-files))
	last-state (apply str @monitored-files)]
    (when-not (= current-state last-state)
      (dosync
       (ref-set monitored-files (state-of-all-files)))
      :true)))

(defn test-loop []
  (loop []
    (if (files-changed?) (run-test))
    (Thread/sleep 1000);milliseconds
    (recur)))

(test-loop) ;Start autotesting!






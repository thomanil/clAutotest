(ns clAutotest.core
  (:gen-class)
  (:use clojure.contrib.shell-out))

;; 1st param = command that launches a test. Example: 'rake test', 'lein test', etc.
;; 2nd param = filename extension of files to monitor (rb for ruby, clj for clojure, java for Java, etc)
;; 3rd-nth param = strings which, if they show up in test output, denote a test failure

(println "")
(println "Will launch tests using the following command: '" (nth *command-line-args* 0) "'")
(println "Watching *." (nth *command-line-args* 1) "files.")
(println "If any of these strings show up in test output we'll consider it a failure: '"
	 (apply str (interpose "','"(rest *command-line-args*)))
	 "'")

(defn cmd-line [command] (sh "bash" :in command))

(defn visually-indicate-test-running []
  (cmd-line "osascript -e 'tell application \"Terminal\" to set background color of first window to \"yellow\"' ")
  (cmd-line "osascript -e 'tell application \"Terminal\" to set normal text color of first window to \"black\"' "))
(defn visually-indicate-failure []
  (cmd-line "osascript -e 'tell application \"Terminal\" to set background color of first window to \"red\"' ")
  (cmd-line "osascript -e 'tell application \"Terminal\" to set normal text color of first window to \"black\"' "))
(defn visually-indicate-success []
  (cmd-line "osascript -e 'tell application \"Terminal\" to set background color of first window to \"green\"' ")
  (cmd-line "osascript -e 'tell application \"Terminal\" to set normal text color of first window to \"black\"' ")
  (cmd-line "osascript -e 'tell application \"Terminal\" to set cursor color of first window to \"white\"' "))

(def watched-files (str "find . -name '*.'" (first *command-line-args*)))
(defn all-files [] (seq (.split (cmd-line watched-files) "\n")))
(defn file-state [file-path] (cmd-line (str "ls -l -T " file-path)))
(defn state-of-all-files [] (map #(file-state %) (all-files)))
(defn run-tests [] (cmd-line (nth *command-line-args* 0))) 

(defn set-console-state [test-result]
  (let [test-status (test-result 0)
	test-output (test-result 1)]
    (when (= test-status :success)
      (println test-output)
      (println "ALL TESTS SUCCEED") 
      (visually-indicate-success))
    (when (= test-status :failure)
      (println test-output)
      (println "FAIL!")
      (visually-indicate-failure))))

(defn exception-or-failure-in-text [result]
  (let [failure-strings (rest *command-line-args*)
	failures (map #(.contains result %) failure-strings)]
    (if (some true? failures)
      :failure
      :success)))

(defn determine-test-status []
  (println "\n\n\n------------------------------------------------------")
  (println (str "TESTRUN STARTED  " (cmd-line "date")))
  (println "------------------------------------------------------\n\n\n")
  (let [result (run-tests)
	state (exception-or-failure-in-text result)]
    [state, result]))

(defn run-test []
  (visually-indicate-test-running)
  (set-console-state (determine-test-status)))

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






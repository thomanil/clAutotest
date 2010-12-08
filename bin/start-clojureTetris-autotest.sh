#!/bin/bash

# Autorun tests in my clojureTetris project

java -cp lib/clojure-1.1.0.jar:lib/clojure-contrib-1.1.0.jar:.:classes clojure.main src/clAutotest/core.clj 'lein test' 'clj' 'Exception in%ERROR in%FAIL in' '/Users/thomas/versioncontrolled/github/clojureTetris'
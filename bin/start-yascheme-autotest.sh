#!/bin/bash

# Autorun tests in my YAScheme project

java -cp lib/clojure-1.1.0.jar:lib/clojure-contrib-1.1.0.jar:.:classes clojure.main src/clAutotest/core.clj 'rake test' 'rb' 'Error%Exception%Failure' '/Users/thomas/versioncontrolled/github/yascheme'
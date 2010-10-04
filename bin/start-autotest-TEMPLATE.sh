#!/bin/bash

# TEMPLATE FILE - COPY AND CUSTOMIZE
# (This is just an example. Make a copy of this file, with the arguments below tweaked to fit your own project.)

# The 1st argument is the command that launches your tests in your project ('rake test' in this example)
# The 2nd argument is the file type/extension of the files which autotest will monitor in your project (clj here => all *.clj files)
# The 3rd argument is a %-separated list of strings which, if present in your test output, indicates failure (Error, Failure and Exception here)
# The 4th argument is the root directory of the project you want to test, the dir where command in arg1 is launched from.

java -cp lib/clojure-1.1.0.jar:lib/clojure-contrib-1.1.0.jar:.:classes clojure.main src/clAutotest/core.clj 'rake test' 'clj' 'Error%Exception%Failure' '/Users/thomas/versioncontrolled/github/yascheme'
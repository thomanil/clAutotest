# clAutotest

This is a generic autotesting tool. It can be told how to run tests, what file types to watch for changes, and how it'll recognize a failing test-run.

Once launched, clAutotest will re-run the tests in your project every time a file with the given extension changes. If tests fail, the terminal is colored red. Otherwise, expect a lovely bright green terminal window.


## Requisites

The tool currently only works on the OS X Terminal, since it uses osascripts to color the terminal when indicating test status.

Install Leiningen (http://github.com/technomancy/leiningen) if you
don't have it already.

## Installation

Install project dependencies in root dir by running first 'lein deps', then
'lein native-deps'.

## Usage

TODO add convencience shellscript

Raw way to start the autotest:

java -cp lib/clojure-1.1.0.jar:lib/clojure-contrib-1.1.0.jar:.:classes clojure.main src/clAutotest/core.clj [test start command] [filetype to monitor] [Error indication 1] [Error indication 2] ... [Error indication N]

Example:

java -cp lib/clojure-1.1.0.jar:lib/clojure-contrib-1.1.0.jar:.:classes clojure.main src/clAutotest/core.clj 'rake test' clj 'Error in:' 'Exception thrown'


## Installation

FIXME: write

## License

FIXME: write






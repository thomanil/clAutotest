# clAutotest

This is a generic autotesting tool. It can be told how to run tests in any project with a command line test launcher, what file types to watch for changes, and how it'll recognize a failed test in the terminal.

Once launched, clAutotest will re-run the tests in your project every time a file with the given extension changes. If tests fail, the terminal is colored red. Otherwise, expect a lovely bright green terminal window.

EXAMPLE: Succeding tests
<img src="http://github.com/downloads/thomanil/clAutotest/test-success.png" alt="Succeeding tests screenshot" />

EXAMPLE: Failing tests
<img src="http://github.com/downloads/thomanil/clAutotest/test-failure.png" alt="Failing tests screenshot" />

## Requisites

The tool currently only works on the OS X Terminal, since it uses osascripts to color the terminal when indicating test status.

Install [Leiningen](http://github.com/technomancy/leiningen) if you
don't have it already.

## Installation

Install project dependencies in root dir by running first 'lein deps'.

## Usage

Make a copy of the start-autotest-TEMPLATE.sh script in /bin.

Tweak it to fit your project.

Launch the script from the root of clAutotest. Example: 'sh bin/start-autotest-MyProject.sh'

## License

(The MIT License)

Copyright (c) 2010 clAutotest

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
'Software'), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.





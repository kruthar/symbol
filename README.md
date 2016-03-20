# symbol
A programming language that contains no symbol characters

Silly idea, I know, but just imagine a world where you could program and not have to reach for those pesky curly braces, where you never again have to hold Shift to add two numbers together.
Introducing symbol, a programming language made completely of non-symbol characters. I also have no experience in developing a code language, won't this be fun?! We'll cross that bridge when we come to it.

At this point I have a bit of code written, full list of features below. Testing things is primitive, there is a main method in Symbol.java that can run a single line of code or run a .symb file (that's the current extension, like it? Got a better idea, let me know).
There are currently no tests, want to help me write some?

If you somehow stumbled onto this repo and have any interest in what I am trying to accomplish feel free to use the issue tracker to add ideas and thoughts.

## What we can currently do:
### Instructions
Instructions are what I am calling the...instructions of the language here are the ones that work:
* `print` - This is a simple print function, prints everything on the line, this is the main instruction used for testing things at the moment

    ```
    > print one two three
    123
    ```
* `set` - You can set variables with this
    
    ```
    > set var one two three
    > print var
    123
    ```
* `comment` - single line comment

    ```
    > print one
    > comment this is a comment
    > print two
    1
    2
    ```
* 'blockcomment' - open and close a multiline comment

    ```
    > print one
    > blockcomment this is
    > a multiline comment
    > print two
    > blockcomment
    > print three
    1
    3
    ```

### Literal Types
Values are considered Literals, here are the types of Literals available so far:
* SymbolString - Typical string

    ```
    > print stringstart Hello World! stringend
    Hello World!
    ```
* SymbolNumber - Currently these are strictly ints, but I plan for these to be unencombered numbers similar to numbers in Python. SymbolNumbers are not delimited you just type out the digits.

    ```
    > print one two three
    123
    ```
    Some day it would be really interesting to also be able to parse vocalized numbers such as one million, etc.

* SymbolBoolean - Typical booleans

    ```
    > print true
    true
    > print not true
    false
    ```

### Comparisons
Comparative expressions are implmented, but unfortunately not anywhere you can use them except to print.
* equalto - tests equality

    ```
    > print true equalto false
    false
    ```
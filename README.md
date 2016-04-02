# symbol
A programming language that contains no symbol characters

Silly idea, I know, but just imagine a world where you could program and not have to reach for those pesky curly braces, where you never again have to hold Shift to add two numbers together.
Introducing symbol, a programming language made completely of non-symbol characters. I also have no experience in developing a code language, won't this be fun?! We'll cross that bridge when we come to it.

## Getting Started
At this point there is no launcher, but running symbol programs is as issue as pushing the gree play button in your favorite IDE.

1. Get the code:

```
git clone https://github.com/kruthar/symbol.git
```

2. Import into your favorite IDE.
3. Read the current feature spec below and write your first symbol script in the `src/main/resources` folder.
4. Execute the file from the `main` method in `srs/main/java/Symbol.java`

```
Symbol.executeFile("test.symb");
```

## What we can currently do:
### Instructions
Instructions are what I am calling the...instructions of the language here are the ones that work:
* `print` - This is a simple print function, prints everything on the line, this is the main instruction used for testing things at the moment

    ```
    > print one two three
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
* `blockcomment` - open and close a multiline comment

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

### Variables
You can set variables using the `is` keyword

    ```
    > var is one two three
    > print var
    123
    ```

### Literal Types
Values are considered Literals, here are the types of Literals available so far:
* SymbolString - Typical string

    ```
    > print string Hello World! string
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
* equals - tests equality

    ```
    > print true equals false
    false
    ```
* notequals - tests inequality

    ```
    > print true notequals false
    true
    ```
* greaterthan

    ```
    > print one greaterthan two
    false
    ```
* greaterthanequals

    ```
    > print one greaterthanequals one
    true
    ```
* lessthan

    ```
    > print one lessthan two
    true
    ```
* lessthanequals

    ```
    > print one lessthanequals two
    true
    ```

### Basic Arithmetic
Basic mathematical operations

```
> print one plus one
2
> print two minus one
1
> print two times two
4
> print four dividedby two
2
> print five modulo three
2
```

Order of operations and parenthesis

```
> print one plus one times two
3
> print open one plus one close times two
4
```

### Control Flow
* if/else blocks are a mix of Java and Shell syntax

```
> if false
>   print one
> else if true
>   print two
> else
>   print three
> fi
2
```

* while loops

```
> counter is zero
> while counter lessthan three
>   println counter
>   counter is counter plus one
> end
0
1
2
```
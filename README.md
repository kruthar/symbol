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

## Running the bin tool (Under Construction)
There is now a bin tool which will be the primary way to launch symbol programs

1. Build the code, a jar will be produced in the `target` dir.

```
mvn package
```

2. Then you can run the build tool with the path to your `.symb` file

```
bin/symbol /path/to/your/test.symb
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
* `execute` - executes an expression, this is really only useful with functions. Function calls are expressions, and so to fire a function you need the execute command

    ```
    > function printer
    >   println string hello string
    > end
    > execute printer
    hello
    ```

### Variables
You can set variables using the `variable` instruction and `is` keyword

    ```
    > variable var is one two three
    > print var
    123
    ```

Assigning variables with variable values works how you would expect

    ```
    > variable test is one
    > variable test is test plus two
    > println test
    3
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

### Data Structures
* SymbolList - simple list, don't have much functionality yes except for defining and printing, and adding together

    ```
    > variable list1 is list one sep two sep list
    > variable list2 is list three sep four sep list
    > println list1
    > println list1 plus list2
    [1, 2]
    [1, 2, 3, 4]
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
> variable counter is zero
> while counter lessthan three
>   println counter
>   variable counter is counter plus one
> done
0
1
2
```

* for loops

```
> for variable x is zero while x lessthan three with variable x is x plus one do
>    println x
> end
0
1
2
```

For loops have the same three parts you would expect in plain java for loops.
`for`, `while`, `with`, `do`, and `end` ar the keywords that surround the three main expressions of a for loop.

`variable x is zero` - This is the initial variable declaration

`x lessthan three` - This is the condition that gets run on each iteration

`variable x is x plus one` - This is the increment instruction that progresses the loop

* foreach loops

```
> foreach item in list one sep string two string sep false sep list do
>    println item
> done
1
two
false
```

Foreach loops similar to those in python. They have a variable name and a Structure to iterate over.
Foreach loops use the keywords `foreach`, `in`, `do`, and `done`.

`item` - The name of the variable that will hold the current iterator value

`list one sep string two string sep false sep list` - A structure to iterate over, this could also be a variable pointint to a Structure

### Functions
Functions are here! There are two main types:
* non-returning functions - functions can end without returning, maybe they print instead

    ```
    > function printer
    >   println string helloworld string
    > end
    > execute printer
    helloworld
    ```
* returning functions - functions that return a value

    ```
    > function add accepts left right
    >   return left plus right
    > end
    > println add one sep two
    3
    ```

Functions can be defined with any number parameters, after the method name use the keyword `accepts` then a list of parameter names.

When calling a function that accepts parameters you must end each parameter with the `sep` keyword for "seperate". This keyword let's you separate expressions when you are listing expressions.
Here is an advanced example of the `sep` keyword:

```
> function add accepts left right
>   return left plus right
> end
> println add add one sep one sep sep two
```

The above function call to `add` has another call to `add` as one of it's parameters. This is perfectly acceptible. Function parameters are expressions, and function calls reduce to expressions, so function calls can be used as parameters to other functions.
The main thing to note here is the double `sep`, that's wierd, but necessary when you think about it. The first `sep` is to end the second parameter expression to the inner call to `add`. The second `sep` is to seperate the entire expression of the inner add call as the first parameter expression to the outer call to `add`. Clear as mud?

### Dot Methods
SymbolObjects will have various 'dot' methods that perform actions on that object. Because methods can have multiple parameter signatures, it is necessary to know exactly when parameter lists stop, so you end dot methods with `tod`.

```
> variable mylist is list one sep two sep three sep list
> println mylist dot size tod
3
> execute mylist dot remove zero tod
> println mylist
[2, 3]
```

A few things to notice:
* the `dot` keyword after a SymbolObject (or an expression that reduces to a SymbolObject) invokes the following method name with the following list of parameters
* similar to how functions work, you have to use the `execute` instruction if you are invoking a dot method that has no return value
* the `dot` invocation has to be ended by `dot` even if it does not have any parameters.

## Testing Against Project Euler
Spoiler Alert! One of the test files is testing against Project Euler problems, don't peak if you haven't solved them yet!

If you have, or just don't care, check out `src/test/resources/euler` for sample symbol scripts.
# Variables

You need a way to store values of stuff! So let's talk about how to set, update and use variables.

## Setting variables
Using a ```set``` command sounds like a good idea, here is a potential set definition:

```
set [TYPE] [NAME] [VALUE]
```

TYPE - variable data type
NAME - variable name
VALUE - (optional) variable value, otherwise initialized with no value, value should probably be any valid expression, so that you can set variables to other variables, or operations on other variables etc.

### Examples

```
symbol> set string sample This is a string
symbol> print sample
This is a string

symbol> set number num one two three
symbol> print num
123
```

### Considerations

* When thinking about variable replacement inside of strings should there be a keyword necessary to specify a variable? or should variable names always be substituted in strings? If they are always substituted a lot of work may need to be done by the user to make sure that strings are properly escaped. So, it might be a good idea to require a variable identifier within strings to do variable replacement. Here is a demonstration:

  ```
  symbol> set number num one two three
  symbol> set string sample sub num is a num
  symbol> print string
  123 is a num
  ```
  
## Updating variables

For now I think that reusing the syntax to set a variable from above is sufficient to update a variables value

## Using variables

This is likely dependent on the context of using them. Some ways of using variables:

* String substitution as explained above
* As part of an expression, i.e. ```a plus b```


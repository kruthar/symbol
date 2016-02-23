# Datatypes

A planning page to talk about datatypes

## Numbers

I say 'numbers' here because I like the idea of going the Python route of not being restricted by size when dealing with ints. Maybe we can still call them ints, but they will not have a max size like in say Java. For now let's just refer to them as 'numbers'.

The actual number keys I am considering symbols, only because I have a hard time remember where each of them are and I can't type out a ten digit number the same what I could type out a ten letter word. And because, well... it's my language.

That being said the basis for numbers are fully typed out words. With two potential patterns:

* Single digit number strings
  
  ```
  symbol> one two three four five six seven eight nine
  123456789
  ```
* Number phrases

  ```
  symbol> one hundred twenty three million four hundred fifty six thousand seven hundred and eigthy nine
  123456789
  ```
  
## Strings

Literal strings can have what ever characters, including symbols, the user desires. But we will define them without symbols.

```
symbol> string start This is a string with numbers and symbols: #123 string end
This is a string with numbers and symbols: #123
```

Some big considerations here:

* Would it be easier to have a single word beginning and end delimiter? EX ```stringstart``` and ```stringend```
* What if some one has the string ```string end``` in their string definition? How do you escape? One idea:

  ```
  symbol> string start escape start string end escape end string end
  string end
  
  symbol> string start escape start escape start escape end string end
  escape start
  ```
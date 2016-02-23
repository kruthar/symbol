# Code Structure

I am invisioning this language as being more of a scripting language like Bash or Python as opposed to object oriented like Java.

With that thought in mind, the layout of the code would be line after line of commands. I say that each line is explicitly a command because I never understood why languages allow you to execute an expression on its own with no guide for the output of that expression.

Looping would be a must, I mean you can't do a whole lot of useful stuff without being able to loop. So loops would look similar to Bash loops in that they have an initial loop identifying line which specifies which type of loop and the parameters of the loop, followed by open and close lines to specify the body. More on loops later.

I like the idea of strict scoping, meaning variables created in a certain scope are either destroyed or else not available outside of that scope. A counter example to that is Python. Python you can create a variable inside of a code block such as ```for``` or ```if```, once that block is executed and you are returned to your previous scope, you can still access the variables you created inside those blocks. This makes for some frustrating debugging some times. Of course though higher scope should follow you into code blocks, so any variables created before a ```for``` block can be used inside the block.
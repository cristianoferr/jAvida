# jAvida
An implementation in Java of the Avida architecture (virtual life organisms running on virtual machines)

About Avida:
https://en.wikipedia.org/wiki/Avida

More details about avida:
http://myxo.css.msu.edu/papers/nature2003/Nature03_Complex.pdf

My implementation off Avida differs in some key points:
1. there are more instructions and they are closer to the actual instructions found on processors. 
2. the instructions are separated from the params, so "INC" will work with any register, the command will then occupy 2 bytes in memory (in contrast to INC_A)
3. I haven´t found a good goal defining algorithm, none that I liked... something to see in the future.
4. organisms can move around via "transfer" and can connect to neighbours and read/write instructions.
5. organisms can only create children in nearby empty spots, if no empty spot exists and the organism try to make a child then a critical error occurs (this gives a more organic feel to the whole).

It´s all object oriented as well.

A video of the program running:
https://www.youtube.com/watch?v=zB7HHCRhiTg

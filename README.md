# AES-Encryption
Date: 15/05/2019  
Authors: Leala Darby, Brenden Mayall  

This task was written and tested in Java 8 and Windows 10.

To run the program, first compile it by typing "javac Application.java" into the command prompt and 
pressing enter. After this the program can be run with a datafile of your choice, eg input.txt, and 
using either mode of the algorithm by typing "java Application input.txt e" for encryption, 
or "java Application input.txt d" for decryption, into the command prompt. 

The main method (entry point) is contained in the Application class. Application.java contains the code for reading input, 
writing output, instantiating AES and generating the format of the Avalanche analysis, which involves converting and manipulating BitSets. 
AES.java contains all of the functionality for AES encryption and decryption. Broadly, it contains first a series of lookup tables 
used for various substitutions and XOR operations, then a constructor that converts the input plaintext/ciphertext into the traditional 
state matrix format of bits to be operated on, as well as the key, and stores them as two-dimensional integer arrays. The class then 
contains methods detailing the structure of each of the 5 versions of AES (AES0 through to AES4), for both encryption and decryption. 
Following this are our implementation of the transformations, both original and inverse where applicable, such as sub bytes, shift rows, 
and add round key. Finally we have some helper methods used for deep copying matrices, converting a matrix to a string, and a getter for 
the roundResult variable.  


Textual resources used in the development of this code include our prescribed textbook: "Cryptography and 
Network Security - Principles and Practice" Seventh Edition by William Stallings, and the official 
Advanced Encryption Standard publication issued by the National Institute of Standards and Technology. 
These each were most useful in providing an overall understanding of the encryption and decryption algorithms,
as well as the individual steps taken in each. The textbook diagrams and examples were particularly helpful 
for working through and understanding the matrix multiplications undertaken in the mixColumns and inverseMixColumns 
operations, using the GF(2^8) as shown on page 187. When combined and contrasted with the explanations provided in 
the official AES publication (see page 17 for mixColumns), the relationship between polynomials and the matrices in 
question became clear. Another source of value was this youtube video "AES Rijndael Cipher explained as a Flash animation"
created by AppliedGo (https://www.youtube.com/watch?v=gP4PqVGudtg) which contains an animation of the entire AES process,
expressing the state matrix in hex. This was most useful for my understanding of the Key Scheduling section of the algorithm
in particular. The repetitive and clear visualisations with real-time updates of the round constant matrix and each column 
(or word) in the key as it was operated on (particularly when examined in combination with the pseudocode in the textbook on 
page 191) enabled me to comprehend then complete my written examples and drafts of the the key expansion section of the AES 
class, and from there to implement this section of the algorithm.  

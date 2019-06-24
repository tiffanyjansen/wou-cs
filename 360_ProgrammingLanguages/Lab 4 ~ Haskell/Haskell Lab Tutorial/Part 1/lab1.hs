--Learning Haskell
--Lab 1 Questions
--Tiffany Jansen

--This import is for the characterFunc because I wasn't sure how else to do it...
import Data.Char

--1.1: Finished downloading the stuff.

--1.2:(Baby's First Steps stuff (Wasn't entirely sure what you wanted))
doubleMe x = x + x
doubleUs x y = doubleMe x + doubleMe y
doubleSmallNumber x = if x > 100
                        then x
                        else x*2
doubleSmallNumber' x = (if x > 100 then x else x*2) + 1

--1.3: What is the square root of 818281336460929553769504384519009121840452831049?
squareRoot = sqrt 818281336460929553769504384519009121840452831049

--1.4: What is the ASCII character that comes before uppercase A?
characterFunc = intToDigit (digitToInt 'A' - 1)

--1.5: What is a function that evaluates to True or False, 
--that tells you whether or not three times a number plus one is even?
functionEven x = if (x*3 + 1) `mod` 2 == 0
                    then True
                    else False
 
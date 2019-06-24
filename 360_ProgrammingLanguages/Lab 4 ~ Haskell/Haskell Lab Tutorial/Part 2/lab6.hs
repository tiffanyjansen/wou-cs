--Learning Haskell
--Lab 6 Questions
--Tiffany Jansen

import Data.Char

--6.1: Use one or more of the following functions to solve 5 of the following problems.
--6.1.a: Multiply every element of a list by 10
--multByTen [Int] -> [Int]
multByTen :: Integral a => [a] -> [a]
multByTen xs = map (*10) xs

--6.1.b: Create an infinite list of the powers of 2
powersTwo :: [Integer]
powersTwo = map (^2) [0,1..]

--6.1.c: Increment every element in a list. Write it again so that it can increment things
-- that are not numbers, i.e. turn "Hello" into "Ifmmp"
incrementList :: Integral a => [a] -> [a]
incrementList xs = map (+1) xs

incrementListChar :: [Char] -> [Char]
incrementListChar xs = map (chr) (map (+1) (map ord xs))

--6.1.d: Subtract 10 from every element in a list.
subTen :: Integral a => [a] -> [a]
subTen xs = map (subtract 10) xs

--6.1.e: Remove all spaces from a string
removeSpace :: [Char] -> [Char]
removeSpace xs = filter (/= ' ') xs

--6.1.f: Tell you True or False whether or not a list contains the number 55.
containsFifty :: Integral a => [a] -> Bool
containsFifty xs = any (== 55) xs

--6.1.g: Tell you True or False whether or not a list contains a value that is divisible by 42
containsDivFortyTwo :: Integral a => [a] -> Bool
containsDivFortyTwo = any (\x -> x `mod` 42 == 0)

--6.1.h: Remove all elements at the beginning of a list of booleans that are not True
removeBeginFalse :: [Bool] -> [Bool]
removeBeginFalse xs = dropWhile (== False) xs

--6.1.i: Use zipWith to take a list like this [5,3,8,2,3,6,3] to this [100000,1000,100000000,100,
--1000,1000000,1000] i.e. it raises 10 to the power of the element in the list and places it in 
--that list.
raiseTen :: Integral a => [a] -> [a]
raiseTen xs = zipWith (^) [10,10..] xs

--6.1.j: Remove all spaces from the end of a string (often called stripping a string).
removeEndSpace :: String -> String
removeEndSpace xs = reverse (dropWhile (== ' ') (reverse xs))

--6.1.k: Tell you True or False if a list contains nothing but even numbers.
evenNums :: Integral a => [a] -> Bool
evenNums xs = all (even) xs

--6.1.l: Put the word "not" on the front of all strings in a list.
addNot :: [String] -> [String]
addNot xs = map ("not " ++) xs

--6.1.m: Reverse all strings contained in a list.
reverseStrings :: [String] -> [String]
reverseStrings xs = map (reverse) xs

--6.2: Choose two of these functions and rewrite as lambda functions
--6.2.a: plus x y = x + y
plus :: (Integer, Integer) -> Integer
plus = \(x,y) -> x + y

--6.2.b: (*4)
timesFour :: Integer -> Integer
timesFour = \x -> x*4

--6.2.d: A function that takes the square root of a number and then rounds it.
squareRounded ::  Float -> Int
squareRounded = \x -> round (sqrt x)

--6.3: Not assigned.
--6.4: Use a Lambda expression and map to take a list of tuples and produce a list of values. The 
--list contains the lengths of two sides of right triangles, a and b. You want to produce a list 
--that contains the lengths of all three sides, where the third side, c, is found with the 
--Pythagorean theorem.
pythagorean :: Floating a => [(a, a)] -> [(a, a, a)]
pythagorean = map (\(a,b) -> (a, b, sqrt((a^2) + (b^2))))


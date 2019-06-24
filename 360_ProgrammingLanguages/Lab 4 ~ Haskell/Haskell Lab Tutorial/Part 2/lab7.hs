--Learning Haskell
--Lab 7 Questions
--Tiffany Jansen

module Lab7
(
--comma separated list of exported function names here
length', convertIntToStringLeft, convertIntToStringRight, express1, express2, express3
) where

-- Imports and function definitions follow..
import Data.Char

--7.1: Trace out the execution of each expression, as was 
--done in class, and show how they are different.

--foldl (*) 6 [5,3,8]
--6*5*3*8

--foldr (*) 6 [5,3,8]
--6*8*3*5

--7.2: Write your own version of length using a fold. 
--Export this function from your module.
length' :: [a] -> Int
length' xs = foldl(\acc x -> acc + 1) 0 xs

--7.3: Write two functions, one that uses a left fold 
--and one that uses a right fold, to imitate the behavior of this map

convertIntToStringLeft :: [Int] -> [Char]
convertIntToStringLeft xs = foldl(\acc x -> acc ++ [intToDigit x]) [] xs

convertIntToStringRight :: [Int] -> [Char]
convertIntToStringRight xs = foldr(\x acc -> intToDigit x:acc) [] xs

--7.4: Rewrite two of the following expressions with as few parentheses 
--as possible using the function application operator, $, and/or function composition.
--7.4.1: length (filter (<20) [1..100])
express1 = length $ filter (<20) [1..100]

--7.4.2: take 10 (cycle (filter (>5) (map (*2) [1..10])))
express2 = take 10 $ cycle $ filter (>5) $ map (*2) [1..10]

--7.4.3: sum (map length (zipWith (flip (++)) ["love you", "love me"] ["i ", "you "]))
express3 = sum $ map length $ zipWith (flip (++)) ["love you", "love me"] ["i ", "you "]


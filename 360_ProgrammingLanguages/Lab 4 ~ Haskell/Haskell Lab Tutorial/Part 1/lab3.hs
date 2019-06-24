--Learning Haskell
--Lab 3 Questions
--Tiffany Jansen

--3.1: Write a function that uses list comprehensions to generate a list of lists
makeList x = if x > 0 then makeList (x-1) ++ [[1..x]] else []

--3.2: Write a Haskell function that takes a string, and replaces any space 
--characters with the special code %20
sanitize :: [Char] -> [Char]
sanitize s = concat [if x == ' ' then "%20" else [x] | x <- s]

--3.3: (Not assigned)

--3.4: Choose 5 of the following functions, look up their type signature with :t 
--in ghci. Write it down.

--5 Operations:
--(1) 3 min
--    min :: Ord a => a -> a -> a
findMin = min 5 6 

--(2) 5 take
--    take :: Int -> [a] -> [a]
takeSeven = take 7 [1..20]   

--(3) 7 head
--    head :: [a] -> a
headList = head [1..10]      

--(4) 8 sqrt
--    sqrt :: Floating a => a -> a
sqrtFunc = sqrt 49    
 
--(5) 10 succ
--    succ :: Enum a => a -> a
succFunc = succ 9

--I wasn't entirely sure what the second part of this meant... so I just guessed.
--I'm sorry if this wasn't really what you were looking for.      
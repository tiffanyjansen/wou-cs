--Learning Haskell
--Lab 5 Questions
--Tiffany Jansen

--5.1: Implement the greatest common divisor algorithm
gcdMine :: Integral a => a -> a -> a
gcdMine x y = if y == 0 then x else gcdMine y (x `mod` y)

gcdCheck :: Integral a => a -> a -> (a, a, [Char])        
gcdCheck x y = (myAnswer, correctAnswer, comment)
  where 
    myAnswer = gcdMine x y
    correctAnswer = gcd x y
    comment = if myAnswer == correctAnswer then "Matches" else "Does not match"
    
--5.2: Write a function to compute Fibonacci numbers.
fibonacci :: Int -> Int
fibonacci n
    | n == 0    = 0
    | n == 1    = 1
    | otherwise = fibonacci (n-1) + fibonacci (n-2)

--5.3: Write a function that counts how many items are found in a list that match the one given:
count :: (Eq a, Num b) => a -> [a] -> b
count x [] = error "Nothing to match"
count x [y] = if x == y then 1 else 0
count y (x:xs) = if y == x then 1 + count x (xs) else count y (xs)

--5.4: Not assigned
--5.5: Rewrite your answer for Lab 3 question 2 (sanitize) using recursion.
sanitize' :: [Char] -> [Char]
sanitize' (x:xs) = if x == ' ' then '%':'2':'0':sanitize' xs else x:sanitize' xs
--Learning Haskell
--Lab 2 Questions
--Tiffany Jansen

--2.1: (Not assigned)

--2.2: What is the product of all the odd numbers one to one hundred?
productOdd = product [1,3..100]

--2.3: Find the greatest number in the following list, that is not the first or last 
--number.
--[99,23,4,2,67,82,49,-40]
greatestMidNum = maximum (init (tail [99,23,4,2,67,82,49,-40]))

--2.4: Write an expression to construct a list like this: [6,19,41,-3]
constructList = 6:19:41:(-3):[]

--2.5: Write Haskell expressions that use list comprehensions to solve the following problems.
--2.5.1: What are the first 27 even numbers? Use the even function and be Lazy!
evenNumbers = take 27 [x | x <-[1..], even x]

--2.5.2: Provide a list of all odd numbers less than 200 that are divisible by 3 and 7.
oddNumbers = [x | x <- [1..200], x `mod` 3 == 0 && x `mod` 7 == 0]
  
--2.5.3: How many odd numbers are there between 100 and 200 and that are divisible by 9?
numOddNums = length ([x | x <- [100,101..200], x `mod` 9 == 0])
  
--2.5.4:(Not assigned)

--2.5.5: Count how many negative numbers there are in a list of numbers.
countNegNums xs = length ([x | x <- xs, x < 0])

--2.6: Create a list of Hexadecimal mappings like this:
--[(0,'0'),(1,'1'),(2,'2'),(3,'3'),(4,'4'),(5,'5'),(6,'6'),(7,'7'),(8,'8'),(9,'9'),
--(10,'A'),(11,'B'),(12,'C'),(13,'D'),(14,'E'),(15,'F')]
hexMap = (zip[0,1..9]['0','1'..'9']) ++ (zip[10, 11..15]['A', 'B'..'F'])


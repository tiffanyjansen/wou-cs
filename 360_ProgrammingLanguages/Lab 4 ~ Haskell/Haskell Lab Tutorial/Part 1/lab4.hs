--Learning Haskell
--Lab 4 Questions
--Tiffany Jansen

--4.1: For the following, implement using pattern matching of parameters
--4.1.a: A function that associates the integers 0 through 3 with "Heart", "Diamond", 
--"Spade", and "Club", respectively.
getSuit :: Int -> String
getSuit 0 = "Heart"
getSuit 1 = "Diamond"
getSuit 2 = "Spade"
getSuit 3 = "Club"
getSuit x = error "There are only 4 suits."

--4.1.b: A function that computes the dot product of two 3D vectors (tuples). Follow 
--the Algebraic Definition.
dotProduct :: (Double, Double, Double) -> (Double, Double, Double) -> Double
dotProduct (x1,y1,z1)(x2,y2,z2) = (x1*x2)+(y1*y2)+(z1*z2)

--4.1.c: A function that reverses the order of the first three elements in a list.
reverseFirstThree :: [a] -> [a]
reverseFirstThree [] = []
reverseFirstThree (x:[]) = x:[]
reverseFirstThree (x:y:[]) = y:x:[]
reverseFirstThree (x:y:z:h) = z:y:x:h

--4.2: For the following, implement using guards
--4.2.a: Write a function that will tell you how warm or cold it feels like outside 
--given a temperature in Fahrenheit.
feelsLike :: Double -> String
feelsLike temp
  | temp >= 200 = "Oven-like"
  | temp >= 75 = "Super hot"
  | temp >= 55 = "Comfortable"
  | temp >= 32 = "Winter Wonderland"
  | temp > (-45.3) = "Freezing"
  | otherwise = "Frostbite Central"

--4.2.b: Modify your code for the previous problem to convert from celsius to fahrenheit 
--at the same time you're telling what it feels like.
feelsLike2 :: Double -> (Double, String)
feelsLike2 cels
  | far >= 200 = (far, "Oven-like")
  | far >= 75 = (far, "Super hot")
  | far >= 55 = (far, "Comfortable")
  | far >= 32 = (far, "Winter Wonderland")
  | far > (-45.3) = (far, "Freezing")
  | otherwise = (far, "Frostbite Central")
  where far = (cels * 1.8) + 32

--4.3(Not assigned)

--4.4: Write a function that uses a let expression and pattern matching of tuples in a 
--list comprehension to calculate the volume of a list of cylinders.
cylinderToVolume :: [(Double, Double)] -> [Double]
cylinderToVolume xs = [vol | (radius, height) <- xs, let vol = pi*(radius^2)*height]
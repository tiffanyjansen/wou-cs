{-
    CS 360 lab 4
    Emmet syntax HTML generator.
    Starting code
    Tiffany Jansen
-}

module Main
(
main
) where

import System.IO (hFlush, stdout)
import Data.List --Used for "isInfixOf" method to decide if the String contains a certain character.
import Data.Char --Used for "DigitToInt" method to convert a string to an int

{-
    First processing function.  This function takes the entire
    line of text from the user.

    This one obviously doesn't do what is required for the lab,
    but is just here to show you the basic I/O
-}
process :: String -> String
process t
       | ['.'] `isInfixOf` t = addClass t
       | ['#'] `isInfixOf` t = addId t
       | ['>'] `isInfixOf` t = addChild t
       | ['*'] `isInfixOf` t = mult t
       | otherwise           = "<" ++ t ++ "></" ++ t ++ ">\n"
       -- isInfixOf decides if the given substring is in the string passed in.
        
addClass :: String -> String
addClass text = "<" ++ fst t ++ " class=" ++ ['"'] ++ (tail $ snd t) ++ ['"'] ++ "></" ++ fst t ++ ">"
      where t = break (=='.') text
      
addId :: String -> String
addId text = "<" ++ fst t ++ " id=" ++ ['"'] ++ (tail $ snd t) ++ ['"'] ++ "></" ++ fst t ++ ">"
      where t = break (=='#') text
      
addChild :: String -> String
addChild text = "<" ++ fst t ++ "><" ++ (tail $ snd t) ++ "></" ++ (tail $ snd t) ++ "></" ++ fst t ++ ">"
      where t = break (=='>') text
      
mult :: String -> String
mult text
      | x <= 0 = error "No multiplicity"
      | x >= 1 = addMult (fst t) x
      where t = break (=='*') text
            x = digitToInt $ head $ tail $ snd t 
            --digitToInt converts the char to an int so we can do the recursion.
            
addMult :: String -> Int -> String
addMult text x
      | x == 1 = process (text)
      | x > 1 = process text ++ addMult text (x-1)
{-
    The main entry point function.  Interactively expand Emmet syntax
    abbreviations and generate HTML skeleton code.  Prints HTML to standard
    output.

    Enter an empty line to quit.
-}
main :: IO ()
main = do
  putStrLn "Type Emmet abbreviations and we'll generate HTML for you"
  putStrLn "  -- to quit, hit return on an empty line"
  -- invoke a recursive main to continue to prompt the user until they wish to quit
  mainR

-- Main interactive function
mainR :: IO ()
mainR = do
  putStr "\nemmet: "
  hFlush stdout   -- line buffering prevents the prompt from printing without the newline, so this sends it
  oneLine <- getLine
  if null oneLine
    then do
      putStrLn "Exiting ..."
      return ()
    else do
      putStrLn $ process oneLine
      mainR
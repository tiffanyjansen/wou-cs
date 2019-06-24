#http://mentalarcade.com/visa/snowman.pov
#Code for base2.pov that I will be using for this movie.

import os
import re
import math
import makeMovie
import sphere

def animate():
  #This opens the original code and reads it into a string so I can work with it
  file = open('base.pov')
  text = file.read()
  file.close()
  
  i = 0
  while i < 10:
    sphere1 = sphere.Sphere(0, 0, 1.4, 1)
    match = re.search("^sphere\s{.......................", text, re.DOTALL)
    insertString = ""
    if match:
      insertString += bounceBall(sphere1, match.group(), i)
      text = text.replace(match.group(), insertString)
    else:
      print "No match Found"  
  
    outfile = 'tmp2.pov'
    fileOut = open(outfile, 'w')
    fileOut.write(text)
    fileOut.close()
  
    picNo = i
    pov_cmd = ' pvengine.exe +I%s +O%s -D -V +A +H600 +W800'
    cmd = pov_cmd % ('tmp2.pov', str(picNo).zfill(4) + "temp2.png")
    os.system(cmd)
    
    i+= 1
  return i    
  
def bounceBall(sphere, text, num):
  while num < 5:
    sphere.changePos(0, 0, (num + 2))
    return sphere.toString()   
  while num > 5:
    sphere.changePos(0, 0, (10 - num))
    return sphere.toString()

def main():
  num = animate()
  makeMovie.make_movie(num-1, 2)
  

if __name__ == '__main__':
  main()  
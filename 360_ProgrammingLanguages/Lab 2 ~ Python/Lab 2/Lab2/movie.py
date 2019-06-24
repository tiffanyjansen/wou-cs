#http://mentalarcade.com/visa/snowman.pov
#Code for base2.pov that I will be using for this movie.

import os
import re
import snowball
import math

def animate():
  #This opens the original code and reads it into a string so I can work with it
  file = open('base2.pov')
  text = file.read()
  file.close()
  
  picNo = 0
  pov_cmd = ' pvengine.exe +I%s +O%s -D -V +A +H600 +W800'
  cmd = pov_cmd % ('base2.pov', str(picNo).zfill(4) + "temp_snow.png")
  os.system(cmd) 
  
  y = -1.0
  z = -2
  x = -5
  snow1 = snowball.Snowball(x, y, z, 2)
   
  newText = text + "\n" + snow1.toString()
   
  outfile = 'tmp_snow.pov'
  fileOut = open(outfile, 'w')
  fileOut.write(newText)
  fileOut.close()
   
  picNo = 1
  pov_cmd = ' pvengine.exe +I%s +O%s -D -V +A +H600 +W800'
  cmd = pov_cmd % ('tmp_snow.pov', str(picNo).zfill(4) + "temp_snow.png")
  os.system(cmd) 
  
  i = 2
  while i < 30:
    match = re.search(snow1.toString(), newText)
    if not match:
       print "No match found."
    
    x += 1
    y += 1
    if i < 10:
      z += 1
    else:    
      z = -((x*x)/4 + (y*y)/9)
    snow1.setPosition(x, y, z)
   
    newText = newText.replace(match.group(), snow1.toString())
  
    outfile = 'tmp_snow.pov'
    fileOut = open(outfile, 'w')
    fileOut.write(newText)
    fileOut.close()
  
    picNo = i
    pov_cmd = ' pvengine.exe +I%s +O%s -D -V +A +H600 +W800'
    cmd = pov_cmd % ('tmp_snow.pov', str(picNo).zfill(4) + "temp_snow.png")
    os.system(cmd)
    
    i += 1
    

def main():
  animate()

if __name__ == '__main__':
  main()  
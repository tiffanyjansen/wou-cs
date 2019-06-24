#http://www.imagico.de/pov/ray_povsdl.php
#This is where I found my code for the picture. I also edited it a little
#because I just wanted one sphere in the picture, not all of the rest of them.

import os
import re
import math

def animate():
  file = open('base.pov')
  text = file.read()
  file.close()
  
  i = 0
  r = math.sqrt(227.25)
  rad = 1
  newText = text
  while i < 500:
    match = re.search(r'(location\s+)<(\d+.\d+),\s+(\d+.\d+),\s+(\d+.\d+)>', newText)
    if match:
      x = match.group(2)
      y = match.group(3)
      z = float(match.group(4))
    else:
      match = re.search(r'(location\s+)<(-\d+.\d+),\s+(\d+.\d+),\s+(\d+.\d+)>', newText)
      if match:
        x = match.group(2)
        y = match.group(3)
        z = float(match.group(4))
      else:
        match = re.search(r'(location\s+)<(-\d+.\d+),\s+(-\d+.\d+),\s+(\d+.\d+)>', newText)
        if match:
          x = match.group(2)
          y = match.group(3)
          z = float(match.group(4))
        else:
          match = re.search(r'(location\s+)<(\d+.\d+),\s+(-\d+.\d+),\s+(\d+.\d+)>', newText)
          x = match.group(2)
          y = match.group(3)
          z = float(match.group(4))        
    rad += 0.125 
    newX = r * math.cos(rad) 
    newY = r * math.sin(rad)        
    newZ = z + 0.125 
    newText = newText.replace(x, str(newX), 1)
    newText = newText.replace(y, str(newY), 1)
    newText = newText.replace(str(z), str(newZ), 1)
    outfile = 'tmp.pov'
    fileOut = open(outfile, 'w')
    fileOut.write(newText)
    fileOut.close()
    #This is what creates the pictures to become the movie.
    picNo = i
    pov_cmd = ' pvengine.exe +I%s +O%s -D -V +A +H600 +W800'
    cmd = pov_cmd % ('tmp.pov', str(picNo).zfill(4) + "temp.png")
    os.system(cmd)    
    i += 1
  return i    
    
def make_movie(num):
  print 'Encoding movie'
  cmd = 'binkconv.exe ????temp.png*0-' + str(num) + ' movie_Tiffany.avi /K25 ' 
  os.system(cmd)   

def main():
  num = animate()
  make_movie(num-1) 
  

if __name__ == '__main__':
  main()  
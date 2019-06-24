import os

def make_movie(num, part):
  print 'Encoding movie'
  cmd = 'binkconv.exe ????temp' + str(part) + '.png*0-' + str(num) + ' movie_part' + str(part) + '.avi /K25 ' 
  os.system(cmd)
   
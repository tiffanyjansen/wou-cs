class Torus:
  sturm = (sturm, true)
  modifiers = {}
  modifiers[pigment] = "color rgb 1"
  modifiers[finish] = "reflection 0.9"
  
  def __init__(self, maj, min):
    self.major = maj
    self.minor = min
    
  def changeSturm(self, bool):
    self.sturm[2] = bool
  
  def changeMajor(self, new_maj):
    self.major = new_maj
  
  def changeMinor(self, new_min):
    self.minor = new_min  
    
  def toString(self):
    returnString = torus:
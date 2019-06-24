class Snowball:
  snowType = {}
  snowType['texture'] = "pigment { color White }"
  snowType['normal'] = "bumps 0.3 scale 0.1"
  snowType['finish'] = "phong 1"
  
  def __init__(self, x_pos, y_pos, z_pos, rad):
    self.position = {}
    self.position["x"] = x_pos
    self.position["y"] = y_pos
    self.position["z"] = z_pos
    self.radius = rad
    
  def setPosition(self, new_x, new_y, new_z):
    self.position["x"] = new_x
    self.position["y"] = new_y
    self.position["z"] = new_z

  def toString(self):
    returnString = "sphere { "
    returnString = returnString + " <" + str(self.position["x"]) + "," + str(self.position["y"]) + "," + \
      str(self.position["z"]) + "> " + str(self.radius) + " "
    for key in self.snowType.keys():
      returnString = returnString + key + " { " + self.snowType[key] + " } " 
    return returnString + " }"
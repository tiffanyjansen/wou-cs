
class Sphere:
  attributes = []
  attributes.append("texture { ")
  attributes.append("pigment { color rgb 1 }")
  attributes.append(" finish { ")
  attributes.append("diffuse 0.3")
  attributes.append("ambient 0.0")
  attributes.append("specular 0.6")
  attributes.append("reflection 0.8")
  attributes.append("}")
  attributes.append("}")
  
  def __init__(self, x_pos, y_pos, z_pos, rad):
    self.vectorPos = {}
    self.vectorPos["x"] = x_pos
    self.vectorPos["y"] = y_pos
    self.vectorPos["z"] = z_pos
    self.radius = rad
  
  def changeRadius(self, new_rad):
    self.radius = new_rad
   
  def changePos(self, new_x, new_y, new_z):
    self.vectorPos["x"] = new_x
    self.vectorPos["y"] = new_y
    self.vectorPos["z"] = new_z
    
  def showAttributes(self):
    returnString = ""
    for attribute in self.attributes:
      returnString += "\n" + attribute
    return returnString

  def showVectorPos(self):
    returnString = "<" + str(self.vectorPos["x"]) + ","
    returnString += str(self.vectorPos['y']) + ","
    returnString += str(self.vectorPos['z']) + ">"
    return returnString
  
  def toString(self):
    returnString = "sphere {+ \n"
    returnString += self.showVectorPos()
    returnString += " " + str(self.radius) + "\n"
    returnString += self.showAttributes()
    return returnString
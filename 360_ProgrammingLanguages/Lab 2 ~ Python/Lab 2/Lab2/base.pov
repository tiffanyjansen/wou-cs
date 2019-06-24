camera {
  location  <5.0, -12.0, 2.0>
  up z sky z
  look_at   <0.0, 0.0, 0.5> 
  angle 40
}

sky_sphere {
  pigment {
    gradient z
    color_map {
      [0.0 rgb <0.6,0.7,1.0>]
      [0.2 rgb <0.2,0.3,0.9>]
    }
  }
}

light_source {
  <3, 1, 2>*1000
  color rgb <2.2, 1.8, 1.5>
}   

// ----------------------------------------

plane {
  z, 0
  texture {
    pigment {
      checker
      color rgb 1, color rgb 0
    }
  }
}

sphere {
  z*1.4, 1
  texture {
    pigment { color rgb 1 }
    finish{
      diffuse 0.3
      ambient 0.0
      specular 0.6
      reflection 0.8
    }
  }
}  
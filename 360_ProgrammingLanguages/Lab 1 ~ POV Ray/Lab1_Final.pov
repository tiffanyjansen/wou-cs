camera {
    fisheye
    location <25,35,10>
    look_at <0,10,0>
}
light_source {
    <-25,100,-25>
    color rgb <1,1,0>
    spotlight
}
union {  
    union {
        cone{ 
            <0,0,0>, 5, <0,25,0>, 0
            pigment { color rgb <1,0,0.1> }
            interior { fade_color color rgb <1,1,0> }
            double_illuminate
            finish { ambient rgb <0.25,0.25,0> }
        } 
        sphere {
            <0,25,0>, 5
            pigment { color rgb <0,1,0.5> }
            interior {
                ior 0.5
                fade_power 1
            }
            finish { 
                ambient color rgb <1,0,0.5>
                brilliance 1
            }
        }       
    }
    box { 
        <-5,-15,-5>, <5,0,5>
        pigment { color rgb <0.25,1,0.25> }
        interior {  
            fade_color color rgb <0.75,0,0.75>
            dispersion 2
        }
        finish { 
            ambient color rgb <0.25,0.5,0.25>
            reflection color rgb <0.5,0.5,0.5>
        }
    }
}
plane {  
    <0,50,0>, -15
    pigment { color rgb <1,1,1> }
}
﻿using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace readygotravel.Models.ViewModels
{
    public class CountryCityJS
    {
        public int ID { get; set; }

        [Required]
        public string Name { get; set; }
    }
}
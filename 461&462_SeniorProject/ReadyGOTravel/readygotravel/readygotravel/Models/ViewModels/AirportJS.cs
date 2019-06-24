using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace readygotravel.Models.ViewModels
{
    public class AirportJS
    {
        public int AirportID { get; set; }

        [Required]
        [StringLength(5)]
        public string AirportCode { get; set; }

        [StringLength(5)]
        public string Direction { get; set; }
    }
}
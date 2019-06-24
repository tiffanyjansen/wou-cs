using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace readygotravel.Models.ViewModels
{
    public class SearchDashBoardVM
    {
        [Required(ErrorMessage = "Start Date is Required and Must be at Least Set to Tomorrow")]
        [DisplayFormat(ApplyFormatInEditMode = true, DataFormatString = "{0:MMM/dd/yyyy}")]
        [Display(Name = "Starting Travel Date")]
        public DateTime StartDate { get; set; }

        [Required(ErrorMessage = "End Date is Required and Must be the Same as Start Date or Beyond")]
        [DisplayFormat(ApplyFormatInEditMode = true, DataFormatString = "{0:MMM/dd/yyyy}")]
        [Display(Name = "Return Travel Date")]
        public DateTime EndDate { get; set; }

        [Required]
        [Display(Name = "Number of Travelers")]
        public int NumTravelers { get; set; }

        [Required]
        [Range(50,999999)]
        [Display(Name = "Max Budget (in USD)")]

        [RegularExpression(@"^\d+(?:.\d{0,2})?$", ErrorMessage = "Please enter only a positive value.")]
        public decimal MaxAmount { get; set; }

        [Display(Name = "Hotel Star Value")]
        public int? HotelStarValue { get; set; }

        [StringLength(2)]
        [Display(Name = "Flight Type")]
        public string FlightType { get; set; }
    }
}
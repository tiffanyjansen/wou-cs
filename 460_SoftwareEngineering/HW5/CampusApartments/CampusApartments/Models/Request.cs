using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace CampusApartments.Models
{
    public class Request
    {
        [Key]
        public int RequestID { get; set; }

        [Required]
        [StringLength(25)]
        [Display(Name = "First Name")]
        public string FirstName { get; set; }

        [Required]
        [StringLength(25)]
        [Display(Name = "Last Name")]
        public string LastName { get; set; }

        [Required]
        [Phone]
        [Display(Name = "Phone Number")]
        public string PhoneNumber { get; set; }

        [Required]
        [StringLength(15)]
        [Display(Name = "Apartment Name")]
        public string ApartmentName { get; set; }

        [Required]
        [Display(Name = "Unit Number")]
        public int UnitNumber { get; set; }

        [Required]
        [StringLength(1000)]
        [Display(Name = "Maintenance Required")]
        public string MaintenanceRequired { get; set; }

        [Display(Name = "Time of Request")]
        public DateTime TimeOfRequest { get; set; }

        [Display(Name = "Permission to Enter?")]
        public bool Permission { get; set; }
    }
}
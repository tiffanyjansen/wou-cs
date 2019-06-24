using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity.Spatial;

namespace readygotravel.Models.ViewModels
{
    public class RegisterAccountViewModel
    {
        public RegisterViewModel User { get; set; }

        [Required]
        [StringLength(128)]
        [Display(Name = "First Name")]
        public string FirstName { get; set; }

        [Required]
        [StringLength(128)]
        [Display(Name = "Last Name")]
        public string LastName { get; set; }
    }
}
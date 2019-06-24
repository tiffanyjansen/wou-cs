using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace readygotravel.Models.ViewModels
{
    public class TopSearched
    {
        [Required]
        [Display(Name = "State")]
        public string EndState { get; set; }

        [Required]
        [Display(Name = "Number of Times Searched")]
        public int TimesSearched { get; set; }
    }
}
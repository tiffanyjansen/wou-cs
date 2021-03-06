namespace ATMProject.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class User
    {
        public int ID { get; set; }

        [Required]
        [StringLength(10)]
        public string PIN { get; set; }

        [Column(TypeName = "money")]
        [Display(Name = "Checking Amount")]
        public decimal CheckingAmount { get; set; }

        [Column(TypeName = "money")]
        [Display(Name = "Savings Amount")]
        public decimal SavingsAmount { get; set; }
    }
}

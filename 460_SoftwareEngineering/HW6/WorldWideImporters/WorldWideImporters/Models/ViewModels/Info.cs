using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace WorldWideImporters.Models.ViewModels
{
    public class Info
    {

        public Info() { }
        public Info(string Name, string PName, string Phone, string Fax, string Email, DateTime Member)
        {
            this.Name = Name;
            this.PName = PName;
            this.Phone = Phone;
            this.Fax = Fax;
            this.Email = Email;
            this.Member = Member;
        }

        //Part 1 of Assignment
        //Person Information
        [Key]
        public string Name { get; set; }

        [Display(Name="Preferred Name")]
        public string PName { get; set; }
        [Display(Name="Phone Number")]
        public string Phone { get; set; }
        [Display(Name="Fax Number")]
        public string Fax { get; set; }
        [Display(Name="Email Address")]
        public string Email { get; set; }
        [Display(Name="Member Since")]
        [DisplayFormat(DataFormatString = "{0:MM/yyyy}")]
        public DateTime Member { get; set; }

        //Part 2 of Assignment
        //Company Information
        [Display(Name = "Company")]
        public string CompanyName { get; set; }
        [Display(Name = "Phone Number")]
        public string CompanyPhone { get; set; }
        [Display(Name = "Fax Number")]
        public string CompanyFax { get; set; }
        public string Website { get; set; }
        [Display(Name = "Member Since")]
        [DisplayFormat(DataFormatString = "{0:MM/yyyy}")]
        public DateTime? CompanyYear { get; set; }

        //Purchases Information
        public int? Orders { get; set; }
        [Display(Name = "Gross Sales")]
        public decimal? GrossSales { get; set; }
        [Display(Name = "Gross Profit")]
        public decimal? GrossProfit { get; set; }

        //Top Products Information
        public IEnumerable<Product> Products { get; set; }        
    }
}
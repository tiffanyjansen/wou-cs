using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace WorldWideImporters.Models.ViewModels
{
    public class Product
    {
        [Key]
        [Display(Name="Stock Item ID")]
        public int StockItemID { get; set; }        
        public string Description { get; set; }
        [Display(Name="Line Profit")]
        public decimal Profit { get; set; }
        [Display(Name="Sales Person")]
        public string Salesperson { get; set; }
    }
}
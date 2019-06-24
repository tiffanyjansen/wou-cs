namespace InternetLanguage.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class Request
    {
        public int RequestID { get; set; }

        public DateTime DateOfRequest { get; set; }

        [Required]
        [StringLength(25)]
        public string IPAddress { get; set; }

        [Required]
        [StringLength(25)]
        public string Browser { get; set; }

        [Required]
        [StringLength(100)]
        public string SpecialSite { get; set; }
    }
}

namespace readygotravel.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class Currency
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public Currency()
        {
            Countries = new HashSet<Country>();
        }

        public int CurrencyID { get; set; }

        [Required]
        [StringLength(3)]
        public string CurrencyCode { get; set; }

        [Required]
        public string CurrencyName { get; set; }

        [Required]
        [StringLength(3)]
        public string CurrencySymbol { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Country> Countries { get; set; }
    }
}

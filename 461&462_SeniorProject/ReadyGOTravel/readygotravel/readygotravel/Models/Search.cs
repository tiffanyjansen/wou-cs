namespace readygotravel.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class Search
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public Search()
        {
            Results = new HashSet<Result>();
        }

        public int SearchID { get; set; }

        public int UserID { get; set; }

        public DateTime StartDate { get; set; }

        public DateTime EndDate { get; set; }

        public int NumTravelers { get; set; }

        [Column(TypeName = "money"), Required(ErrorMessage = "Please enter only a positive value.")]
        public decimal MaxAmount { get; set; }

        public int? HotelStarValue { get; set; }

        [StringLength(2)]
        public string FlightType { get; set; }

        public int? StartAirport { get; set; }

        public int EndAirport { get; set; }

        public virtual Airport Airport { get; set; }

        public virtual Airport Airport1 { get; set; }

        public virtual Person Person { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Result> Results { get; set; }
    }
}

namespace readygotravel.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class State
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public State()
        {
            Climates = new HashSet<Climate>();
            Geographies = new HashSet<Geography>();
        }

        public int StateID { get; set; }

        public int LocationID { get; set; }

        [Column(TypeName = "money")]
        public decimal FoodCost { get; set; }

        [Required]
        public string FoodItemOne { get; set; }

        [Required]
        public string FoodItemTwo { get; set; }

        [Required]
        public string FoodItemThree { get; set; }

        [Required]
        public string ActivityItemOne { get; set; }

        [Required]
        public string ActivityItemTwo { get; set; }

        [Required]
        public string ActivityItemThree { get; set; }

        public int SpringTemp { get; set; }

        public int SummerTemp { get; set; }

        public int FallTemp { get; set; }

        public int WinterTemp { get; set; }

        public int SpringWeather { get; set; }

        public int SummerWeather { get; set; }

        public int FallWeather { get; set; }

        public int WinterWeather { get; set; }

        public decimal CrimeRate { get; set; }

        public virtual Location Location { get; set; }

        public virtual Weather Weather { get; set; }

        public virtual Weather Weather1 { get; set; }

        public virtual Weather Weather2 { get; set; }

        public virtual Weather Weather3 { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Climate> Climates { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Geography> Geographies { get; set; }
    }
}

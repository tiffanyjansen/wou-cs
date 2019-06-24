namespace readygotravel.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class Person
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public Person()
        {
            Searches = new HashSet<Search>();
            UserStates = new HashSet<UserState>();
        }

        public int PersonID { get; set; }

        [Required]
        [StringLength(128)]
        public string UserID { get; set; }

        [Required, Display(Name = "First Name")]
        [StringLength(128)]
        public string FirstName { get; set; }

        [Required, Display(Name = "Last Name")]
        [StringLength(128)]
        public string LastName { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Search> Searches { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<UserState> UserStates { get; set; }
    }
}

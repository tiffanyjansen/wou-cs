namespace readygotravel.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("UserState")]
    public partial class UserState
    {
        public int UserStateID { get; set; }

        public int PersonID { get; set; }

        public int? LocationID { get; set; }

        public bool? International { get; set; }

        public virtual Location Location { get; set; }

        public virtual Person Person { get; set; }
    }
}

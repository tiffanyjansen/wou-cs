namespace readygotravel.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class Result
    {
        public int ResultID { get; set; }

        public int SearchID { get; set; }

        public decimal AvgHotelStar { get; set; }

        [Column(TypeName = "money")]
        public decimal? AvgFlightAmount { get; set; }

        [Column(TypeName = "money")]
        public decimal? AvgHotelAmount { get; set; }

        [Column(TypeName = "money")]
        public decimal AvgFoodCost { get; set; }

        public virtual Search Search { get; set; }
    }
}

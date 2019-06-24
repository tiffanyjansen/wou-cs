namespace AuctionHouse.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("Bid")]
    public partial class Bid
    {
        public int ID { get; set; }

        public int Item { get; set; }

        [Required]
        [StringLength(30)]
        public string Buyer { get; set; }

        [Column(TypeName = "money")]
        public decimal Price { get; set; }

        public DateTime Timestamp { get; set; }

        public virtual Buyer Buyer1 { get; set; }

        public virtual Item Item1 { get; set; }
    }
}

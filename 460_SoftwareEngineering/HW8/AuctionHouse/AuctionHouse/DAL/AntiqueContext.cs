namespace AuctionHouse.DAL
{
    using System;
    using System.Data.Entity;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Linq;
    using AuctionHouse.Models;

    public partial class AntiqueContext : DbContext
    {
        public AntiqueContext()
            : base("name=AuctionHouse")
        {
        }

        public virtual DbSet<Bid> Bids
        {
            get; set;
        }
        public virtual DbSet<Buyer> Buyers
        {
            get; set;
        }
        public virtual DbSet<Item> Items
        {
            get; set;
        }
        public virtual DbSet<Seller> Sellers
        {
            get; set;
        }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Bid>()
                .Property(e => e.Price)
                .HasPrecision(19, 4);

            modelBuilder.Entity<Buyer>()
                .HasMany(e => e.Bids)
                .WithRequired(e => e.Buyer1)
                .HasForeignKey(e => e.Buyer)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Item>()
                .HasMany(e => e.Bids)
                .WithRequired(e => e.Item1)
                .HasForeignKey(e => e.Item)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Seller>()
                .HasMany(e => e.Items)
                .WithRequired(e => e.Seller1)
                .HasForeignKey(e => e.Seller)
                .WillCascadeOnDelete(false);
        }
    }
}

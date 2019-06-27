namespace ATMProject.Models
{
    using System;
    using System.Data.Entity;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Linq;

    public partial class DataContext : DbContext
    {
        public DataContext()
            : base("name=DataContext")
        {
        }

        public virtual DbSet<User> Users { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<User>()
                .Property(e => e.CheckingAmount)
                .HasPrecision(19, 4);

            modelBuilder.Entity<User>()
                .Property(e => e.SavingsAmount)
                .HasPrecision(19, 4);
        }
    }
}

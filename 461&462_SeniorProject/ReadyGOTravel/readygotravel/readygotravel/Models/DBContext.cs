namespace readygotravel.Models
{
    using System;
    using System.Data.Entity;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Linq;

    public partial class DBContext : DbContext
    {
        public DBContext()
            : base("name=TravelDB")
        {
        }

        public virtual DbSet<Airport> Airports { get; set; }
        public virtual DbSet<Climate> Climates { get; set; }
        public virtual DbSet<Continent> Continents { get; set; }
        public virtual DbSet<Country> Countries { get; set; }
        public virtual DbSet<Currency> Currencies { get; set; }
        public virtual DbSet<Geography> Geographies { get; set; }
        public virtual DbSet<Location> Locations { get; set; }
        public virtual DbSet<Person> People { get; set; }
        public virtual DbSet<Result> Results { get; set; }
        public virtual DbSet<Search> Searches { get; set; }
        public virtual DbSet<State> States { get; set; }
        public virtual DbSet<UserState> UserStates { get; set; }
        public virtual DbSet<Weather> Weather { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Airport>()
                .HasMany(e => e.Searches)
                .WithOptional(e => e.Airport)
                .HasForeignKey(e => e.StartAirport);

            modelBuilder.Entity<Airport>()
                .HasMany(e => e.Searches1)
                .WithRequired(e => e.Airport1)
                .HasForeignKey(e => e.EndAirport)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Continent>()
                .HasMany(e => e.Countries)
                .WithRequired(e => e.Continent)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Country>()
                .HasMany(e => e.Locations)
                .WithRequired(e => e.Country)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Currency>()
                .HasMany(e => e.Countries)
                .WithRequired(e => e.Currency)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Location>()
                .HasMany(e => e.Airports)
                .WithRequired(e => e.Location)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Location>()
                .HasMany(e => e.States)
                .WithRequired(e => e.Location)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Person>()
                .HasMany(e => e.Searches)
                .WithRequired(e => e.Person)
                .HasForeignKey(e => e.UserID)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Person>()
                .HasMany(e => e.UserStates)
                .WithRequired(e => e.Person)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Result>()
                .Property(e => e.AvgHotelStar)
                .HasPrecision(3, 2);

            modelBuilder.Entity<Result>()
                .Property(e => e.AvgFlightAmount)
                .HasPrecision(19, 4);

            modelBuilder.Entity<Result>()
                .Property(e => e.AvgHotelAmount)
                .HasPrecision(19, 4);

            modelBuilder.Entity<Result>()
                .Property(e => e.AvgFoodCost)
                .HasPrecision(19, 4);

            modelBuilder.Entity<Search>()
                .Property(e => e.MaxAmount)
                .HasPrecision(19, 4);

            modelBuilder.Entity<Search>()
                .HasMany(e => e.Results)
                .WithRequired(e => e.Search)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<State>()
                .Property(e => e.FoodCost)
                .HasPrecision(19, 4);

            modelBuilder.Entity<State>()
                .Property(e => e.CrimeRate)
                .HasPrecision(6, 1);

            modelBuilder.Entity<State>()
                .HasMany(e => e.Climates)
                .WithMany(e => e.States)
                .Map(m => m.ToTable("StateClimates").MapLeftKey("StateID").MapRightKey("ClimateID"));

            modelBuilder.Entity<State>()
                .HasMany(e => e.Geographies)
                .WithMany(e => e.States)
                .Map(m => m.ToTable("StateGeographies").MapLeftKey("StateID").MapRightKey("GeographyID"));

            modelBuilder.Entity<Weather>()
                .HasMany(e => e.States)
                .WithRequired(e => e.Weather)
                .HasForeignKey(e => e.SpringWeather)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Weather>()
                .HasMany(e => e.States1)
                .WithRequired(e => e.Weather1)
                .HasForeignKey(e => e.SummerWeather)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Weather>()
                .HasMany(e => e.States2)
                .WithRequired(e => e.Weather2)
                .HasForeignKey(e => e.FallWeather)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Weather>()
                .HasMany(e => e.States3)
                .WithRequired(e => e.Weather3)
                .HasForeignKey(e => e.WinterWeather)
                .WillCascadeOnDelete(false);
        }
    }
}

namespace InternetLanguage.DAL
{
    using System;
    using System.Data.Entity;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Linq;
    using InternetLanguage.Models;

    public partial class RequestsContext : DbContext
    {
        public RequestsContext()
            : base("name=RequestsContext")
        {
        }

        public virtual DbSet<Request> Requests
        {
            get; set;
        }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
        }
    }
}

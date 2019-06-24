using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;
using CampusApartments.Models;

namespace CampusApartments.DAL
{
    public class RequestContext : DbContext
    {
        public RequestContext() : base("name=TheRequests")
        {
            
        }

        public virtual DbSet<Request> Requests { get; set; }
    }
}
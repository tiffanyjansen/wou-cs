using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using CampusApartments.DAL;
using CampusApartments.Models;

namespace CampusApartments.Controllers
{
    public class RequestsController : Controller
    {
        private RequestContext db = new RequestContext();

        // GET: Requests
        public ActionResult Index()
        {
            List<Request> list = db.Requests.ToList();
            var sorted = list.OrderBy(time => time.TimeOfRequest);
            return View(sorted);
        }

        // GET: Requests/Create
        public ActionResult Create()
        {
            @ViewBag.Time = DateTime.Now;
            return View();
        }

        // POST: Requests/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "TimeOfRequest,FirstName,LastName,PhoneNumber,ApartmentName,UnitNumber,MaintenanceRequired,Permission")] Request request)
        {
            if (ModelState.IsValid)
            {
                db.Requests.Add(request);
                db.SaveChanges();
                return RedirectToAction("ThankYou");
            }

            return View(request);
        }

        //The view for after the submit works.
        public ActionResult ThankYou()
        {
            return View();
        }
    }
}

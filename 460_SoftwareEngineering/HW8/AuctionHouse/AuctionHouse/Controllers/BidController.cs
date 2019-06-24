using AuctionHouse.DAL;
using AuctionHouse.Models;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace AuctionHouse.Controllers
{
    public class BidController : Controller
    {
        AntiqueContext db = new AntiqueContext();

        [HttpGet]
        public ActionResult Create()
        {
            //Get Lists of Buyer and Item so there are no confusions.
            ViewBag.Buyer = new SelectList(db.Buyers, "Name", "Name");
            ViewBag.Item = new SelectList(db.Items, "ID", "Name");

            return View();
        }

        [HttpPost]
        public ActionResult Create([Bind(Include = "Item,Buyer,Price")] Bid bid)
        {
            bid.Timestamp = DateTime.Now;
            Debug.WriteLine("We are in the Create Method for Bid (Post)");
            Debug.WriteLine(bid.ToString());
            
            //Check if model state is valid (if the object is actually formatted correctly)
            if (ModelState.IsValid)
            {
                Debug.WriteLine("The Model State is Valid.");
                //Add and save to database
                db.Bids.Add(bid);
                db.SaveChanges();

                return RedirectToRoute(new
                {
                    controller = "Home",
                    action = "Index"
                });
            }

            ViewBag.Buyer = new SelectList(db.Buyers, "Name", "Name");
            ViewBag.Item = new SelectList(db.Items, "ID", "Name");

            return View();
        }
    }
}
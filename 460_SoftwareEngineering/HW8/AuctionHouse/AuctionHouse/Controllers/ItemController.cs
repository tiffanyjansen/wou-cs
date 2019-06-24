using AuctionHouse.DAL;
using AuctionHouse.Models;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Diagnostics;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace AuctionHouse.Controllers
{
    public class ItemController : Controller
    {
        AntiqueContext db = new AntiqueContext();

        [HttpGet]
        public ActionResult List()
        {
            //return view with item list
            return View(db.Items);
        }

        [HttpGet]
        public ActionResult Create()
        {
            //Return View.
            ViewBag.Seller = new SelectList(db.Sellers, "Name", "Name");
            return View();
        }

        [HttpPost]
        public ActionResult Create([Bind(Include = "Name,Description,Seller")] Item item)
        {
            Debug.WriteLine("We are in the Create Method (Post)");
            
            //Check if model state is valid (if the object is actually formatted correctly)
            if (ModelState.IsValid)
            {
                Debug.WriteLine("The Model State is Valid.");
                //Add and save to database
                db.Items.Add(item);
                db.SaveChanges();

                //Redirect to the Details page so you can see what all has been added.
                return RedirectToAction("Details", item.ID);
            }
            Debug.WriteLine("Nothing is going to happen");
            //If model is not correctly formatted do nothing...
            return View();
        }

        [HttpGet]
        public ActionResult Details(int? ID)
        {
            //Debug to check where we are.
            Debug.WriteLine("We are in the Details Method");

            //Check if ID is null, if so go back to the list.
            if (ID == null)
            {
                Debug.WriteLine("The ID was null");
                return RedirectToAction("List");
            }

            //Get the item using Linq
            Item item = db.Items.Find(ID);
                
            //Check if item is null, if so go back to the list.
            if (item == null)
            {
                Debug.WriteLine("The item was null");
                return RedirectToAction("List");
            }

            //return the view with the item.
            return View(item);
        }

        [HttpGet]
        public ActionResult Edit(int? ID)
        {
            //Debug to check where we are.
            Debug.WriteLine("We are in the Edit Method (Get)");

            //Check if ID is null, if so go back to the list.
            if (ID == null)
            {
                Debug.WriteLine("The ID was null");
                return RedirectToAction("List");
            }

            //Get the item using Linq
            Item item = db.Items.Find(ID);
                
            //Check if the item is null, if so go back to the list.
            if (item == null)
            {
                Debug.WriteLine("The item was null.");
                return RedirectToAction("List");             
            }

            //return the view with the item.
            ViewBag.Seller = new SelectList(db.Sellers, "Name", "Name");
            return View(item);
        }

        [HttpPost]
        public ActionResult Edit([Bind(Include = "ID,Name,Description,Seller")] Item item)
        {
            //Debug to check where we are.
            Debug.WriteLine("We are in the Edit Method (Post)");

            //Check if model state is valid (if the object is actually formatted correctly)
            if (ModelState.IsValid)
            {
                //Update and save to database
                db.Entry(item).State = EntityState.Modified;
                db.SaveChanges();                            

                //Redirect to the Details page so you can see what all has been added.
                return RedirectToAction("Details", item.ID);
            }

            //If model is not correctly formatted do nothing...
            ViewBag.Seller = new SelectList(db.Sellers, "Name", "Name");
            return View(item);
        }

        [HttpGet]
        public ActionResult Delete(int? ID)
        {
            //Check if ID is null, if so redirect to list.
            if (ID == null)
            {
                Debug.WriteLine("The ID was null");
                return RedirectToAction("List");
            }

            //Get the item using Linq
            Item item = db.Items.Find(ID);

            //Check if the item is null, if so go back to the list.
            if (item == null)
            {
                Debug.WriteLine("The item was null.");
                return RedirectToAction("List");
            }

            //Return View with the item in it.
            return View(item);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Delete(int id)
        {
            Item item = db.Items.Find(id);
            foreach (Bid bid in item.Bids.ToList())
            {
                db.Bids.Remove(bid);
            }
            db.Items.Remove(item);
            db.SaveChanges();
            return RedirectToAction("List");
        }
    }
}
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using readygotravel.Abstract;
using readygotravel.Models;

namespace readygotravel.Controllers
{
    [Authorize]
    public class PeopleController : Controller
    {
        private DBContext db = new DBContext();
        private ITravelRepo repo;
        public PeopleController(ITravelRepo travelRepo)
        {
            repo = travelRepo;
        }

        /// <summary>
        /// This will return the Details view which is a view about the users profile.
        /// </summary>
        /// <returns>A Person object relating to the logged in user.</returns>
        public ActionResult Details()
        {
            //Since this will be accesed from homepage we can't pass a people ID as parameter so we use the UserId to get the id of people relating to the logged in user.
            string idTemp = User.Identity.GetUserId();
            int? id = db.People.Where(n => n.UserID.Equals(idTemp)).First().PersonID;

            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }

            Person person = db.People.Find(id);
            if (person == null)
            {
                return HttpNotFound();
            }

            //Check if the logged in user is an Admin or not.
            if (IsAdminUser(User.Identity))
            {
                ViewBag.Admin = 1;
            }
            else
            {
                ViewBag.Admin = 0;
            }
            return View(person);
        }

        /// <summary>
        /// Checks if logged in user is an admin.
        /// </summary>
        /// <param name="user">A user that will be check for admin status.</param>
        /// <returns>A boolean value.</returns>
        public bool IsAdminUser(System.Security.Principal.IIdentity user)
        {
            //Do the stuff
            ApplicationDbContext userDB = new ApplicationDbContext();
            var UserManager = new UserManager<ApplicationUser>(new UserStore<ApplicationUser>(userDB));

            //Get all of the roles associated with the user.
            var roles = UserManager.GetRoles(user.GetUserId());
            //If the first one is Admin, then the user is an admin.
            if (roles.Count != 0)
            {
                if (roles[0] == "Admin")
                {
                    return true;
                }
            }
            return false;
        }

        /// <summary>
        /// Use GET and return Edit view.
        /// </summary>
        /// <returns>A Person object relating to the user.</returns>
        public ActionResult Edit()
        {
            //Since this will be accesed from homepage we can't pass a people ID as parameter so we use the UserId to get the id of people relating to the logged in user.
            string idTemp = User.Identity.GetUserId();
            int? id = db.People.Where(n => n.UserID.Equals(idTemp)).First().PersonID;

            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Person person = db.People.Find(id);
            if (person == null)
            {
                return HttpNotFound();
            }
            return View(person);
        }

        /// <summary>
        /// Use POST and if the passed in Person object is valid then redirect to People/Details and if not return view with Person object.
        /// </summary>
        /// <param name="person">The Person object that will be edited in db.</param>
        /// <returns>Either a redirect to People/Details or a Edit View</returns>
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "PersonID,UserID,FirstName,LastName")] Person person)
        {
            if (ModelState.IsValid)
            {
                db.Entry(person).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Details", "People");
            }
            return View(person);
        }

    }
}

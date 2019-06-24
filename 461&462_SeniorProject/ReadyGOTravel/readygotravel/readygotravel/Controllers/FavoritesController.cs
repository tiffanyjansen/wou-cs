using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using Microsoft.AspNet.Identity;
using readygotravel.Abstract;
using readygotravel.Models;

namespace readygotravel.Controllers
{
    public class FavoritesController : Controller
    {
        private DBContext db = new DBContext();
        private ITravelRepo repo;
        public FavoritesController(ITravelRepo travelRepo)
        {
            repo = travelRepo;
        }

        // GET: Favorites
        [Authorize]
        public ActionResult Index()
        {
            //Get logged in users id.
            string id2 = User.Identity.GetUserId();

            //Get peopleID from UserId.
            int peopleID = repo.People.Where(n => n.UserID.Equals(id2)).Select(n => n.PersonID).First();

            //Get UserStates associated with the logged in user
            var userStates = repo.UserStates.Where(n=>n.PersonID == peopleID);
            return View(userStates.ToList());
        }


        /// <summary>
        /// Uses GET to Create a UserState.
        /// </summary>
        /// <param name="stateID">The ID of the state that will be added to the UserState table</param>
        /// <returns>The Create view.</returns>
        [Authorize]
        public ActionResult Create(int locationID)
        {
            ViewBag.locationID = locationID;
            ViewBag.locationName = GetLocationName(locationID);

            return View();
        }
        
        /// <summary>
        /// It will return the location name of the spedified location.
        /// </summary>
        /// <param name="locationID2">The ID of the location to return</param>
        /// <returns></returns>
        public string GetLocationName(int locationID)
        {
                return repo.Locations.Where(a => a.LocationID == locationID).First().Name;
        }

        /// <summary>
        /// Uses POST to Create a UserState.
        /// </summary>
        /// <returns>The Create view.</returns>
        [Authorize]
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create()
        {
            //Get logged in users id.
            string id2 = User.Identity.GetUserId();

            //Get peopleID from UserId.
            int peopleID = db.People.Where(n => n.UserID.Equals(id2)).Select(n => n.PersonID).First();//Get logged in users id.

            //Get location ID.
            int locationID = Convert.ToInt32(Request.QueryString["LocationID"]);

            //Check if state is already related to the user in the UserState table.
            bool isInFavorites = FavoriteOfUserCheck(peopleID, locationID);

            //Check if it is international.
            bool international = CheckIfInternational(locationID);

            //Make userState depending on if it is international.
            UserState userState;
            userState = new UserState { PersonID = peopleID, LocationID = locationID , International = international};

            if (ModelState.IsValid && !isInFavorites)
            {
                db.UserStates.Add(userState);
                db.SaveChanges();
                return RedirectToAction("Index", "Home");
            }

            
            return View(userState);
        }

        /// <summary>
        /// This will check if a locations is international or not.
        /// </summary>
        /// <param name="locationID">The id of the location that will be checked.</param>
        /// <returns>True or False depending if the location is international.</returns>
        public Boolean CheckIfInternational(int locationID)
        {
            int international = repo.Locations.Where(n => n.LocationID == locationID).FirstOrDefault().CountryID;
            if (international == 1)
                return false;
            else
                return true;
        }

        /// <summary>
        /// This will check if a UserState Has an entry with a specified PersonID and CityID/StateID.
        /// </summary>
        /// <param name="PersonId">The ID of the person associated with the UserState.</param>
        /// <param name="locationID">The ID of the location associated with the UserState.</param>
        /// <param name="international">A boolean determind if we should look for a US State or International City.</param>
        /// <returns></returns>
        public Boolean FavoriteOfUserCheck(int PersonId, int locationID)
        {
            return repo.UserStates.Where(n => n.PersonID == PersonId).Select(n => n.LocationID).Contains(locationID);
        }


        /// <summary>
        /// Use GET to delete a UserState.
        /// </summary>
        /// <param name="id">The ID of the UserState.</param>
        /// <returns>The Delete View. </returns>
        [Authorize]
        public ActionResult Delete(int? id)
        {
            //Get logged in users id.
            string id2 = User.Identity.GetUserId();

            //Get peopleID from UserId.
            int peopleID = repo.People.Where(n => n.UserID.Equals(id2)).Select(n => n.PersonID).First();

            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            UserState userState = repo.UserStates.Where(n=>n.UserStateID == id).First();

            if (userState == null)
            {
                return HttpNotFound();
            }
            //Checks if the logged in user is not the owner of the UserState and redirects if not.
            else if(!UserStateHasPersonId(userState, peopleID))
            {
                return RedirectToAction("Index", "Home");
            }
            return View(userState);
        }

        /// <summary>
        /// Use POST to delete a UserState.
        /// </summary>
        /// <param name="id">The ID of the UserState.</param>
        /// <returns>A redirect to Favorites/Index.</returns>
        [Authorize]
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            //Get logged in users id.
            string id2 = User.Identity.GetUserId();

            //Get peopleID from UserId.
            int peopleID = repo.People.Where(n => n.UserID.Equals(id2)).Select(n => n.PersonID).First();

            UserState userState = db.UserStates.Where(n => n.UserStateID == id).First();

            //Check if logged in user is the same as the PersonID from the UserState that will be deleted. If not then it won't delete it.
            if (UserStateHasPersonId(userState, peopleID))
            {
                db.UserStates.Remove(userState);
                db.SaveChanges();
            }
            return RedirectToAction("Index");
        }



        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        /// <summary>
        /// Checks if a UserState has the personID that was passed to it.
        /// </summary>
        /// <param name="userState">The userState that will be checked if it has a personID.</param>
        /// <param name="personID">The ID of a person associated with a userState.</param>
        /// <returns></returns>
        private Boolean UserStateHasPersonId(UserState userState, int personID)
        {
            if (userState.PersonID == personID)
                return true;
            else
                return false;
        }

    }
}

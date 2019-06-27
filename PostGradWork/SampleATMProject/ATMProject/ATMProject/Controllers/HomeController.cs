using ATMProject.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;
using System.Web;
using System.Web.Mvc;

namespace ATMProject.Controllers
{
    public class HomeController : Controller
    {
        DataContext db = new DataContext();

        [HttpGet]
        public ActionResult Index()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Index(int button, string pin)
        {
            //If the button selected was generate a new pin.
            if (button == 1)
            {
                return RedirectToAction("Generate");
            }

            //Otherwise, check if the pin is valid, and then redirect to page to withdraw/deposit money.
            else
            {
                //Check if the pin entered is valid, if not let the user know and try agin.
                string accept = CheckPin(pin);
                if (!bool.TryParse(accept, out bool accepted))
                {
                    ViewBag.Error = accept;
                    return View();
                }

                //Checks if the pin is attatched to a user in the database, if so redirect to page to deal with the money.
                if(db.Users.Where(u => u.PIN == pin).Select(u => u).FirstOrDefault() != null)
                {
                    return RedirectToAction("");
                }

                //If the user is not in the database, let the user know that the pin is not recognized and to try again.
                else
                {
                    ViewBag.Error = "The PIN you entered was not recognized, please try again.";
                    return View();
                }
            }
        }

        /// <summary>
        /// This method checks to see if the PIN is valid. That is, if it's long enough and only numbers. 
        /// If it's not valid, it returns the appropriate error message so the user knows what they did wrong.
        /// If it is valid, it returns true.
        /// </summary>
        /// <param name="pin">The PIN we are checking</param>
        /// <returns>True if valid, error message otherwise</returns>
        private string CheckPin(string pin)
        {
            //Check if the pin is the correct length
            if (pin.Length > 8 || pin.Length < 4)
            {
                return "The PIN you inputted was either too long or too short, please try again.";                
            }

            //Note: I had to use a regular expression for the pins that start with '0'
            Regex rx = new Regex(@"^\d{4,8}");

            //Check if the pin is all numbers
            if (!rx.IsMatch(pin))
            {
                return "The PIN you inputted was not accepted, please try again.";
            }

            else
            {
                return "true";
            }
        }

        [HttpGet]
        public ActionResult Generate()
        {
            //generate the PIN for the new user.
            string pin = GeneratePin();

            //Create the user and add them to the database.
            User newUser = new User { PIN = pin, CheckingAmount = 0, SavingsAmount = 0 };
            db.Users.Add(newUser);
            db.SaveChanges();

            return View(newUser);
        }
        
        /// <summary>
        /// This method generates the pin to be used by the new user.
        /// </summary>
        /// <returns>The user's new pin</returns>
        private string GeneratePin()
        {
            //Create a random to create random pin numbers
            Random rand = new Random();

            //Decide what the length of the pin is going to be (4 to 8)
            int pinLength = rand.Next(4, 9);

            //Create the pin using the random number generator.
            string pin = "";
            for(int i = 0; i < pinLength; i++)
            {
                int pinDigit = rand.Next(0, 10);
                pin += pinDigit;
            }

            //If the pin already exists, try again.
            if (db.Users.Where(u => u.PIN == pin).Select(u => u).FirstOrDefault() != null)
            {
                return GeneratePin();
            }

            //Return the pin if it's valid.
            return pin;
        }
    }
}
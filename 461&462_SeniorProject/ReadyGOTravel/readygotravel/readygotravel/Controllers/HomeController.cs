using readygotravel.Abstract;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Net;
using System.Net.Mail;
using System.Web;
using System.Web.Mvc;

namespace readygotravel.Controllers
{
    public class HomeController : Controller
    {
        //database access fields
        private ITravelRepo repo;
        public HomeController(ITravelRepo travelRepo)
        {
            repo = travelRepo;
        }

        /// <summary>
        /// Homepage view
        /// </summary>
        /// <returns></returns>
        public ActionResult Index()
        {
            return View();
        }

        /// <summary>
        /// About us view page
        /// </summary>
        /// <returns></returns>
        public ActionResult About()
        {
            ViewBag.Message = "About Us";

            return View();
        }

        /// <summary>
        /// Contact us view page
        /// </summary>
        /// <param name="success"></param>
        /// <returns>Whether the message successfully sent or not</returns>
        [HttpGet]
        public ActionResult Contact(bool? success)
        {
            
             if (success == true)
            {
                ViewBag.submitMessage = "Message successfully sent.";
                ViewBag.Success = true;
            }
            else if (success == false)
            {
                ViewBag.submitMessage = "Failed to send message.";
                ViewBag.Success = false;
            }
            else
            {
                ViewBag.Success = null;
            }

            ViewBag.Message = "Contact Us";
            

            return View();
        }

        /// <summary>
        /// The style guide for the webpage, includes most css styling
        /// </summary>
        /// <returns></returns>
        public ActionResult StyleGuide()
        {
            if(new PeopleController(repo).IsAdminUser(User.Identity))
            {
                ViewBag.Message = "The style guide for the entire webpage";
                return View();
            }
            else
            {
                return RedirectToAction("Index");
            }
        }

        public ActionResult Mission()
        {

            return View();
        }

        /// <summary>
        /// This wil send the infomation received to a specified email.
        /// </summary>
        /// <param name="name">Name of person which will be included in email.</param>
        /// <param name="email">Email of person which will be included in email.</param>
        /// <param name="ownAccount">Check if person has an account which will be included in email. </param>
        /// <param name="catagory">Catagory of problem which will be included in email.</param>
        /// <param name="description">Description of problem which will be included in email.</param>
        /// <returns>It will redirect back to the homepage.</returns>
        [HttpPost]
        public ActionResult Contact(string name, string email, bool ownAccount, string catagory, string description)
        {
            var myTimeZone = TimeZoneInfo.FindSystemTimeZoneById("Pacific Standard Time");
            var currentDateTime = TimeZoneInfo.ConvertTimeFromUtc(DateTime.UtcNow, myTimeZone);

            //Setup email message string.
            string emailMessage = "This is an alert sent from ReadyGO-Travel website Notifying you of a complaint that was filled out. Here is the information given:"+"\n Timestamp:" + currentDateTime.ToString() +"\n FullName: " + name + "\n Email: " + email + "\n Has an Account: ";
            if (ownAccount)
                emailMessage += "yes";
            else
                emailMessage += "no";
            emailMessage += "\n Catagory: " + catagory + "\n Description: " + description;
            Debug.WriteLine(emailMessage);

            
            //This part gets the hidden infomation to be used for sending/reciving the email.
            string senderEmail = System.Web.Configuration.WebConfigurationManager.AppSettings["senderEmail"];
            string senderPassword = System.Web.Configuration.WebConfigurationManager.AppSettings["senderPass"];
            string receiverEmail = System.Web.Configuration.WebConfigurationManager.AppSettings["receiverEmail"];
            
            //Setting up the Email message with infomation needed.
            MailMessage Message = new MailMessage();
            Message.From = new MailAddress(senderEmail);
            Message.To.Add(receiverEmail);
            Message.Subject = "ReadyGo-Travel Form Alert";
            Message.Body = emailMessage;

            //Setting up and sending the Email message.
            SmtpClient client = new SmtpClient
            {
                
                UseDefaultCredentials = false,
                Host = "smtp.gmail.com",
                Port = 587,
                EnableSsl = true,
                DeliveryMethod = SmtpDeliveryMethod.Network,
                Credentials = new NetworkCredential(senderEmail, senderPassword),
                Timeout = 20000
            };
            try
            {
                client.Send(Message);
                Debug.Write("Mail successfully sent.");
                return RedirectToAction("Contact", "Home", new { success = true });
            }
            catch (Exception)
            {
                Debug.Write("Fail Has error.");
                return RedirectToAction("Contact", "Home", new { success = false });
            }
            finally
            {
                Message.Dispose();
            }
            

        }
    }
}
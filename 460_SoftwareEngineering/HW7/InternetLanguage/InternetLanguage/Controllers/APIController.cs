using InternetLanguage.DAL;
using InternetLanguage.Models;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;

namespace InternetLanguage.Controllers
{
    public class APIController : Controller
    {
        private RequestsContext db = new RequestsContext();

        // GET: API
        public JsonResult Sentence(string word)
        {
            string key = System.Configuration.ConfigurationManager.AppSettings["GiphyKey"];

            Debug.WriteLine("word = " + word);
            Debug.WriteLine("Key = " + key);

            string website = "https://api.giphy.com/v1/stickers/translate?api_key=" + key + "&s=" + word;

            WebRequest request = WebRequest.Create(website);
            request.ContentType = "application/json; charset=utf-8";
            var response = (HttpWebResponse)request.GetResponse();
            string words;
            using (var stream = new StreamReader(response.GetResponseStream()))
            {
                words = stream.ReadToEnd();
            };

            var obj = JObject.Parse(words);
            string data = (string)obj["data"]["embed_url"];

            //Adds stuff to the database.
            AddToDatabase(data);

            return Json(data, JsonRequestBehavior.AllowGet);
        }

        private void AddToDatabase(string data)
        {
            Request request = new Request
            {
                IPAddress = Request.UserHostAddress,
                DateOfRequest = DateTime.Now,
                Browser = Request.Browser.Type,
                SpecialSite = (string)data
            };

            Debug.WriteLine("IPAddress = " + request.IPAddress);
            Debug.WriteLine("Browser = " + request.Browser);
            Debug.WriteLine("DateOfRequest = " + request.DateOfRequest);
            Debug.WriteLine("SpecialSite = " + request.SpecialSite);
            db.Requests.Add(request);
            try
            {
                db.SaveChanges();
                Debug.WriteLine("Changes were saved");
            }
            //Got a bunch of errors so this was how i figured out how to fix them.
            catch (System.Data.Entity.Validation.DbEntityValidationException dbEx)
            {
                Exception raise = dbEx;
                foreach (var validationErrors in dbEx.EntityValidationErrors)
                {
                    foreach (var validationError in validationErrors.ValidationErrors)
                    {
                        string message = string.Format("{0}:{1}",
                            validationErrors.Entry.Entity.ToString(),
                           validationError.ErrorMessage);

                        Debug.WriteLine(message);
                    }
                }
            }
        }
    }
}
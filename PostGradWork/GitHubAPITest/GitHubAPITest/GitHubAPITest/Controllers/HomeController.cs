
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using GitHubAPITest.Models;
using Newtonsoft.Json.Linq;

namespace GitHubAPITest.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            User user = GetUserData();
            List<Repository> repos = GetRepoData();
            user.Repositories = repos;

            return View(user);
        }

        /// <summary>
        /// Get the User from the GitHub API
        /// </summary>
        /// <returns>The User</returns>
        private User GetUserData()
        {
            string key = System.Web.Configuration.WebConfigurationManager.AppSettings["GitHubAPIKey"];
            var json = SendRequest("https://api.github.com/user", key, "tiffanyjansen");

            User user = new User(json); //create User using JSON data

            return user;
        }

        /// <summary>
        /// Get all the Repos for the User using the GitHubAPI
        /// </summary>
        /// <returns>The List of Repositories</returns>
        private List<Repository> GetRepoData()
        {
            string key = System.Web.Configuration.WebConfigurationManager.AppSettings["GitHubAPIKey"];
            var json = SendRequest("https://api.github.com/user/repos?sort=updated", key, "tiffanyjansen");

            JArray jsonArray = JArray.Parse(json);

            List<Repository> repositories = new List<Repository>();
            foreach (var data in jsonArray)
            {
                Repository repo = new Repository(data);
                repositories.Add(repo);
            }

            return repositories;
        }

        private string SendRequest(string uri, string credentials, string username)
        {
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(uri);
            request.Headers.Add("Authorization", "token " + credentials);
            request.UserAgent = username;       // Required, see: https://developer.github.com/v3/#user-agent-required
            request.Accept = "application/json";

            string jsonString = null;
            // TODO: You should handle exceptions here
            using (WebResponse response = request.GetResponse())
            {
                Stream stream = response.GetResponseStream();
                StreamReader reader = new StreamReader(stream);
                jsonString = reader.ReadToEnd();
                reader.Close();
                stream.Close();
            }
            return jsonString;
        }
    }
}

using System;
using System.Collections.Generic;
using System.Diagnostics;
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
        private static readonly string _key = System.Web.Configuration.WebConfigurationManager.AppSettings["GitHubAPIKey"];
        private static readonly string _username = "tiffanyjansen";

        /// <summary>
        /// The Index method
        /// </summary>
        /// <returns>The View</returns>
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
            var json = SendRequest("https://api.github.com/user", _key, _username);
            User user = new User(json); //create User using JSON data
            return user;
        }

        /// <summary>
        /// Get all the Repos for the User using the GitHubAPI
        /// </summary>
        /// <returns>The List of Repositories</returns>
        private List<Repository> GetRepoData()
        {
            var json = SendRequest("https://api.github.com/user/repos?sort=updated", _key, _username);
            JArray jsonArray = JArray.Parse(json);

            List<Repository> repositories = new List<Repository>();
            foreach (var data in jsonArray)
            {
                Repository repo = new Repository(data);
                repositories.Add(repo);
            }

            return repositories;
        }

        /// <summary>
        /// Copied from Scot
        /// </summary>
        /// <param name="uri">The Url</param>
        /// <param name="credentials">The API Key</param>
        /// <param name="username">The github username</param>
        /// <returns>The JSON of the github data</returns>
        private string SendRequest(string uri, string credentials, string username)
        {
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(uri);
            request.Headers.Add("Authorization", "token " + credentials);
            request.UserAgent = username;       // Required, see: https://developer.github.com/v3/#user-agent-required
            request.Accept = "application/json";

            string jsonString = null;
            try
            {
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
            catch (Exception)
            {
                return "";
            }            
        }

        /// <summary>
        /// Get all the Commits for the given repo
        /// </summary>
        /// <param name="user">owner of the repo</param>
        /// <param name="repo">repo name</param>
        /// <returns>json array of commits</returns>
        public JsonResult Commits(string user, string repo)
        {
            var json = SendRequest($"https://api.github.com/repos/{user}/{repo}/commits?per_page={int.MaxValue}", _key, _username);
            JArray jsonArray = JArray.Parse(json);
            List<object> commits = new List<object>();
            
            foreach (var data in jsonArray)
            {
                var commit = new
                {
                    sha = ((string)data["sha"]).Substring(0, 8),
                    sha_link = (string)data["html_url"],
                    committer_name = (string)data["commit"]["committer"]["name"],
                    timestamp = ((DateTime)data["commit"]["committer"]["date"]).ToString("yyyy-MM-ddTHH:mm:ssZ"),
                    message = (string)data["commit"]["message"],
                };
                commits.Add(commit);
            }

            return Json(commits, JsonRequestBehavior.AllowGet);
        }
    }
}
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GitHubAPITest.Models
{
    public class User
    {
        public User(string jsonData)
        {
            JObject user = JObject.Parse(jsonData);
            UserName = user.Value<string>("login");
            Name = user.Value<string>("name");
            Email = user.Value<string>("email");
            ImageLink = user.Value<string>("avatar_url");
            Bio = user.Value<string>("bio");
            Followers = user.Value<int>("followers");
            Following = user.Value<int>("following");
        }

        public string Name { get; set; }

        public string UserName { get; set; }

        public string Email { get; set; }

        public string ImageLink { get; set; }

        public string Bio { get; set; }

        public int Followers { get; set; }

        public int Following { get; set; }

        public List<Repository> Repositories { get; set; }
    }
}
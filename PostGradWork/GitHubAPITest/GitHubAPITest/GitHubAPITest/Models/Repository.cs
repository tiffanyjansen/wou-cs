using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GitHubAPITest.Models
{
    public class Repository
    {
        public Repository(JToken jsonData)
        {
            RepoName = jsonData.Value<string>("name");
            RepoLink = jsonData.Value<string>("html_url");
            LastUpdated = jsonData.Value<DateTime>("updated_at");
            CreatedAt = jsonData.Value<DateTime>("created_at");
            Size = jsonData.Value<int>("size");
            Description = jsonData.Value<string>("description");
            Private = jsonData.Value<bool>("private");

            var owner = jsonData.Value<JToken>("owner");
            OwnerName = owner.Value<string>("login");
            OwnerImage = owner.Value<string>("avatar_url");           
        }

        public string RepoName { get; set; }

        public string RepoLink { get; set; }

        public string Description { get; set; }

        public bool Private { get; set; }

        public string OwnerName { get; set; }

        public string OwnerImage { get; set; }

        public DateTime LastUpdated { get; set; }

        public int Size { get; set; }

        public DateTime CreatedAt { get; set; }
    }
}
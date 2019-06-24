﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Routing;

namespace readygotravel
{
    public class RouteConfig
    {
        public static void RegisterRoutes(RouteCollection routes)
        {
            routes.IgnoreRoute("{resource}.axd/{*pathInfo}");

            routes.MapRoute(
                name: "Default",
                url: "{controller}/{action}/{id}",
                defaults: new { controller = "Home", action = "Index", id = UrlParameter.Optional }
            );
 
            routes.MapRoute(
                name: "Javascript",
                url: "{controller}/{action}/{value}",
                defaults: new { controller = "Searches", action = "Regions" }
            );

            routes.MapRoute(
              name: "CatchAll",
              url: "{*url}",
              defaults: new { controller = "Error", action = "Error404" }
          );
        }
    }
}

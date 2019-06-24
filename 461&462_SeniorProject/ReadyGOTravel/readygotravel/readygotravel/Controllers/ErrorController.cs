using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace readygotravel.Controllers
{
    public class ErrorController : Controller
    {
        // GET: Error 400
        /// <summary>
        /// View Function for returning a custom 400 as necessary
        /// </summary>
        /// <returns></returns>
        public ActionResult Error400()
        {
            HttpContext.Response.StatusCode = 400;
            HttpContext.Response.TrySkipIisCustomErrors = true;

            return View();
        }
        // GET: Error 404
        /// <summary>
        /// View Function for returning a custom 404 as necessary
        /// </summary>
        /// <returns></returns>
        public ActionResult Error404()
        {
            HttpContext.Response.StatusCode = 404;
            HttpContext.Response.TrySkipIisCustomErrors = true;

            return View();
        }

        // GET: Error 500
        /// <summary>
        /// View Function for returning a custom 500 as necessary
        /// </summary>
        /// <returns></returns>
        public ActionResult Error500()
        {
            HttpContext.Response.StatusCode = 500;
            HttpContext.Response.TrySkipIisCustomErrors = true;

            return View();
        }

        // GET: Error Generic
        /// <summary>
        /// View Function for returning a custom Error Page for all other issues
        /// </summary>
        /// <returns></returns>
        public ActionResult Error()
        {
            HttpContext.Response.TrySkipIisCustomErrors = true;

            return View();
        }
    }
}
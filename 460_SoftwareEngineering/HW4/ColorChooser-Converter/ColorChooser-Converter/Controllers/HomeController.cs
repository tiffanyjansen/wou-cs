using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Text.RegularExpressions;

namespace ColorChooser_Converter.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            return View();
        }
        public ActionResult Converter()
        {
            ViewBag.Message = "Convert Miles to Metric";

            //Get Query Strings
            string miles = Request.QueryString["miles"];
            string unit = Request.QueryString["unit"];

            //Find answer, hopefully...
            ViewBag.Answer = calculateAnswer(miles, unit);

            return View();
        }
        public string calculateAnswer(string miles, string unit)
        {
            if (miles == null || miles == "")
            {
                return "";
            }
            //Do converstion.
            double milesNum = double.Parse(miles);
            double kiloNum = milesNum * 1.60934;
            double meterNum = kiloNum * 1000;
            double centiNum = meterNum * 100;
            double milliNum = centiNum * 10;
            if(unit == "kilometers")
            {
                return miles + " miles is equal to " + kiloNum + " " + unit;
            }
            else if (unit == "meters")
            {
                return miles + " miles is equal to " + meterNum + " " + unit;
            }
            else if (unit == "centimeters")
            {
                return miles + " miles is equal to " + centiNum + " " + unit;
            }
            else if (unit == "millimeters")
            {
                return miles + " miles is equal to " + milliNum + " " + unit;
            }
            else
            {
                return "";
            }
        }
    }
}
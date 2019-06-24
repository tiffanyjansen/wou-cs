using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

//PBI 269 - Stacia Unit Test
namespace rgt_testing
{
    [TestClass]
    class StaciaTests
    {
        [TestMethod]

        //this is the method I am testing
        private int GetHotelStarValue(string HotelStarValue)
        {
            //Split the string into pieces
            string[] stars = HotelStarValue.Split('-');
            string last = stars.Last();
            //If the user selected a number sequence convert the number to an int
            if (int.TryParse(last, out int n))
            {
                return Convert.ToInt32(last);
            }
            //Else return 0
            else
            {
                return 0;
            }
        }
    }
}

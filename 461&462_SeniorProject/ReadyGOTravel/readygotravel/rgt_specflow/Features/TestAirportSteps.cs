using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.Support.UI;
using SeleniumExtras.WaitHelpers;
using System;
using TechTalk.SpecFlow;
using ExpectedConditions = OpenQA.Selenium.Support.UI.ExpectedConditions;

//Adrian Sprint 5 Test - PBI 388
namespace rgt_specflow
{
    [Binding]
    public class TestAirport : IDisposable
    {
        //Create driver to run chrome automatically
        private ChromeDriver driver;

        public TestAirport()
        {
            driver = new ChromeDriver();
        }

        [Given(@"I have navigated to the ReadyGOTravel website")]
        public void GivenIHaveNavigatedToTheReadyGOTravelWebsite()
        {
            //Navigate to Page, confirm correct location
            driver.Navigate().GoToUrl("http://readygotravel.azurewebsites.net/");
            Assert.IsTrue(driver.Title.Contains("ReadyGO"));
        }

        [Given(@"I have moved to the search page")]
        public void GivenIHaveMovedToTheSearchPage()
        {
            //Navigate to Page, confirm correct location
            System.Threading.Thread.Sleep(2000);
            driver.FindElement(By.XPath("/html/body/div[2]/div/div/div[1]/div/div[1]/div[2]/button")).Click();
            //Confrim on the correct page
            Assert.IsTrue(driver.Title.Contains("Find"));
        }

        [When(@"I select a different country and city of said country")]
        public void WhenISelectADifferentCountryAndCityOfSaidCountry()
        {
            System.Threading.Thread.Sleep(2000);
            //Find "Continent drop down
            var selectContinent = driver.FindElement(By.XPath("//*[@id='StartContinent']"));
            var continent = new SelectElement(selectContinent);
            continent.SelectByText("Europe");

            System.Threading.Thread.Sleep(2000);
            //Find "Country" drop down
            var selectCountry = driver.FindElement(By.XPath("//*[@id='StartCountry']"));
            //Make and object to select correct drop down item
            var country = new SelectElement(selectCountry);
            country.SelectByText("France");

            System.Threading.Thread.Sleep(2000);
            //Wait for Javascript
            var wait = new WebDriverWait(driver, TimeSpan.FromSeconds(50));
            wait.Until(ExpectedConditions.ElementIsVisible(By.XPath("//*[@id='StartCity']")));
            //Find "City" drop down
            var selectCity = driver.FindElement(By.XPath("//*[@id='StartCity']"));
            //Make and object to select correct drop down item
            var city = new SelectElement(selectCity);
            city.SelectByText("Paris");

            System.Threading.Thread.Sleep(2000);
            var wait2 = new WebDriverWait(driver, TimeSpan.FromSeconds(50));
            //Wait for Javascript airport info
            wait.Until(ExpectedConditions.ElementIsVisible(By.Id("StartAirport")));

        }

        [Then(@"I should be presented with the main airport for that city")]
        public void ThenIShouldBePresentedWithTheMainAirportForThatCity()
        {
            //Find "Airport" drop down
            var confirmAir = driver.FindElement(By.Id("StartAirport"));
            //Change location to string
            var test = confirmAir.Text.ToString();
            //Confirm correct Airport Code
            Assert.AreEqual(test, "CDG");
        }

        public void Dispose()
        {
            if (driver != null)
            {
                driver.Dispose();
                driver = null;
            }
        }
    }
}

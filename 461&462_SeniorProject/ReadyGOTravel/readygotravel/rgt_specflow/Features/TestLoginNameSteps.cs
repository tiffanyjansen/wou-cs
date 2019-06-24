using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.Support.UI;
using SeleniumExtras.WaitHelpers;
using System;
using TechTalk.SpecFlow;

//Adrian Sprint 5 Test - PBI 375
namespace rgt_specflow
{
    [Binding]
    public class TestLoginNameSteps: IDisposable
    {
        //Create driver to run chrome automatically
        private ChromeDriver driver;

        public TestLoginNameSteps()
        {
            driver = new ChromeDriver();
        }

        [Given(@"I have clicked on log in")]
        public void GivenIHaveClickedOnLogIn()
        {
            //Navigate to Page, confirm correct location
            driver.Navigate().GoToUrl("http://readygotravel.azurewebsites.net/Account/Login");
            Assert.IsTrue(driver.Title.Contains("Log in"));
        }
        
        [Given(@"I have entered my login creditials")]
        public void GivenIHaveEnteredMyLoginCreditials()
        {
            //Wait for page load
            System.Threading.Thread.Sleep(1000);
            //Find login field
            IWebElement name = driver.FindElement(By.XPath("//*[@id='Email']"));
            //Clear it to be safe
            name.Clear();
            //Enter Login Email for Account
            name.SendKeys("Test@wou.edu");

            System.Threading.Thread.Sleep(2000);
            //Find password field
            IWebElement password = driver.FindElement(By.Id("Password"));
            //Enter Password
            password.SendKeys("Test123!");

            System.Threading.Thread.Sleep(2000);
            //Click "login" button
            driver.FindElement(By.XPath("//*[@id='loginForm']/form/div[4]/div/input")).Click();

        }
        
        [When(@"I move to the Top Locations Page")]
        public void WhenIMoveToTheTopLocationsPage()
        {
            //Navigate to "Top Locations" Page
            driver.FindElement(By.XPath("//*[@id='Preview']")).Click();
            System.Threading.Thread.Sleep(1000);
            driver.FindElement(By.XPath("/html/body/div[1]/nav/ul[1]/li[2]/div/a[1]")).Click();
            System.Threading.Thread.Sleep(2000);
            //Confirm Correct Location
            Assert.IsTrue(driver.Title.Contains("Top"));
        }
        
        [Then(@"the result should show my name as a greating on the page")]
        public void ThenTheResultShouldShowMyNameAsAGreatingOnThePage()
        {
            System.Threading.Thread.Sleep(2000);
            //Find the Welcome Locations and Name info
            var firstName = driver.FindElement(By.XPath("/html/body/div[2]/div/div[1]/div/h2"));
            //Turn this into a string
            var confirm = firstName.Text.ToString().ToUpper();
            //A confirmation string
            var test = "WELCOME TEST!";
            //Assert that they are the same
            Assert.AreEqual(test, confirm);
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

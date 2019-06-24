using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.Owin;
using Owin;
using readygotravel.Models;

[assembly: OwinStartupAttribute(typeof(readygotravel.Startup))]
namespace readygotravel
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
            CreateAdmin();
        }
        //Here the "Admin" role will be created and a user will be created as the Admin.
        private void CreateAdmin()
        {
            ApplicationDbContext context = new ApplicationDbContext();
            DBContext db = new DBContext();

            var roleManager = new RoleManager<IdentityRole>(new RoleStore<IdentityRole>(context));
            var UserManager = new UserManager<ApplicationUser>(new UserStore<ApplicationUser>(context));

            //Check if Admin role already exists
            if (!roleManager.RoleExists("Admin"))
            {
                //Create the Admin Role
                var role = new IdentityRole();
                role.Name = "Admin";
                roleManager.Create(role);

                //Create the User that will become the Admin
                var user = new ApplicationUser();
                user.Email = "tjans10@gmail.com";
                user.UserName = user.Email;

                string password = "123Pass#"; //This is changed each deployment, as the password is in the code.

                //The user to check for adding the role.
                var checkUser = UserManager.Create(user, password);

                //Add the User to the People table so we can still track everything.
                string name = "Tiffany";
                string lastName = "Jansen";
                db.People.Add(new Person { FirstName = name, LastName = lastName, UserID = user.Id});
                db.SaveChanges();

                //Add default User to Role Admin   
                if (checkUser.Succeeded)
                {
                    var result1 = UserManager.AddToRole(user.Id, "Admin");
                }
            }
        }
    }
}

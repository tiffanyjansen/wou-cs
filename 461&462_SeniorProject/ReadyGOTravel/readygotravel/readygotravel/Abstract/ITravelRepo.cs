using readygotravel.Models;
using System.Collections.Generic;

namespace readygotravel.Abstract
{
    public interface ITravelRepo
    {
        IEnumerable<Airport> Airports { get; }
        IEnumerable<Climate> Climates { get; }
        IEnumerable<Continent> Continents { get; }
        IEnumerable<Country> Countries { get; }
        IEnumerable<Currency> Currencies { get; }
        IEnumerable<Geography> Geographies { get; }
        IEnumerable<Location> Locations { get; }
        IEnumerable<Person> People { get; }
        IEnumerable<Result> Results { get; }
        IEnumerable<Search> Searches { get; }
        IEnumerable<State> States { get; }
        IEnumerable<UserState> UserStates { get; }
        IEnumerable<Weather> Weather { get; }
    }
}

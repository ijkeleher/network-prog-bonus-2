public class DisplayStats
{
    private String cash = "no data";
    private String cars = "no data";

    public void setCash(long cash)
    {
        this.cash = Long.toString(cash);
    }

    public void setCars(long cars)
    {
        this.cars = Long.toString(cars);
    }

    public String getCash()
    {
        return cash;
    }

    public String getCars()
    {
        return cars;
    }
}

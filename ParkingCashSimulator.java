import java.util.ArrayList;

public class ParkingCashSimulator
{
    private DisplayStats displayStats;

    public ParkingCashSimulator(ArrayList<Slot> slots)
    {
        /**
         * Setting the packing car simulator
         *
         * The number of threads is based on the number of cores present.
         *
         * For example, a quad core processor will produce 8 threads
         */
        ParkingCash parkingCash = new ParkingCash();
        ParkingStats parkingStats = new ParkingStats(parkingCash);

        displayStats = new DisplayStats();

        System.out.println("Parking Simulator\n");

        int numberOfSensors = 10;
        Thread [] threads = new Thread[numberOfSensors];

        for(int i = 0; i < numberOfSensors; i++)
        {
            Sensor sensor = new Sensor(parkingStats, slots.get(i));
            Thread thread = new Thread(sensor);
            thread.start();
            threads[i] = thread;
        }

        /**
         * Waits for all the sensors to end
         */
        for(int i=0; i < numberOfSensors; i++)
        {
            try {
                threads[i].join();
            }
            catch (InterruptedException e)
            { }
        }

        /**
        * The results should show the number of cars to be zero and the
        * amount collected should be 2020
        *
        * If the number of cars is not zero and the amount collected is
        * not 2020 then data inconsistency is present
        */
        displayStats.setCars(parkingStats.numberOfCars);
        displayStats.setCash(parkingCash.cash);
    }

    public DisplayStats getDisplay()
    {
        return displayStats;
    }

    private class ParkingCash
    {
        /**
         * ParkingCash is responsible for initialising the cost and cash as well as collecting
         * the cash when the car pays. Finally the print method show how much money was
         * made.
         */
        private static final int COST = 2;
        private long cash;

        public ParkingCash()
        {
            cash = 0;
        }

        public void vehiclePay()
        {
            cash = cash + COST;
        }

        public void print()
        {
            System.out.println("Closing Account");
            long totalAmount;
            totalAmount = cash;
            cash = 0;
            System.out.println("The total amount is " + totalAmount);
        }
    }

    private class ParkingStats
    {
        /**
         * ParkingStats is responsible for recording a car coming into the packing
         * lot and then when a car leaves, the car pays. The ParkingCash object pays
         * for the car.
         */
        private long numberOfCars;

        private ParkingCash parkingCash;

        public ParkingStats(ParkingCash parkingCash)
        {
            numberOfCars = 0;
            this.parkingCash = parkingCash;
        }

        public void carComeIn()
        {
            numberOfCars++;
        }

        public void carComeOut()
        {
            numberOfCars--;
            parkingCash.vehiclePay();
        }
    }

    private class Sensor implements Runnable
    {
        /**
         * Each sensor will simulate three coming in and three cars coming
         *
         * Each sensor will sleep for a period of time, where another thread
         * might have the opportunity to modify another thread data.
         *
         * If this happens then data inconsistency will occur.
         */
        private ParkingStats parkingStats;
        private Slot slot;

        public Sensor(ParkingStats parkingStats, Slot slot)
        {
            this.parkingStats = parkingStats;
            this.slot = slot;
        }

        public void run()
        {
            int numberOfCycles = 100;

            while(numberOfCycles >= 0) {

                parkingStats.carComeIn();

                slot.carComeIn();

                sleep((long) (Math.random() * 250));

                parkingStats.carComeOut();

                slot.carComeOut();

                sleep((long) (Math.random() * 250));
                numberOfCycles--;
            }
        }
    }

    private void sleep(long ms)
    {
        try
        {
            Thread.sleep(ms); // Sleeps are in milliseconds
        }
        catch (InterruptedException e)
        {

        }
    }
}

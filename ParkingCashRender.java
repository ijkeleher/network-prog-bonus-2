import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;

public class ParkingCashRender
{
    public final static int WIDTH = 720;
    public final static int HEIGHT = 640;

    private Scene scene;
    private GraphicsContext graphicsContext;
    private ParkingCashSimulator unsafeParkingCashSimulator;

    private ArrayList<Slot> slots;

    private volatile boolean running;

    public ParkingCashRender(Scene scene, GraphicsContext graphicsContext)
    {
        this.scene = scene;
        this.graphicsContext = graphicsContext;

        init();

        startSimulation();
    }

    private void init()
    {
        if(slots == null)
            slots = new ArrayList<>();

        for(int i = 0; i <= 4; i++)
            slots.add(new Slot(graphicsContext, 20, 120 * i + 20));

        for(int i = 0; i <= 4; i++)
            slots.add(new Slot(graphicsContext, 240, 120 * i + 20));
    }

    private void startSimulation()
    {
        new Thread(() -> unsafeParkingCashSimulator = new ParkingCashSimulator(slots)).start();
    }

    public void run()
    {
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        running = true;

        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(16.66), event -> { // Sleeps for approximately 1/60 of a second

            update();
            render();
        });

        gameLoop.getKeyFrames().add(keyFrame);
        gameLoop.play();
    }

    private void update()
    {

    }

    private void render()
    {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0,0, WIDTH, HEIGHT);

        if(slots != null)
        {
            for(Slot slot : slots)
                slot.draw();
        }

        graphicsContext.setFill(Color.BLUE);

        graphicsContext.fillText("End of Day Parking Stats", 520, 40);

        if(unsafeParkingCashSimulator != null)
        {
            if(unsafeParkingCashSimulator.getDisplay() != null)
            {
                graphicsContext.fillText("Number of Cars: " + unsafeParkingCashSimulator.getDisplay().getCars(), 520, 80);
                graphicsContext.fillText("Cash : " + unsafeParkingCashSimulator.getDisplay().getCash(), 520, 120);
            }
        }
        else
        {
            graphicsContext.fillText("Number of Cars: No Data", 520, 80);
            graphicsContext.fillText("Cash : No Data", 520, 120);
        }
    }
}

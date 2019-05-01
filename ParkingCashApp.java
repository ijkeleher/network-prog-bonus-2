import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class ParkingCashApp extends Application
{
    @Override
    public void start(Stage primaryStage)
    {

        primaryStage.setTitle("Parking Cash");
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(ParkingCashRender.WIDTH, ParkingCashRender.HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        ParkingCashRender render = new ParkingCashRender(scene, gc);
        render.run();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

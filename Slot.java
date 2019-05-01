import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

import java.awt.*;

public class Slot
{
    public enum State {Empty, Occupied}
    private State state = State.Empty;

    private GraphicsContext gc;

    private Rectangle base;

    public Slot(GraphicsContext gc, int x, int y)
    {
        this.gc = gc;

        base = new Rectangle(x, y, 200, 100);
    }

    public void carComeIn()
    {
        state = State.Occupied;
    }

    public void carComeOut()
    {
        state = State.Empty;
    }

    public void draw()
    {
        if(state == State.Empty)
            gc.setStroke(Color.GREEN);
        else if(state == State.Occupied)
            gc.setStroke(Color.RED);

        gc.strokeRect(base.x, base.y, base.width, base.height);
        gc.setFill(Color.BLUE);
        gc.fillText(state.toString(), base.x + 20, base.y + 20);


    }
}

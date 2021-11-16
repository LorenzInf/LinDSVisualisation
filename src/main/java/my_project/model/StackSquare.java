package my_project.model;

import KAGO_framework.control.SoundController;
import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

import static my_project.Config.WINDOW_HEIGHT;

public class StackSquare extends GraphicalObject {

    private ViewController viewController;
    private StackSquare topStackSquare;
    private boolean arrived;
    private boolean deleted;
    private int r;
    private int g;
    private int b;

    /**
     * Erzeugt ein neues StackSquare
     * @param x Startposition x
     * @param y Startposition y
     * @param viewController das ViewController-Objekt des Frameworks
     */
    public StackSquare(double x, double y, StackSquare topStackSquare, ViewController viewController){
        this.x = x;
        this.y = y;
        this.viewController = viewController;
        this.topStackSquare = topStackSquare;
        viewController.draw(this);
        arrived = false;
        deleted = false;
        r = 255;
        g = 255;
        b = 255;
    }

    /**
     * Selbsterklärend: zeichnet das StackSquare. Wird from Framework aufgerufen
     */
    @Override
    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(r,g,b,255);
        drawTool.drawFilledRectangle(x,y,40,40);
        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawRectangle(x,y,40,40);
    }

    /**
     * Wird mit jeder Frame vom Framework aufgerufen und dient zur Manipulation des Objekts im Verlauf
     * der Zeit.
     * @param dt die Sekunden, die seit dem letzten Aufruf von update vergangen sind
     */
    @Override
    public void update(double dt){
        if(!arrived){
            if(topStackSquare == null || y < topStackSquare.getY()-40){
                y += 200*dt;
            } else arrived = true;
            if (y >= WINDOW_HEIGHT-80) arrived = true;
        }
        if(deleted){
            x += 300*dt;
            if(x > 640) viewController.removeDrawable(this);
        }
    }

    public boolean tryToDelete(){
        if(arrived){
            deleted = true;
            return true;
        }
        return false;
    }

    public void changeColor(){
        r = (int) (Math.random()*255+1);
        g = (int) (Math.random()*255+1);
        b = (int) (Math.random()*255+1);
    }
}

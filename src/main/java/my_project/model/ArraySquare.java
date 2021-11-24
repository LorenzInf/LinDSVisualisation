package my_project.model;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.control.ProgramController;

public class ArraySquare extends GraphicalObject {
    private int arrayX;
    private int arrayY;
    private ViewController viewController;
    private ProgramController programController;
    private boolean empty;
    private boolean current;

    public ArraySquare(int arrayX, int arrayY, ViewController viewController, ProgramController programController, boolean empty, boolean current){
        this.arrayX = arrayX;
        this.arrayY = arrayY;
        this.programController = programController;
        this.viewController = viewController;
        this.empty = empty;
        this.current = current;
        viewController.draw(this);
    }

    /**
     * Selbsterklärend: zeichnet das ArraySquare. Wird from Framework aufgerufen
     */
    @Override
    public void draw(DrawTool drawTool){
        if(current) drawTool.setCurrentColor(100, 200, 100, 255);
        else if(empty) drawTool.setCurrentColor(150, 150, 150, 150);
        else drawTool.setCurrentColor(255, 100, 100, 255);

        if(arrayX == 0) drawTool.drawFilledRectangle(150, (arrayY * 30) + 200, 25, 25);
        else drawTool.drawFilledRectangle((arrayX * 20) + 160, (arrayY * 30) + 207.5, 15, 15);
    }

    public void deleteCurrent(){
        if(current){
            viewController.removeDrawable(this);
        }
    }

    /**
     * Wird mit jeder Frame vom Framework aufgerufen und dient zur Manipulation des Objekts im Verlauf
     * der Zeit.
     * @param dt die Sekunden, die seit dem letzten Aufruf von update vergangen sind
     */
    @Override
    public void update(double dt){

    }
}

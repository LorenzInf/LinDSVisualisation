package my_project.model;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.control.ProgramController;

public class ListRectangle extends GraphicalObject {
    private ViewController viewController;
    private ProgramController programController;
    private boolean deleted;
    private boolean visible;
    private ListRectangle lastRectangle;
    private double listXCoord;
    private double listYCoord;
    private int alpha;
    private int listRectangleTotal;
    private int posInList;

    /**
     * Erzeugt ein neues ListRectangle
     * @param listXCoord die x-Koordinate des ersten ListRectangles
     * @param listYCoord die y-Koordinate der ListRectangles
     * @param lastRectangle das letzte ListRectangle in der Liste
     * @param viewController das ViewController Objekt des Frameworks
     * @param listRectangleTotal die Gesamtanzahl aller ListRectangles
     * @param posInList das wie vielte ListRectangle es in der Liste ist
     */
    public ListRectangle(double listXCoord, double listYCoord, ListRectangle lastRectangle, ViewController viewController, ProgramController programController,
                         int listRectangleTotal, int posInList, ListRectangle nextRectangle){
        this.listXCoord = listXCoord;
        this.listYCoord = listYCoord;
        this.lastRectangle = lastRectangle;
        this.viewController = viewController;
        this.programController = programController;
        deleted = false;
        visible = true;
        alpha = 1;
        this.listRectangleTotal = listRectangleTotal;
        this.posInList = posInList;
    }

    /**
     * Selbsterklärend: zeichnet das ListRectangle. Wird from Framework aufgerufen
     */
    @Override
    public void draw(DrawTool drawTool){
        if(programController.isCurrent(this)) drawTool.setCurrentColor(255, 215, 0, alpha); //If current, Farbe = Gold
        else drawTool.setCurrentColor(62, 180, 137, alpha); //If not current, Farbe = Minze
        System.out.println("draw() wurde aufgerufen");
        drawTool.drawFilledRectangle(listXCoord + posInList * 20,listYCoord - 40,13,20);
    }

    /**
     * Wird mit jeder Frame vom Framework aufgerufen und dient zur Manipulation des Objekts im Verlauf
     * der Zeit.
     * @param dt die Sekunden, die seit dem letzten Aufruf von update vergangen sind
     */
    @Override
    public void update(double dt){
        if(!deleted && alpha < 255) alpha += 255*dt;
        if(alpha < 1) viewController.removeDrawable(this);
        if(y < listYCoord) y += 50*dt;
    }

    public boolean tryToDelete(){
        if(programController.isCurrent(this)){

        }
        return false;
    }
}

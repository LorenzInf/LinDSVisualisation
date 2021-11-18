package my_project.model;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.control.ProgramController;

public class ListRectangle extends GraphicalObject {
    private final ViewController viewController;
    private final ProgramController programController;
    private boolean deleted;
    private final double listXCoord;
    private final double listYCoord;
    private int alpha;
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
    public ListRectangle(double listXCoord, double listYCoord,  ViewController viewController, ProgramController programController, int posInList){
        this.listXCoord = listXCoord;
        this.listYCoord = listYCoord;
        this.viewController = viewController;
        this.programController = programController;
        deleted = false;
        alpha = 1;
        this.posInList = posInList;
        y = listYCoord - 25;
        viewController.draw(this);
    }

    /**
     * Selbsterklärend: zeichnet das ListRectangle. Wird from Framework aufgerufen
     */
    @Override
    public void draw(DrawTool drawTool){
        if(programController.isCurrent(this)) drawTool.setCurrentColor(255, 215, 0, alpha); //If current, Farbe = Gold
        else drawTool.setCurrentColor(62, 180, 137, alpha); //If not current, Farbe = Minze
        drawTool.drawFilledRectangle(listXCoord + posInList * 15,y,10,20);
    }

    /**
     * Wird mit jeder Frame vom Framework aufgerufen und dient zur Manipulation des Objekts im Verlauf
     * der Zeit.
     * @param dt die Sekunden, die seit dem letzten Aufruf von update vergangen sind
     */
    @Override
    public void update(double dt){
        if(!deleted && alpha < 256) alpha += 255*(dt*2);
        alpha = Math.min(255, alpha);
        if(y < listYCoord) y += 50*dt;
        y = Math.min(listYCoord,y);

        if(deleted){
            y -= 200*dt;
            alpha -= 255*(dt*2);
        }
        if(alpha < 1) {
            viewController.removeDrawable(this);
            programController.moveUpRectangleList();
        }
    }

    public boolean tryToDelete(){
        if(programController.isCurrent(this)){
            deleted = true;
            return true;
        }
        return false;
    }

    public void setPosInList(int posInList){
        this.posInList = posInList;
    }

    public int getPosInList() {
        return posInList;
    }
}

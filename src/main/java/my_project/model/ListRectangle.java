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
    private double posInList;
    private int moving;
    private int tmp;


    /**
     * Erzeugt ein neues ListRectangle
     * @param listXCoord die x-Koordinate des ersten ListRectangles
     * @param listYCoord die y-Koordinate der ListRectangles
     * @param viewController das ViewController Objekt des Frameworks
     * @param posInList das wie vielte ListRectangle dies in der Liste ist
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
        if (!deleted && alpha < 256) alpha += 255*(dt*2);
        alpha = Math.min(255, alpha);
        if(y < listYCoord) y += 50*dt;
        y = Math.min(listYCoord,y);

        if(deleted){
            y -= 200*dt;
            alpha -= 255*(dt*2);
        }
        if (alpha < 1) {
            viewController.removeDrawable(this);
            if(!programController.removedLast()) programController.moveUpRectangleList();
            else {
                programController.resetRemovedLast();
                programController.resetRemoving();
            }
        }
        if (moving == 1) {
            if(posInList < tmp + 1){
                posInList += 10*dt;
                posInList = Math.min(tmp + 1, posInList);
            } else moving = 0;
        }
        if (moving == -1) {
            if(posInList > tmp - 1){
                posInList -= 10*dt;
                posInList = Math.max(tmp - 1, posInList);
            } else {
                moving = 0;
                tmp = 0;
            }
        }
    }

    /**
     * Versucht das ListRectangle zu löschen
     * @return {@code true}, falls dieses ListRectangle current ist
     */
    public boolean deleteCurrent(){
        if(programController.isCurrent(this)){
            deleted = true;
            return true;
        }
        return false;
    }

    public void setPosInList(double posInList){
        this.posInList = posInList;
    }

    public int getPosInList() {
        return (int) posInList;
    }

    public void setMoving(int amount) {
        moving = amount;
        tmp = (int) posInList;
    }
}

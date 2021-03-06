package my_project.view;

import KAGO_framework.control.Interactable;
import KAGO_framework.control.ViewController;
import my_project.control.ProgramController;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Realisiert ein Objekt, dass alle Eingaben empfängt und dann danach passende Methoden
 * im ProgramController aufruft
 */
public class InputReceiver implements Interactable {

    private ProgramController programController;
    private ViewController viewController;

    /**
     * Objekterzeugung
     * @param programController Nötig als Objekt vom Controllerbereich, das informiert wird
     * @param viewController Nötig, um den Aufruf der Interface-Methoden sicherzustellen
     */
    public InputReceiver(ProgramController programController, ViewController viewController){
        this.programController = programController;
        this.viewController = viewController;
        viewController.register(this);
    }

    @Override
    public void keyPressed(int key) {

    }

    @Override
    public void keyReleased(int key) {
        if(key == KeyEvent.VK_SPACE) programController.addSquareToStack();
        if(key == KeyEvent.VK_BACK_SPACE) programController.deleteSquareFromStack();
        if(key == KeyEvent.VK_E) programController.changeTopSquareColor();

        if(!programController.isRemoving()){
            if(key == KeyEvent.VK_UP) programController.removeRectangleFromList();
            if(key == KeyEvent.VK_DOWN) programController.appendRectangleToList();
            if(key == KeyEvent.VK_INSERT) programController.insertRectangleIntoList();
        }
        if(key == KeyEvent.VK_RIGHT) programController.rectangleListRight();
        if(key == KeyEvent.VK_LEFT) programController.rectangleListLeft();

        if(key == KeyEvent.VK_W) programController.squareArrayUp();
        if(key == KeyEvent.VK_A) programController.squareArrayLeft();
        if(key == KeyEvent.VK_S) programController.squareArrayDown();
        if(key == KeyEvent.VK_D) programController.squareArrayRight();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) programController.addBallToQueue();
        else programController.deleteBallFromQueue();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }
}

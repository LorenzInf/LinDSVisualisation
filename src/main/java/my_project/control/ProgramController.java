package my_project.control;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.model.abitur.datenstrukturen.Queue;
import KAGO_framework.model.abitur.datenstrukturen.Stack;
import my_project.model.ArraySquare;
import my_project.model.ListRectangle;
import my_project.model.QueueBall;
import my_project.model.StackSquare;
import my_project.view.InputReceiver;

import java.awt.event.MouseEvent;
import java.lang.reflect.Array;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    //Attribute


    // Referenzen
    private final ViewController viewController;  // diese Referenz soll auf ein Objekt der Klasse viewController zeigen. Über dieses Objekt wird das Fenster gesteuert.

    private Queue<QueueBall> ballQueue;
    private QueueBall lastBallInQueue;

    private Stack<StackSquare> squareStack;

    private List<ListRectangle> rectangleList;
    private int posInList = 0;
    private boolean removing;
    private boolean removedLast;
    private ListRectangle toMoveUpFrom;

    private ArraySquare squareArray[][] = new ArraySquare[8][4];

    /**
     * Konstruktor
     * Dieser legt das Objekt der Klasse ProgramController an, das den Programmfluss steuert.
     * Damit der ProgramController auf das Fenster zugreifen kann, benötigt er eine Referenz auf das Objekt
     * der Klasse viewController. Diese wird als Parameter übergeben.
     * @param viewController das viewController-Objekt des Programms
     */
    public ProgramController(ViewController viewController){
        this.viewController = viewController;
    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen.
     * Sie erstellt die leeren Datenstrukturen, zu Beginn nur eine Queue
     */
    public void startProgram() {
        // Für Benutzerinteraktion
        new InputReceiver(this,viewController); // darf anonym sein, weil kein Zugriff nötig ist
        // Für die Queue:
        ballQueue = new Queue<>();
        squareStack = new Stack<>();
        lastBallInQueue = null; // die letzte Kugel muss für die Animation gemerkt werden

        // Für die List
        rectangleList = new List<>();

        // Für das Array
        for(int x = 0; x < squareArray.length; x++){
            for(int y = 0; y < squareArray.length; y++){
                ArraySquare arraySquare = new ArraySquare(x,y,viewController,this,true);
            }
        }
    }

    //Ball Queue

    /**
     * Adds a QueueBall to ballQueue
     */
    public void addBallToQueue(){
        QueueBall newQueueBall = new QueueBall(650,50, lastBallInQueue,viewController);
        ballQueue.enqueue(newQueueBall);
        lastBallInQueue = newQueueBall;
    }

    /**
     * Deletes a QueueBall from ballQueue
     */
    public void deleteBallFromQueue(){
        if(!ballQueue.isEmpty()){
            if(ballQueue.front().tryToDelete()) ballQueue.dequeue();
        }
    }

    //Square Stack
    /**
     * Adds a StackSquare to squareStack
     */
    public void addSquareToStack(){
        StackSquare newStackSquare = new StackSquare(50, -20, squareStack.top(), viewController);
        squareStack.push(newStackSquare);
    }

    /**
     * Deletes a StackSquare from squareStack
     */
    public void deleteSquareFromStack(){
        if(!squareStack.isEmpty()) {
            if(squareStack.top().tryToDelete()) squareStack.pop();
        }
    }

    //Rectangle List
    /**
     * Appends a ListRectangle to the rectangleList
     */
    public void appendRectangleToList(){
        if(posInList < 32) {
            ListRectangle newListRectangle = new ListRectangle(100, 100, viewController, this, posInList);
            rectangleList.append(newListRectangle);
            posInList += 1;
        }
    }

    /**
     * Inserts a ListRectangle into rectangleList
     */
    public void insertRectangleIntoList() {
        if(posInList < 32) {
            if (!rectangleList.isEmpty()) {
                int tmp = posInList + 1;
                posInList = rectangleList.getContent().getPosInList();
                toMoveUpFrom = rectangleList.getContent();
                moveDownRectangleList();
                ListRectangle newListRectangle = new ListRectangle(100, 100, viewController, this, posInList);
                rectangleList.insert(newListRectangle);
                posInList = tmp;
            } else {
                appendRectangleToList();
            }
        }
    }

    /**
     * Removes a ListRectangle from rectangleList
     */
    public void removeRectangleFromList(){
        if(rectangleList.hasAccess() && !removing){
            if(rectangleList.getContent().deleteCurrent()) {
                ListRectangle tmp = rectangleList.getContent();
                rectangleList.next();
                if(!rectangleList.hasAccess()) removedLast = true;
                rectangleList.toFirst();
                while(rectangleList.getContent() != tmp){
                    rectangleList.next();
                }
                rectangleList.remove();
                posInList -= 1;
                toMoveUpFrom = rectangleList.getContent();
                removing = true;
            }
        }
    }

    /**
     * Randomizes the colour of the top StackSquare in squareStack
     */
    public void changeTopSquareColor(){
        if(!squareStack.isEmpty()) squareStack.top().changeColor();
    }

    /**
     * @param listRectangle the ListRectangle to be checked
     * @return {@code true}, if listRectangle is current in rectangleList
     */
    public boolean isCurrent(ListRectangle listRectangle){
        return rectangleList.hasAccess() && rectangleList.getContent().equals(listRectangle);
    }

    /**
     * Moves current in rectangleList one element to the right
     * <p> (if it gets called when current is last, current is automatically moved to first, via {@code updateProgram})
     */
    public void rectangleListRight() {
        rectangleList.next();
    }

    /**
     * Moves current in rectangleList one element to the left
     * <p>(if it gets called when current is first, current is moved to last)
     */
    public void rectangleListLeft(){
        if(!rectangleList.isEmpty()) {
            int subtractAmount = removing ? 2 : 1;
            int prevPos = rectangleList.getContent().getPosInList();
            rectangleList.toFirst();
            while(rectangleList.hasAccess() && rectangleList.getContent().getPosInList() != (prevPos - subtractAmount)) {
                rectangleList.next();
            }
            if(!rectangleList.hasAccess()) rectangleList.toLast();
        }
    }

    /**
     * Is called after an element from rectangleList is removed (unless it was last)
     */
    public void moveUpRectangleList(){
        moveRectangleList(-1);
    }

    /**
     * Is called after an element is inserted into rectangleList
     */
    public void moveDownRectangleList(){
       moveRectangleList(1);
    }

    /**
     * If amount is -1, every ListRectangle, starting from the one after the one that got removed before this function was called, is moved up by one position
     * <p> If amount is 1, every ListRectangle, starting from the one after the one that got inserted before this function was called, is moved down by one position
     * @param amount amount is set by calling either {@code moveUpRectangleList} or {@code moveDownRectangleList}
     */
    private void moveRectangleList (int amount) {
        ListRectangle prevCurrentRectangle = rectangleList.getContent();
        rectangleList.toFirst();
        while(rectangleList.getContent() != toMoveUpFrom){
            rectangleList.next();
        }
        while(rectangleList.hasAccess()){
            rectangleList.getContent().setMoving(amount);
            rectangleList.next();
        }
        rectangleList.toFirst();
        while(rectangleList.getContent() != prevCurrentRectangle){
            rectangleList.next();
        }
        resetRemoving();
    }

    public boolean removedLast(){
        return removedLast;
    }

    public void resetRemovedLast(){
        removedLast = false;
    }

    public boolean isRemoving(){
        return removing;
    }

    public void resetRemoving(){
        removing = false;
    }

    public void squareArrayUp() {

    }

    public void squareArrayLeft() {

    }

    public void squareArrayDown() {

    }

    public void squareArrayRight() {

    }

    /**
     * Aufruf bei Mausklick
     * @param e das Objekt enthält alle Informationen zum Klick
     */
    public void mouseClicked(MouseEvent e){

    }

    /**
     * Aufruf mit jeder Frame
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt){
        if(!rectangleList.hasAccess()) rectangleList.toFirst();
    }
}
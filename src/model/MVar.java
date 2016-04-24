package model;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  MVar Class - rps
 *  
 *  Author:
 *                  David Smallwood
 * 
 *  Issues:
 * 
 *  Notes:
 *
 *      Custom construcors were added that would allow the state to
 *      be "set" upon creation. This was later removed as use of 
 *      this MVar was scaled back in favour of java.util.concurrent
 *      data structures
 * 
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
public class MVar<E> {
    private E       state;
    private boolean isSet = false;

    /** 
     *  getIsSet
     *
     *  Description:
     *      Returns the state of the "isSet" variable
     */
    public boolean getIsSet () {
        return isSet;
    }

    public synchronized void putMVar(E s) {
        while (isSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                // handle
            }
        }

        isSet = true;
        state = s;
        notifyAll();
    }

    public synchronized E takeMVar() {
        while (!isSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                // handle
            }
        }

        isSet = false;
        notifyAll();
        return state;
    }
}
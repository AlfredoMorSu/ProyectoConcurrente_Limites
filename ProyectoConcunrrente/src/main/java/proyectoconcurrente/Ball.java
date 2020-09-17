package proyectoconcurrente;
import javax.swing.*;
/**
 *
 * @author José Alfredo Moreno Suárez
 */
public class Ball extends Thread{
    private PanelBall panel;
    private boolean pausa = false;
    private boolean corriendo = true;
    
    private MiX x;
    private MiY y;
    private int MaximoX = 500; //Minimo X =0
    private int MaximoY = 350; //minimo Y = 0
    private int dx = 10;
    private int dy = 10;
    
    
    Ball(PanelBall panel, MiX x, MiY y){
        this.panel = panel;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void run(){
        try{
            while(true){
                x.setX(x.getX()+dx); //x = x+10;
                y.setY(y.getY()+dy); //y = y+10;
                if(x.getX() < 0){
                    x.setX(0);
                    dx=-dx;
                }
                if((x.getX() + 20) >= MaximoX){
                    x.setX(MaximoX-20);
                    dx=-dx;
                }
                
                if(y.getY() < 0){
                    y.setY(0);
                    dy=-dy;
                }
                if((y.getY()+20) >= MaximoY){
                    y.setY(MaximoY-20);
                    dy=-dy;
                }
                panel.repaint();
                
                sleep(25);
                synchronized(this){
                    if(pausa)
                        wait();
                    if(!corriendo)
                        join();
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void pausa(){
        this.pausa = true;
    }
    
    public void reanudar(){
        synchronized(this){
            pausa = false;
            notifyAll();
        }
    }
    
    public void detener(){
        this.corriendo = false;
    }
}

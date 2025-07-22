import java.util.ArrayList; 
import processing.core.PApplet;

public class Sketch extends PApplet {
  final int WIDTH = 400;
  final int HEIGHT = 100;
  final int WALKER_COUNT = 50;
  
  ArrayList<Walker> walkers;
  
  public void settings() {
    size(400, 100);
    walkers = new ArrayList<Walker>();
    for(int i=0; i<WALKER_COUNT; i++)
      walkers.add( new Walker(this,WIDTH,HEIGHT,1) );
    for(int i=0; i<WALKER_COUNT; i++)
      walkers.add( new Walker(this,WIDTH,HEIGHT,0) );
    System.out.println(walkers);
  }

  public void setup() {
    background(152, 190, 100);
  }

  public void draw() {
    background(152, 190, 100);
    walkers.sort( (w1,w2) -> Float.compare(w1.x, w2.x) );
    
    for(int i=0; i<WALKER_COUNT*2; i++)
    {
      Walker w = walkers.get(i);
      if(w.dx>0)
      {
        for(int j=i+1; j<WALKER_COUNT*2; j++)
        {
          if( w.lookForward(walkers.get(j)) )
            break;
        }
      }
      else
      {
        for(int j=i-1; j>=0; j--)
        {
          if( w.lookForward(walkers.get(j)) )
            break;
        }        
      }
    }
    
    walkers.forEach( (w) -> w.oneStep() );
    walkers.forEach( (w) -> w.show() );
    delay(10);
  }
}
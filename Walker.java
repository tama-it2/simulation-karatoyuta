import processing.core.PApplet;

public class Walker extends PApplet
{
  final float SHIYA_KYORI = 20.0f;
  final float SHOTOTSU_KYORI = 5.0f;
  final float MIN_WALK_SPEED = 0.8f;

  final float KASOKU = 1.1f;
  
  protected int dx;
  protected float x,y;
  float vx,vy;
  float maxSpeed;
  
  Sketch sketch;
  float width, height;

  public Walker(Sketch s, int w, int h, int idx)
  {
    sketch = s;
    maxSpeed = random(1.0f) * (1-MIN_WALK_SPEED) + MIN_WALK_SPEED;
    width = w;
    height = h;
    if(idx > 0)
    {
      dx = -1;
      vx = 1.0f;
      vy = 0.0f;
      x = random(w);
      y = random(h);
    }
    else
    {
      dx = 1;
      vx = -1.0f;
      vy = 0.0f;
      x = random(w);
      y = random(h);      
    }
  }

  public void show()
  {
    sketch.g.ellipse((int)x,(int)y, 5, 5);
  }

  public void oneStep()
  {
    vx *= KASOKU;
    vy *= KASOKU;
    adjustSpeed();
    x += vx;
    y += vy;
    checkBoundary();
  }

  void checkBoundary()
  {
    if(x<0)
    {
      x=width;
      y=random(height);
      maxSpeed = random(1.0f) * 0.4f + 0.6f;    
    }
    if(x>width)
    {
      x=0;
      y=random(height);
      maxSpeed = random(1.0f) * 0.4f + 0.6f;    
    }    
    if(y<0) y=0;
    if(y>height) y=height;
  }

  void adjustSpeed()
  {
    vx = vx + dx * 0.5f;
    vy = vy - (y - height/2) / height * 0.1f;
    float v = (float)Math.sqrt(vx*vx+vy*vy);
    if(v>maxSpeed)
    {
      vx = vx * maxSpeed / v;
      vy = vy * maxSpeed / v;
    }
  }

  public boolean lookForward(Walker w)
  {
    double d = Math.sqrt((w.x-x)*(w.x-x) + (w.y-y)*(w.y-y));
    float fx, fy;
    fx = 0;
    fy = 0;
    if( d < SHOTOTSU_KYORI )
    {
      fx += (x - w.x) / d ;
      fy += (y - w.y) / d ;
      vx = vx/10.0f + fx * 3.0f;
      vy = vy/10.0f + fy * 3.0f;
    }
    if( d < SHIYA_KYORI )
    {
      fx += (w.x - x) / d;
      fy += (w.y - y) / d;
      if(w.dx == dx)
      {
        vx += fx * 0.1f;
        vy += fy * 0.1f;
      }
      else
      {
        vx -= fx * 0.3f;
        vy -= fy * 0.3f;        
      }
    }
    else
      return true;
    return false;
  }
}

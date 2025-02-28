import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Color;
import javax.swing.JOptionPane;
// main
public class Main {
  // booleans for which keys are being pressed
  private static boolean lP, dP, uP, rP, wP, sP;
  public static void main(String[] args) { 
    lP = false;
    dP = false;
    uP = false;
    rP = false;
    wP = false;
    sP = false;
    // list of shapes to be inputted into the panel
    ArrayList<Shape3D> sList = new ArrayList<Shape3D>();
    // 3D Constructions *start*
    
    // Staircase: 
     sList.add(new RectangularCuboid(new Vertex(0, -200, 0), new Vertex(100, -100, 100), 0, 0, 0, Color.RED));
     sList.add(new RectangularCuboid(new Vertex(0, -100, 0), new Vertex(100, 0, 100), 0, 0, 0, Color.ORANGE));
     sList.add(new RectangularCuboid(new Vertex(0, 0, 0), new Vertex(100, 100, 100), 0, 0, 0, Color.YELLOW));
     sList.add(new RectangularCuboid(new Vertex(0, 100, 0), new Vertex(100, 200, 100), 0, 0, 0, Color.GREEN));
     sList.add(new RectangularCuboid(new Vertex(0, 200, 0), new Vertex(100, 300, 100), 0, 0, 0, Color.BLUE));

    // Tetrahedron: 
    //sList.add(new Rectangle3D(new Vertex(0, 0, 0), 100, 100, 0, 0, 0, Color.BLUE));
    
    // 3D Constructions *end*
    
    // creating the frame
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    // creating the 3D panel using the Shape3D list provided earlier
    Panel3D panel = new Panel3D(sList);
    panel.setPreferredSize(new Dimension(600, 600));
    
    if(JOptionPane.showConfirmDialog(null, "Wireframe?", "?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
      panel.isWireFrame = true;
    if(JOptionPane.showConfirmDialog(null, "Full Rasterization?", "?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
      panel.isFullRasterization = true;
    
    // adding the panel to the frame
    frame.add(panel);
    frame.pack();
    frame.setVisible(true);
    // adding a custom keylistener that alters the key pressed values defined within the main class
    frame.addKeyListener(new KeyListener() {
      @Override
      public void keyPressed(KeyEvent e) {
        int kCode = e.getKeyCode();
        if(kCode == KeyEvent.VK_UP)
          uP = true;
        if(kCode == KeyEvent.VK_DOWN)
          dP = true;
        if(kCode == KeyEvent.VK_RIGHT)
          rP = true;
        if(kCode == KeyEvent.VK_LEFT)
          lP = true;
        if(kCode == KeyEvent.VK_W)
          wP = true;
        if(kCode == KeyEvent.VK_S)
          sP = true;
      }
      @Override
        public void keyReleased(KeyEvent e) {
          int kCode = e.getKeyCode();
          if(kCode == KeyEvent.VK_UP)
            uP = false;
          if(kCode == KeyEvent.VK_DOWN)
            dP = false;
          if(kCode == KeyEvent.VK_RIGHT)
            rP = false;
          if(kCode == KeyEvent.VK_LEFT)
            lP = false;
          if(kCode == KeyEvent.VK_W)
            wP = false;
          if(kCode == KeyEvent.VK_S)
            sP = false;
        }
      @Override
        public void keyTyped(KeyEvent e) {

        }
    });

    // requesting that the frame gets focus
    frame.requestFocusInWindow();
    // delta value for the gameloop
    double delta = 0;
    // fps of the render(render runs non-concurrently on CPU so don't set very high)
    int fPS = 15;
    // values to determine the passing of time
    long oldTime = System.nanoTime();
    long curTime = System.nanoTime();
    // amount of time(ns) between each frame
    double timeBetFrames = 1000000000/fPS;
    //  render loop
    while(1 != 2) {
      // breaks out of this loop on average *FPS* times per second
      while(delta < 1) {
        delta += (curTime-oldTime)/timeBetFrames;
        oldTime = curTime;
        curTime = System.nanoTime();
      }
      delta--;
      // handling rotations
      if(lP)
        panel.xzRot += 0.01;
      if(rP)
       panel.xzRot -= 0.01;
      if(uP)
        panel.yzRot -= 0.01;
      if(dP)
        panel.yzRot += 0.01;
      if(wP)
        panel.xyRot += 0.01;
      if(sP)
        panel.xyRot -= 0.01;
      // updating and repainting the 3D panel
      panel.update();
      panel.repaint();
      }
    }
  }

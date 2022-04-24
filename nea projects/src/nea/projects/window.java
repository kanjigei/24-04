/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nea.projects;

import static nea.projects.editor.mysa;
import static nea.projects.editor.sdf;
import static nea.projects.editor.hsb;




import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import static nea.projects.editor.pas;


/**
 *
 * @author 15choasi
 */
public class window extends JComponent implements MouseWheelListener, MouseListener, MouseMotionListener, ActionListener {

    private static int Max_iter = 100;
    private static float Scale = 250;
   // private final double zoom = 250;
    private double zoomfactor = 1230;
    private double prevzoomfactor = 1;
    static BufferedImage Image;
   // private double zx, zy, cX, cY, tmp;
   // private final int colour = 20;
    int mf = 35;
    public static int WIDTH = 1100;
    public static int HEIGHT = 600;
    private double xOffset;
    private double yOffset;
    private int xDiff;
    private int yDiff;
    private Point startPoint;
    public static float hueoffset = 0.6f;
    private static Timer timer;
    private boolean newImage = true;
    public static JButton J;
    public static JFrame frame;
    private static double top = -1.0;
    private static double left = -2.0;
    private static double zoom = 1.0 / 700.0;
    private static double zr = 0.0, zi =0.0;
    private static double ci,zrzr,zizi ,cr;
    private static int colour;
    private static JLabel sdfs;
    public static float brightness;
    public static float hue;
    public static float saturation;
    
    private boolean zoomer , dragger, released;

    public window() {
       
       
        Image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
//        JLabel label = new JLabel(new ImageIcon(Image));
//        JPanel ls = new JPanel();
//        ls.add(label);
        
        timer = new Timer(1, this);
        
        
       
        J = new JButton("show Pallete editor");
        J.setActionCommand("editor");
        ActionListener l = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String action = ae.getActionCommand();
                if (action.equals("editor")) {
                    new editor().setVisible(true);
                }
            }
        };
        J.addActionListener(l);
        
//        JFrame frame = new JFrame("mandelbrot set");
//        
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setResizable(false);
//        frame.setContentPane(new window());
//        frame.getContentPane().add(ls);
//        frame.getContentPane().add(J,BorderLayout.PAGE_END);
//       
//        Component add = frame.getContentPane().add(this);
//        frame.getContentPane().add(this);
//        
//        
//        
//        frame.pack();
//        frame.setResizable(true);
//        frame.setVisible(true);
        
        
       // Image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
       /* for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                zx = zy = 0;
                cX = (x - 500) / zoom;
                cY = (y - 300) / zoom;
                int iter = Max_iter;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                    
                }
                Image.setRGB(x, y, iter | (iter << mf));
            } 
        }*/
        
    }
//    public static void main(String[] args) {
//       
//        EventQueue.invokeLater(() -> {  
//        JFrame frame = new JFrame("mandelbrot set");
//        
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setResizable(false);
//        frame.getContentPane().add(new window());
//        frame.getContentPane().add(ls);
//        frame.getContentPane().add(J,BorderLayout.PAGE_END);
//       
//        Component add = frame.getContentPane().add(this);
//        frame.getContentPane().add(this);
//        
//        
//        
//        frame.pack();
//        frame.setResizable(true);
//        frame.setVisible(true);
//        
//        
//        });
//      }
    public static MouseListener mouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent event) {
                if (event.getButton() == MouseEvent.BUTTON1) {
                    left = (event.getX() - WIDTH / 8.0) * zoom + left;
                    top = (event.getY() - HEIGHT / 8.0) * zoom + top;
                    zoom = zoom / 4.0;
                    
                    
                } else {
                    left = (event.getX() - WIDTH) * zoom + left;
                    top = (event.getY() - HEIGHT) * zoom + top;
                    zoom = zoom * 2.0;
                }
                renderMandelbrotset();
            }
        };
    }
    
    
    @Override
    public void addNotify(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        timer.start();
    }
    
    
    private void repaint(boolean newImage) {
		this.newImage = newImage;
		repaint();
	}
    
    public int rint(double d) {
		return (int) Math.rint(d); // half even
	}
    
    private static Executor executor = Executors.newSingleThreadExecutor();

    
    public static void renderMandelbrotset(){
        
        executor.execute(()-> {
      
          // printThreadInfo();
          IntStream.range(0, HEIGHT).parallel().forEach((int y) -> {
          
              if(mysa != null){
              
          //    int editorColour = Integer.parseInt(mysa);
              
          //    for (int y = 0; y < HEIGHT; y++) 
            for (int x = 0; x < WIDTH; x++){
          //      int colour = calculatePoint((x - WIDTH/2f)/Scale , (y - HEIGHT/2f)/Scale);
                float sd = (x- WIDTH/2f)/Scale;
                float df = (y - HEIGHT/2f)/Scale;
                
                float cx = sd;
                float cy = df;
        
                int i = 0;
       
                for(;i<Max_iter;i++){

                    float nx = sd*sd - df*df + cx;
                    float ny = 2 * sd* df +cy;

                    sd = nx;
                    df = ny;

                    if(sd*sd + df*df > 4) break;
                }

                if(i == Max_iter){
                    colour = 0x000000;
                }else{
                   // colour =  sdf;
                 //  colour = Color.decode(mysa);
                pas = sdf.getRGB();
                //colour = pas;
                hsb = Color.RGBtoHSB(sdf.getRed(), sdf.getGreen(), sdf.getBlue(), null);
                
                hue = hsb[0];
                saturation = hsb[1];
                brightness = hsb[2];
                   
                    colour = Color.HSBtoRGB((float)i/Max_iter+ hue,  saturation, brightness);
                }
                

                Image.setRGB(x, y, colour);
            }        
        
        frame.repaint();
              
          }else{
            
   //     for (int y = 0; y < HEIGHT; y++) 
            for (int x = 0; x < WIDTH; x++){
          //      int colour = calculatePoint((x - WIDTH/2f)/Scale , (y - HEIGHT/2f)/Scale);
                float sd = (x- WIDTH/2f)/Scale;
                float df = (y - HEIGHT/2f)/Scale;
                
                float cx = sd;
                float cy = df;
        
                int i = 0;
        
       
        
                for(;i<Max_iter;i++){

                    float nx = sd*sd - df*df + cx;
                    float ny = 2 * sd* df +cy;

                    sd = nx;
                    df = ny;

                    if(sd*sd + df*df > 4) break;
                }

                if(i == Max_iter){
                    colour = 0x000000;
                }else{
                    colour = Color.HSBtoRGB(((float)i / Max_iter + hueoffset)%1f, 0.7f, 1);
                }
                

                Image.setRGB(x, y, colour);
            }        
      //  float x,y;
        
        frame.repaint();
          }
        });
        });
    }
    
//    public static int calculatePoint(float x , float y){
//        
//        float cx = x;
//        float cy = y;
//        
//        int i = 0;
//        
//       
//        
//        for(;i<Max_iter;i++){
//            
//            float nx = x*x - y*y + cx;
//            float ny = 2 * x* y +cy;
//            
//            x = nx;
//            y = ny;
//            
//            if(x*x + y*y > 4) break;
//        }
//        
//        if(i == Max_iter) return 0x000000;
//        return Color.HSBtoRGB(((float)i / Max_iter + hueoffset)%1f, 0.7f, 1);
//        
//    }
    
    private static void printThreadInfo() {
        System.out.println(Thread.currentThread());
        Arrays.stream(Thread.currentThread().getStackTrace())
            .skip(2)
            .limit(4)
            .forEach(System.out::println);
        System.out.println();
    }
    
//    private static int PALETTE() {
//     
//       
//    };
    

    @Override
    public void paint(Graphics g) {
//        super.paint(g);
//        Graphics2D g2 = (Graphics2D) g;
//    if (zoomer) {
//        AffineTransform at = new AffineTransform();
//        
//        double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
//        double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();
//
//        double zoomDiv = zoomfactor / prevzoomfactor;
//
//        xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
//        yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;
//        
//        
//        at.scale(zoomfactor, zoomfactor);
//        prevzoomfactor = zoomfactor;
//        g2.transform(at);
//        zoomer = false;
//    }
//    if(dragger){
//        AffineTransform at = new AffineTransform();
//            at.translate(xOffset + xDiff, yOffset + yDiff);
//            at.scale(zoomfactor, zoomfactor);
//            g2.transform(at);
//
//            if (released) {
//                xOffset += xDiff;
//                yOffset += yDiff;
//                dragger = false;
//            }
//
//        } 
        
        g.drawImage(Image, 0, 0, null);
        
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
//        zoomer = true;
//        
//        //Zoom in
//        if (e.getWheelRotation() < 0) {
//            zoomfactor *= 1.1;
//            repaint();
//        }
//        if (e.getWheelRotation() > 0) {
//            zoomfactor /= 1.1;
//            repaint();
//        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      
    }

    @Override
    public void mousePressed(MouseEvent e) {
        released = false;
        startPoint = MouseInfo.getPointerInfo().getLocation();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        released = true;
        repaint(); 
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       
    }

    @Override
    public void mouseExited(MouseEvent e) {
      
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point curPoint = e.getLocationOnScreen();
        xDiff = curPoint.x - startPoint.x;
        yDiff = curPoint.y - startPoint.y;

        dragger = true;
        
        repaint(); 
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
       // hueoffset += 0.5f;
        renderMandelbrotset();
        repaint();
        
    }

}
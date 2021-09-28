package Tool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import javax.swing.JPanel;


public class PanelCool1 extends JPanel {

    private boolean reverse;

    public PanelCool1() {
        super();
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Point start = new Point(0, 0);
        Point end = new Point(0, getHeight());
        float[] fractions = new float[]{
            0F, 0.5F, 1f
        };
        Color[] colors = null;
       if (isReverse()) {
            colors = new Color[]{
                Color.BLUE, Color.CYAN, Color.BLUE
            };
        } else {
            colors = new Color[]{
                Color.BLUE, Color.CYAN, Color.BLUE
            };
        
        }
        LinearGradientPaint paint = new LinearGradientPaint(start, end, fractions, colors);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(paint);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}

package Tool;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author usu
 */
public class Password extends JPasswordField {

    public Password() {
        super();
        setOpaque(false);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setHorizontalAlignment(CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paintComponent(g);

        Color dark = new Color(1F, 1F, 1F, 0F);
        Color light = new Color(1F, 1F, 1F, 0.3F);
        GradientPaint paint = new GradientPaint(0, 0, light, 0, getHeight() / 2, dark);
        g2.setPaint(paint);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());

        g2.dispose();
    }
}

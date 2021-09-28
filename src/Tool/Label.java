package Tool;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import Tool.EfectFrame;


public class Label extends JLabel {

    public Label() {
        super();
    }

    @Override
    public void setIcon(Icon icon) {
        if (icon instanceof ImageIcon) {
            ImageIcon con = (ImageIcon) icon;
            con = new ImageIcon(EfectFrame.getEfekKaca(con.getImage()));
            super.setIcon(con);
        } else {
            super.setIcon(icon);
        }
    }
}

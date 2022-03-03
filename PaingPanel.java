import javax.swing.*;
import java.awt.*;

public class PaingPanel extends JPanel {
    int x0, y0;
    int x1, y1;
    int x, y;
    double v = 12;
    double alpha = Math.atan(0.2);
    double t = 0;
    int targetX;
    int targetY;
    double delta_t = 0.1, delta_alpha = 0.05;
    int preVPosition;

    @Override
    protected void paintComponent(Graphics g) {
        if (x0 == 0) {
          reset();
        }
        g.setColor(Color.red);
        g.fillOval(x1, y1, 10, 10);
        if (x1 > x && x != 0) {
            g.drawLine(x, y, x1, y1);
        }
        g.setColor(Color.BLUE);
        g.fillOval(targetX, targetY, 10, 10);
    }

    public boolean clacCoord() {
        x = x1;
        y = y1;
        x1 = x0 + (int)(100 * ( (v * Math.cos(alpha) + (MainForm.wind_v * Math.cos(MainForm.wind_alpha)) ) * t));
        y1 = y0 - (int)(100 * ((v * Math.sin(alpha) + (MainForm.wind_v * Math.sin(MainForm.wind_alpha)) ) * t - 9.81 * t * t / 2));
        while (x1 > targetX && delta_t > 0.001 && t > 0) {
            delta_t = delta_t/2;
            t = t - delta_t;
            x1 = x0 + (int)(100 * ((v * Math.cos(alpha) + (MainForm.wind_v * Math.cos(MainForm.wind_alpha)) ) * t));
            y1 = y0 - (int)(100 * ((v * Math.sin(alpha) + (MainForm.wind_v * Math.sin(MainForm.wind_alpha)) ) * t - 9.81 * t * t / 2));
        }
        return (x1 < targetX);
    }

    public PaingPanel nextT() {
        t += delta_t;
        return this;
    }

    public void reset() {
        if(isPositionChanged()) {
            delta_alpha = delta_alpha / 2;
            alpha -= delta_alpha;
        } else{
            alpha += delta_alpha;
        }
        t = 0;
        delta_t = 0.1;
        x0 = 50;
        y0 = (getHeight() * 2) / 3;
        clacCoord();
        targetX = x0 + 100 * 10;
        targetY = y0 - 100 * 2;
        invalidate();
        repaint();
    }

    public void resetDeltaAlpha(){
        delta_alpha = 0.05;
        preVPosition = 0;
    }

    public boolean isPositionChanged(){
        if (t == 0) {
            return false;
        }
        double y_tmp = ((1.0 * targetX - x)/(x1 - x)) * (y - y1) + y1;
        if (preVPosition == 0) {
            preVPosition = (int)Math.signum(y_tmp - targetY);
            return false;
        } else {
            return preVPosition != (int)Math.signum(y_tmp - targetY);
        }
    }

    public boolean check() {
        return Math.abs(x - targetX) <= 10 && Math.abs(y - targetY) <= 10;
    }

    public double getAlpha() {
        return alpha;
    }

}

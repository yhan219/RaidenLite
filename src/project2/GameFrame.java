package project2;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {

    private GameCanvas canvas = new GameCanvas();

    public GameFrame() {
        super();
        canvas.setFocusable(true);
        canvas.requestFocus();
        this.setLayout(new GridLayout(1, 1));
        this.add(canvas);
    }

    public static void main(String args[]) {
        GameFrame frame = new GameFrame();
        frame.setTitle("雷电");
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dem = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((int) (dem.getWidth() - 400) / 2, (int) (dem.getHeight() - 600) / 2);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

public class GUI extends JFrame {

    private JFrame jf = this;
    private ArrayList<Board> solution;
    private Grid grid;
    public int solutionCounter = 1;

    public GUI(String name, ArrayList<Board> solution) {
        setTitle(name);
        this.solution = solution;
        this.setLayout(new BorderLayout());
        grid = new Grid(solution.get(0).getBoard().length);
        add(grid);

        //add(new JLabel("BOARD"));
//		this.setFocusable(true);
    }

    class Grid extends JPanel {

        private Color c = Color.BLACK;
        private Timer newTimer;
        private int n;
        private JButton jButton1;
        private ArrayList<Cell> fillCells;

        public Grid(int n) {
            this.n = n;

            fillCells = new ArrayList<>(n * n);
            setLayout(new FlowLayout());
            jButton1 = new JButton("Start Solving");
            add(jButton1);
            int[][] board = solution.get(0).getBoard();
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    fillCell(i, j, board[i][j]);
                }
            }

            //newTimer.start();
            repaint();

            newTimer = new Timer(500, paintTimerAction);
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });
        }

        private void jButton1ActionPerformed(ActionEvent evt) {
            if(jButton1.getText().equals("Done!")){
                //System.exit(0);
                jf.dispose();
            }else if (newTimer.isRunning()) {
                jButton1.setText("Resume");
                newTimer.stop();
            } else {
                jButton1.setText("Pause");
                newTimer.start();
            }
            
            //JOptionPane.showMessageDialog(null, "Started");
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Cell fillCell : fillCells) {
                int cellX = 50 + n * 2 + (fillCell.x * 40);
                int cellY = 40 + n * 2 + (fillCell.y * 40);
                g.setColor(fillCell.c);
                g.fillRect(cellX, cellY, 40, 40);
                g.setColor(Color.BLACK);
                g.drawRect(cellX, cellY, 40, 40);
            }
        }

        public void fillCell(int x, int y, int col) {
            fillCells.add(new Cell(x, y, col));
            repaint();
        }

        Action paintTimerAction = new AbstractAction() { // functionality of our timer
            public void actionPerformed(ActionEvent e) {
                if (solutionCounter < solution.size()) {
                    int[][] board = solution.get(solutionCounter).getBoard();
                    for (int i = 0; i < board.length; i++) {
                        for (int j = 0; j < board.length; j++) {
                            grid.fillCell(i, j, board[i][j]);
                        }
                    }
                    solutionCounter++;
                }
                if(solutionCounter == solution.size()){
                    newTimer.stop();
                    jButton1.setText("Done!");
                }
                repaint();
            }
        };
    }
}

class Cell extends Point {
    public Color c;
    Cell(int x, int y, int color) {
        this.x = x;
        this.y = y;
        if (color == 1) {
            this.c = Color.MAGENTA;
        }
        if (color == 2) {
            this.c = Color.BLUE;
        }
        if (color == 3) {
            this.c = Color.RED;
        }
        if (color == 4) {
            this.c = Color.YELLOW;
        }
        if (color == 5) {
            this.c = Color.GREEN;
        }
        if (color == 6) {
            this.c = Color.CYAN;
        }
    }
}

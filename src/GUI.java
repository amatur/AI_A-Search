
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

    private ArrayList<Board> solution;
    private Grid grid;
    public int solutionCounter = 0;

    public GUI(String name, ArrayList<Board> solution) {
        setTitle(name);
        this.solution = solution;
        this.setLayout(new BorderLayout());
        grid = new Grid(solution.get(0).getBoard().length);
        add(grid);
        grid.setLayout(new FlowLayout());
        
        grid.add(new JButton("Start"));
        //add(new JLabel("BOARD"));
//		this.setFocusable(true);
    }

    class Grid extends JPanel {

        private Color c = Color.BLACK;
        private Timer newTimer;
        private int n;
        private ArrayList<Cell> fillCells;

        public Grid(int n) {
            this.n = n;
            fillCells = new ArrayList<>(n * n);
            newTimer = new Timer(1000, paintTimerAction);
            newTimer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Cell fillCell : fillCells) {
                int cellX = 10 + (fillCell.x *40);
                int cellY = 10 + (fillCell.y *40);
                g.setColor(fillCell.c);
                g.fillRect(cellX, cellY, 40, 40);
               g.setColor(Color.BLACK);
               g.drawRect(cellX, cellY, 40, 40);
            }

//            for (int i = 10; i <= 800; i += 10) {
//                g.drawLine(i, 10, i, 510);
//            }
//            for (int i = 10; i <= 500; i += 10) {
//                g.drawLine(10, i, 810, i);
//            }
        }

        public void fillCell(int x, int y, int col) {
            fillCells.add(new Cell(x, y, col));
            repaint();
        }

        Action paintTimerAction = new AbstractAction() { // functionality of our timer:
            public void actionPerformed(ActionEvent e) {
			// set X and Y co-ordinates that will then be fetched when drawing
                // the ball Image on the JPanel.
                //currentBall.setX(currentBall.getX() + 5);
                //currentBall.setY(currentBall.getY() + 5);
                //time agaile increment solution counter

                if (solutionCounter < solution.size()) {
                    int[][] board = solution.get(solutionCounter).getBoard();
                    for (int i = 0; i < board.length; i++) {
                        for (int j = 0; j < board.length; j++) {
                            grid.fillCell(i, j, board[i][j]);
                        }
                    }
                    solutionCounter++;
                }
                //newTimer.start();
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

//
//class MainPanel extends JPanel { // inherit JPanel
//
//	// Constructor:-----------------------------------------------------
//	public MainPanel() {
////		setDoubleBuffered(true);
//		new Timer(15, paintTimer).start();
//	}
//
//	// ----------------------------------------------------------------
//
//	public void paint(Graphics g) {
//		super.paint(g);
//		Graphics2D g2d = (Graphics2D) g;
//		//g2d.drawImage(), currentBall.getX(), currentBall.getY(), this); //Draws the ball Image at the correct X and Y co-ordinates.									
//		g2d.drawRect(0,0, 40, 40);
//                
//                //Toolkit.getDefaultToolkit().sync(); // necessary for linux users to draw  and animate image correctly
//		g.dispose();
//
//	}
//
//	Action paintTimer = new AbstractAction() { // functionality of our timer:
//		public void actionPerformed(ActionEvent e) {
//			// set X and Y co-ordinates that will then be fetched when drawing
//			// the ball Image on the JPanel.
//			//currentBall.setX(currentBall.getX() + 5);
//			//currentBall.setY(currentBall.getY() + 5);
//			//time agaile increment solution counter
//                        repaint();
//		}
//	};
//}

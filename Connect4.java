import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Connect4 {

        /**
        *       Program:        Connect4.java
        *       Purpose:        Stacking disk game for 2 players
        *       Creator:        Aaditya Sharma
        *       Created:        10.01.2015
        *       Modified:       13.02.2015 (JFrame)
        */   

        public static void main(String[] args) {
                Connect4JFrame frame = new Connect4JFrame();
                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
        }
}

class Connect4JFrame extends JFrame implements ActionListener {

        private Button          btn1, btn2, btn3, btn4, btn5, btn6, btn7;
        private Label           lblSpacer;
        MenuItem                newMI, exitMI, redMI, yellowMI;
        int[][]                 theArray;
        boolean                 end=false;
        boolean                 gameStart;
        public static final int BLANK = 0;
        public static final int RED = 1;
        public static final int YELLOW = 2;

        public static final int MAXROW = 6;     // 6 rows
        public static final int MAXCOL = 7;     // 7 columns

        public static final String SPACE = "                  "; // 18 spaces

        int activeColour = RED;
      
        public Connect4JFrame() {
                setTitle("Connect4 by AADITYA SHARMA");
                MenuBar mbar = new MenuBar();
                Menu fileMenu = new Menu("File");
                newMI = new MenuItem("New");
                newMI.addActionListener(this);
                fileMenu.add(newMI);
                exitMI = new MenuItem("Exit");
                exitMI.addActionListener(this);
                fileMenu.add(exitMI);
                mbar.add(fileMenu);
                Menu optMenu = new Menu("Options");
                redMI = new MenuItem("Red starts");
                redMI.addActionListener(this);
                optMenu.add(redMI);
                yellowMI = new MenuItem("Yellow starts");
                yellowMI.addActionListener(this);
                optMenu.add(yellowMI);
                mbar.add(optMenu);
                setMenuBar(mbar);

                // Build control panel.
                Panel panel = new Panel();

                btn1 = new Button("1");
                btn1.addActionListener(this);
                panel.add(btn1);
                lblSpacer = new Label(SPACE);
                panel.add(lblSpacer);

                btn2 = new Button("2");
                btn2.addActionListener(this);
                panel.add(btn2);
                lblSpacer = new Label(SPACE);
                panel.add(lblSpacer);

                btn3 = new Button("3");
                btn3.addActionListener(this);
                panel.add(btn3);
                lblSpacer = new Label(SPACE);
                panel.add(lblSpacer);

                btn4 = new Button("4");
                btn4.addActionListener(this);
                panel.add(btn4);
                lblSpacer = new Label(SPACE);
                panel.add(lblSpacer);

                btn5 = new Button("5");
                btn5.addActionListener(this);
                panel.add(btn5);
                lblSpacer = new Label(SPACE);
                panel.add(lblSpacer);

                btn6 = new Button("6");
                btn6.addActionListener(this);
                panel.add(btn6);
                lblSpacer = new Label(SPACE);
                panel.add(lblSpacer);

                btn7 = new Button("7");
                btn7.addActionListener(this);
                panel.add(btn7);

                add(panel, BorderLayout.NORTH);
                initialize();
                // Set to a reasonable size.
                setSize(1024, 768);
        } // Connect4

        public void initialize() {
                theArray=new int[MAXROW][MAXCOL];
                for (int row=0; row<MAXROW; row++)
                        for (int col=0; col<MAXCOL; col++)
                                theArray[row][col]=BLANK;
                gameStart=false;
        } // initialize

        public void paint(Graphics g) {
                g.setColor(Color.BLUE);
                g.fillRect(110, 50, 100+100*MAXCOL, 100+100*MAXROW);
                for (int row=0; row<MAXROW; row++)
                        for (int col=0; col<MAXCOL; col++) {
                                if (theArray[row][col]==BLANK) g.setColor(Color.WHITE);
                                if (theArray[row][col]==RED) g.setColor(Color.RED);
                                if (theArray[row][col]==YELLOW) g.setColor(Color.YELLOW);
                                g.fillOval(160+100*col, 100+100*row, 100, 100);
                        }
                check4(g);
        } // paint

        public void putDisk(int n) {
        // put a disk on top of column n
                // if game is won, do nothing
                if (end) return;
                gameStart=true;
                int row;
                n--;
                for (row=0; row<MAXROW; row++)
                        if (theArray[row][n]>0) break;
                if (row>0) {
                        theArray[--row][n]=activeColour;
                        if (activeColour==RED)
                                activeColour=YELLOW;
                        else
                                activeColour=RED;
                        repaint();
                }
        }

        public void displayWinner(Graphics g, int n) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Courier", Font.BOLD, 100));
                if (n==RED)
                        g.drawString("Red wins!", 100, 400);
                else
                        g.drawString("Yellow wins!", 100, 400);
                end=true;
        }

        public void check4(Graphics g) {
        // see if there are 4 disks in a row: horizontal, vertical or diagonal
                // horizontal rows
                for (int row=0; row<MAXROW; row++) {
                        for (int col=0; col<MAXCOL-3; col++) {
                                int curr = theArray[row][col];
                                if (curr>0
                                 && curr == theArray[row][col+1]
                                 && curr == theArray[row][col+2]
                                 && curr == theArray[row][col+3]) {
                                        displayWinner(g, theArray[row][col]);
                                }
                        }
                }
                // vertical columns
                for (int col=0; col<MAXCOL; col++) {
                        for (int row=0; row<MAXROW-3; row++) {
                                int curr = theArray[row][col];
                                if (curr>0
                                 && curr == theArray[row+1][col]
                                 && curr == theArray[row+2][col]
                                 && curr == theArray[row+3][col])
                                        displayWinner(g, theArray[row][col]);
                        }
                }
                // diagonal lower left to upper right
                for (int row=0; row<MAXROW-3; row++) {
                        for (int col=0; col<MAXCOL-3; col++) {
                                int curr = theArray[row][col];
                                if (curr>0
                                 && curr == theArray[row+1][col+1]
                                 && curr == theArray[row+2][col+2]
                                 && curr == theArray[row+3][col+3])
                                        displayWinner(g, theArray[row][col]);
                        }
                }
                // diagonal upper left to lower right
                for (int row=MAXROW-1; row>=3; row--) {
                        for (int col=0; col<MAXCOL-3; col++) {
                                int curr = theArray[row][col];
                                if (curr>0
                                 && curr == theArray[row-1][col+1]
                                 && curr == theArray[row-2][col+2]
                                 && curr == theArray[row-3][col+3])
                                        displayWinner(g, theArray[row][col]);
                        }
                }
        } // end check4

        public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btn1)
                        putDisk(1);
                else if (e.getSource() == btn2)
                        putDisk(2);
                else if (e.getSource() == btn3)
                        putDisk(3);
                else if (e.getSource() == btn4)
                        putDisk(4);
                else if (e.getSource() == btn5)
                        putDisk(5);
                else if (e.getSource() == btn6)
                        putDisk(6);
                else if (e.getSource() == btn7)
                        putDisk(7);
                else if (e.getSource() == newMI) {
                        end=false;
                        initialize();
                        repaint();
                } else if (e.getSource() == exitMI) {
                        System.exit(0);
                } else if (e.getSource() == redMI) {
                        // don't change colour to play in middle of game
                        if (!gameStart) activeColour=RED;
                } else if (e.getSource() == yellowMI) {
                        if (!gameStart) activeColour=YELLOW;
                }
        } // end ActionPerformed

} // class
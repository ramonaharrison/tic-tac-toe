package nyc.c4q.ramonaharrison.tictactoe;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBoard();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        MenuItem item = menu.add("New Game");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        setBoard();

        return super.onOptionsItemSelected(item);
    }

    int cell[][];
    Button button[][];
    TextView dialog;
    AI ai;
    int o = 0;
    int x = 1;
    int e = 2;

    private void setBoard() {

        ai = new AI();

        // initialize and populate 2D button array
        button = new Button[4][4];

        button[1][1] = (Button) findViewById(R.id.one);
        button[1][2] = (Button) findViewById(R.id.two);
        button[1][3] = (Button) findViewById(R.id.three);

        button[2][1] = (Button) findViewById(R.id.four);
        button[2][2] = (Button) findViewById(R.id.five);
        button[2][3] = (Button) findViewById(R.id.six);

        button[3][1] = (Button) findViewById(R.id.seven);
        button[3][2] = (Button) findViewById(R.id.eight);
        button[3][3] = (Button) findViewById(R.id.nine);


        // initialize and populate 2D cell array with 'empty' values
        cell = new int[4][4];

        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                cell[i][j] = e;
            }
        }

        dialog = (TextView) findViewById(R.id.dialog);
        dialog.setText("Your turn.");

        // click listeners
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                button[i][j].setOnClickListener(new MyClickListener(i, j));
                if(!button[i][j].isEnabled()) {
                    button[i][j].setText(" ");
                    button[i][j].setEnabled(true);
                }
            }
        }
    }

    class MyClickListener implements View.OnClickListener {

        int x;
        int y;

        public MyClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void onClick(View view) {
            if (button[x][y].isEnabled()) {
                button[x][y].setEnabled(false);
                button[x][y].setText("X");
                cell[x][y] = 1;
                dialog.setText("X");
                if (!checkBoard()) {
                    ai.takeTurn();
                }
            }
        }

    }

    private class AI {
        public void takeTurn() {
            if (cell[1][1] == 2 &&
                    ((cell[1][2] == 1 && cell[1][3] == 1) ||
                            (cell[2][2] == 1 && cell[3][3] == 1) ||
                            (cell[2][1] == 1 && cell[3][1] == 1))) {
                markSquare(1, 1);
            } else if (cell[1][2] == 2 &&
                    ((cell[2][2] == 1 && cell[3][2] == 1) ||
                            (cell[1][1] == 1 && cell[1][3] == 1))) {
                markSquare(1, 2);
            } else if (cell[1][3] == 2 &&
                    ((cell[1][1] == 1 && cell[1][2] == 1) ||
                            (cell[3][1] == 1 && cell[2][2] == 1) ||
                            (cell[2][3] == 1 && cell[3][3] == 1))) {
                markSquare(1, 3);
            } else if (cell[2][1] == 2 &&
                    ((cell[2][2] == 1 && cell[2][3] == 1) ||
                            (cell[1][1] == 1 && cell[3][1] == 1))) {
                markSquare(2, 1);
            } else if (cell[2][2] == 2 &&
                    ((cell[1][1] == 1 && cell[3][3] == 1) ||
                            (cell[1][2] == 1 && cell[3][2] == 1) ||
                            (cell[3][1] == 1 && cell[1][3] == 1) ||
                            (cell[2][1] == 1 && cell[2][3] == 1))) {
                markSquare(2, 2);
            } else if (cell[2][3] == 2 &&
                    ((cell[2][1] == 1 && cell[2][2] == 1) ||
                            (cell[1][3] == 1 && cell[3][3] == 1))) {
                markSquare(2, 3);
            } else if (cell[3][1] == 2 &&
                    ((cell[1][1] == 1 && cell[2][1] == 1) ||
                            (cell[3][2] == 1 && cell[3][3] == 1) ||
                            (cell[2][2] == 1 && cell[1][3] == 1))) {
                markSquare(3, 1);
            } else if (cell[3][2] == 2 &&
                    ((cell[1][2] == 1 && cell[2][2] == 1) ||
                            (cell[3][1] == 1 && cell[3][3] == 1))) {
                markSquare(3, 2);
            } else if (cell[3][3] == 2 &&
                    ((cell[1][1] == 1 && cell[2][2] == 1) ||
                            (cell[1][3] == 1 && cell[2][3] == 1) ||
                            (cell[3][1] == 1 && cell[3][2] == 1))) {
                markSquare(3, 3);
            } else {
                Random rand = new Random();

                int a = rand.nextInt(4);
                int b = rand.nextInt(4);
                while (a == 0 || b == 0 || cell[a][b] != 2) {
                    a = rand.nextInt(4);
                    b = rand.nextInt(4);
                }
                markSquare(a, b);
            }
        }

        private void markSquare(int x, int y) {
            button[x][y].setEnabled(false);
            button[x][y].setText("O");
            cell[x][y] = 0;
            checkBoard();
        }
    }

    // check the board to see if someone has won
    private boolean checkBoard() {
        boolean gameOver = false;
        if ((cell[1][1] == 0 && cell[2][2] == 0 && cell[3][3] == 0)
                || (cell[1][3] == 0 && cell[2][2] == 0 && cell[3][1] == 0)
                || (cell[1][2] == 0 && cell[2][2] == 0 && cell[3][2] == 0)
                || (cell[1][3] == 0 && cell[2][3] == 0 && cell[3][3] == 0)
                || (cell[1][1] == 0 && cell[1][2] == 0 && cell[1][3] == 0)
                || (cell[2][1] == 0 && cell[2][2] == 0 && cell[2][3] == 0)
                || (cell[3][1] == 0 && cell[3][2] == 0 && cell[3][3] == 0)
                || (cell[1][1] == 0 && cell[2][1] == 0 && cell[3][1] == 0)) {
            dialog.setText("Game over. You lost!");
            gameOver = true;
        } else if ((cell[1][1] == 1 && cell[2][2] == 1 && cell[3][3] == 1)
                || (cell[1][3] == 1 && cell[2][2] == 1 && cell[3][1] == 1)
                || (cell[1][2] == 1 && cell[2][2] == 1 && cell[3][2] == 1)
                || (cell[1][3] == 1 && cell[2][3] == 1 && cell[3][3] == 1)
                || (cell[1][1] == 1 && cell[1][2] == 1 && cell[1][3] == 1)
                || (cell[2][1] == 1 && cell[2][2] == 1 && cell[2][3] == 1)
                || (cell[3][1] == 1 && cell[3][2] == 1 && cell[3][3] == 1)
                || (cell[1][1] == 1 && cell[2][1] == 1 && cell[3][1] == 1)) {
            dialog.setText("Game over. You won!");
            gameOver = true;
        } else {
            boolean empty = false;
            for(int i = 1; i <= 3; i++) {
                for(int j = 1; j <= 3; j++) {
                    if(cell[i][j] == 2) {
                        empty = true;
                        break;
                    }
                }
            }
            if(!empty) {
                gameOver = true;
        dialog.setText("Game over. It's a draw!");
            }
        }
        return gameOver;
    }
}




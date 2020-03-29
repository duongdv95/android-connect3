package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
//import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    // 0 = cat
    // 1 = dog
    // 5 = empty
    int activePlayer = 0;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningCombinations = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean gameOver = false;

    public void replay(View view) {
        Button replayButton = (Button) findViewById(R.id.replayButton);
        TextView showWinner = (TextView) findViewById(R.id.displayWinner);
        showWinner.setText("");
        replayButton.setVisibility(View.INVISIBLE);
        androidx.gridlayout.widget.GridLayout grid = findViewById(R.id.gridLayout);
//        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout);
        for(int i = 0 ; i < grid.getChildCount() ; i++){
            ImageView child = (ImageView) grid.getChildAt(i);
            child.setImageDrawable(null);
        }
        gameState = new int[] {2,2,2,2,2,2,2,2,2};
        gameOver = false;
    }

    public void placeToken(View view) {
        if(gameOver) return;
        ImageView token = (ImageView) view;
        TextView showWinner = (TextView) findViewById(R.id.displayWinner);
        Button replayButton = (Button) findViewById(R.id.replayButton);
        int tokenNum = Integer.parseInt(token.getTag().toString());
        if(gameState[tokenNum] != 2) return;
        gameState[tokenNum] = activePlayer;
        token.setTranslationY(-1500);
        if(activePlayer == 0) {
            activePlayer = 1;
            token.setImageResource(R.drawable.cat);
        } else {
            activePlayer = 0;
            token.setImageResource(R.drawable.dog);
        }
        token.animate().translationYBy(1500).setDuration(300);
        Log.i("tokenString", Arrays.toString(gameState));
        for( int[] winningSet : winningCombinations) {
            if(gameState[winningSet[0]] == gameState[winningSet[1]] && gameState[winningSet[1]] == gameState[winningSet[2]] && gameState[winningSet[0]] != 2) {
                String winnerString = (gameState[winningSet[0]] == 0) ? "Cat has won!" : "Dog has won!";
                gameOver = true;
                replayButton.setVisibility(View.VISIBLE);
                showWinner.setText(winnerString);
                Toast.makeText(this, winnerString, Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button replayButton = (Button) findViewById(R.id.replayButton);
        replayButton.setVisibility(View.GONE);
    }
}

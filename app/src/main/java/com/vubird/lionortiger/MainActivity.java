package com.vubird.lionortiger;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum Player {

        ONE,TWO,NO
    }

    GridLayout gl;
    ImageView bullPlayer, bearPlayer;
    TextView tv;
    boolean fadedValue = false;
    boolean decided = false;

    Player PlayerChoice[] = new Player[9];
    int [][] winnerArr = {{0,1,2},{3,4,5},{6,7,8},
                              {0,3,6},{1,4,7},{2,5,8},
                              {0,4,8},{2,4,6}};

    Player currentPlayer = Player.ONE;
    boolean clear = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        gl = findViewById(R.id.gridLayout);
        bullPlayer = findViewById(R.id.bullplayerImage);
        bearPlayer = findViewById(R.id.bearPlayerImg);


        for(int i=0; i < PlayerChoice.length ; i++)
        {
            PlayerChoice[i]= Player.NO;
        }
    }

    public void ImageViewIsTapped(View imageView)
    {
        ImageView tappedImageView = (ImageView)imageView;
        int tiTag = Integer.parseInt(tappedImageView.getTag().toString());

        if(decided == true)
        {
            Toast.makeText(this, "Game Ended", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!(PlayerChoice[tiTag] == Player.NO))
        {
            Toast.makeText(this, "already done", Toast.LENGTH_SHORT).show();
            return;
        }
        PlayerChoice[tiTag] = currentPlayer;

        if(currentPlayer == Player.ONE)
        {
            tappedImageView.setTranslationY(2000);
            tappedImageView.setImageResource(R.drawable.bear);
            tappedImageView.animate().translationYBy(-2000).alpha(1).rotation(1800).setDuration(700);
            currentPlayer = Player.TWO;

            bullPlayer.setVisibility(View.VISIBLE);
            bullPlayer.animate().rotation(360).setDuration(5000);
            bearPlayer.setVisibility(View.INVISIBLE);
        }

        else if(currentPlayer == Player.TWO)
        {
            tappedImageView.setTranslationY(-2000);
            tappedImageView.setImageResource(R.drawable.bull);
            tappedImageView.animate().translationYBy(2000).alpha(1).rotation(1800).setDuration(700);
            currentPlayer = Player.ONE;

            bearPlayer.setVisibility(View.VISIBLE);
            bearPlayer.animate().rotation(360).setDuration(5000);
            bullPlayer.setVisibility(View.INVISIBLE);
        }

        for (int[] winnerColumns : winnerArr)
        {
                if (PlayerChoice[winnerColumns[1]] == PlayerChoice[winnerColumns[0]] && PlayerChoice[winnerColumns[1]] == PlayerChoice[winnerColumns[2]]&&!(PlayerChoice[winnerColumns[1]] == Player.NO))
                {
                    Toast.makeText(this, "Winning" , Toast.LENGTH_SHORT).show();

                    decided = true;

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    if(currentPlayer == Player.TWO)
                        alertDialogBuilder.setMessage("Player "+Player.ONE+ " Won");
                    else
                        alertDialogBuilder.setMessage("Player "+Player.TWO+ " Won");





                    alertDialogBuilder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            for(int j= 0; j< gl.getChildCount();j++)
                            {
                                Toast.makeText(MainActivity.this,""+ gl.getChildAt(0).getTag().toString(), Toast.LENGTH_SHORT).show();
                                ImageView imageView = (ImageView) gl.getChildAt(j);
                                imageView.setImageDrawable(null);
                            }

                            for(int k=0;k<PlayerChoice.length;k++)
                                PlayerChoice[k] = Player.NO;

                            decided = false;

                        }

                    });


                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();


                }
        }
    }
}

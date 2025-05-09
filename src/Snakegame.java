

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;



public class Snakegame  extends JPanel implements ActionListener, KeyListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            gameloop.stop();
        }
    }
    public void move(){
        if(collision(snakehead,food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }
        for(int i = snakeBody.size()-1; i>=0; i--){
            Tile snakePart = snakeBody.get(i);
            if(i== 0){
                snakePart.x = snakehead.x;
                snakePart.y = snakehead.y;
            }
            else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        snakehead.x += velocityX;
        snakehead.y += velocityY;

        for(int i = 0; i<snakeBody.size();i++){
            Tile snakePart = snakeBody.get(i);
            if(collision(snakehead, snakePart)){
                gameOver = true;
            }
        }
        if(snakehead.x*titlesize <0 || snakehead.x*titlesize > boardwidth || snakehead.y*titlesize <0 || snakehead.y*titlesize > boardheight){
            gameOver = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != -1){
            velocityX = 0;
            velocityY = -1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1){
            velocityX= 0;
            velocityY= 1;

        }
        else if(e.getKeyCode()== KeyEvent.VK_LEFT && velocityX != -1){
            velocityX= -1;
            velocityY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX =1;
            velocityY = 0;

        }    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private class Tile {
        int x;
        int y;
        Tile (int x , int y){
            this.x = x;
            this.y = y;
        }
    }
    int boardwidth;
    int boardheight;
    int titlesize = 25;
    Tile snakehead;
    ArrayList<Tile> snakeBody;

    Tile food;
    Random random;

    Timer gameloop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    Snakegame(int boardwidth, int boardheight){
        this.boardheight = boardheight;
        this.boardwidth = boardwidth;
        setPreferredSize(new Dimension(this.boardwidth, this.boardheight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakehead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();
        food = new Tile(10,10);
        random = new Random();
        placeFood();
        velocityX = 0;
        velocityY = 1;

        gameloop = new Timer(100,this);
        gameloop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){


        g.setColor(Color.red);
//        g.fillRect(food.x*titlesize, food.y*titlesize,titlesize,titlesize);
        g.fill3DRect(food.x*titlesize, food.y*titlesize,titlesize,titlesize, true);
        g.setColor(Color.green);
//        g.fillRect(snakehead.x *titlesize,snakehead.y * titlesize,titlesize , titlesize);
        g.fill3DRect(snakehead.x *titlesize,snakehead.y * titlesize,titlesize , titlesize, true);
        for(int i = 0; i< snakeBody.size();i++){
            Tile snakePart = snakeBody.get(i);
//             g.fillRect(snakePart.x*titlesize, snakePart.y*titlesize, titlesize, titlesize);
            g.fill3DRect(snakePart.x*titlesize, snakePart.y*titlesize, titlesize, titlesize, true);
        }
        g.setFont(new Font ("Arial",Font.PLAIN,16));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), titlesize - 16, titlesize);

        }
        else{
            g.drawString("Score: "  + String.valueOf(snakeBody.size()), titlesize - 16, titlesize);
        }

    }

    public void placeFood()
    {
        food.x = random.nextInt(boardwidth/titlesize);
        food.y = random.nextInt(boardheight/titlesize);
    }
    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;

    }
}


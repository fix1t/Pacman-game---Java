package ija.ija2022.homework1.game;

import ija.ija2022.homework1.common.Maze;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MazeConfigure {
    boolean pacmanPlaced;
    boolean started;
    int rows;
    int cols;
    boolean errorFlag;
    List<String> configuration = new ArrayList<String>();
    //constructor
    public MazeConfigure(){
        this.pacmanPlaced = false;
        this.rows = 0;
        this.cols = 0;
        this.started = false;
        this.errorFlag = false;
    }

    public void startReading(int rows, int cols) {
        int border = 0;
        this.rows = rows+border;
        this.cols = cols+border;
        this.started = true;
    }
    public boolean processLine(String line) {
        if (!this.started || this.cols != line.length()){
            this.errorFlag = true;
            return  false;
        }

        if (this.configuration.size() >= this.rows){
            this.errorFlag = true;
            return false;
        }

        //check allowed chars & only one occurrence of S
        String allowedCharsRegex = "^[X.]*S?[X.]*$";
        Pattern p = Pattern.compile(allowedCharsRegex);
        Matcher m = p.matcher(line);
        if (!m.matches()) {
            this.errorFlag = true;
            return false;
        }
        //check if pacman was placed
        String containsStart = "^[X.]*S?[X.]*$";
        p = Pattern.compile(containsStart);
        m = p.matcher(line);
        if (m.matches()){
            this.errorFlag = true;
            this.pacmanPlaced=true;
        }

        this.configuration.add(line);
        return true;
    }



    public boolean stopReading() {
        return this.configuration.size() == this.rows && this.started && !this.errorFlag;
    }

    public Maze createMaze() {
        Maze
    }
}

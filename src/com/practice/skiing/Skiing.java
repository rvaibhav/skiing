package com.practice.skiing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by vaibhavr on 21/04/16.
 */
public class Skiing {

    private int longestDip = 0;

    private int longestLength = 0;

    Integer[] dimensions = new Integer[2];

    Integer[][] altitudes;

    private static final String SPACE = " ";

    public static void main(String[] args) throws MalformedURLException {
        Skiing skiing = new Skiing();
        skiing.readAltitudesFromUrl();
        skiing.printArray();
        skiing.calculateDipAndLength();
        System.out.println("Longest length is " + skiing.longestLength);
        System.out.println("Longest dip is " + skiing.longestDip);
    }

    private void readAltitudesFromUrl() throws MalformedURLException {
        URL mapUrl = new URL("http://s3-ap-southeast-1.amazonaws.com/geeks.redmart.com/coding-problems/map.txt");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(mapUrl.openStream()));
            String inputLine;
            int i = 0;
            while ((inputLine = in.readLine()) != null) {
                if(i == 0){
                    String[] rowCols = inputLine.split(SPACE);
                    dimensions[0] = Integer.valueOf(rowCols[0]);
                    dimensions[1] = Integer.valueOf(rowCols[1]);
                    initializeArray();
                }else{
                    String[] alts = inputLine.split(SPACE);
                    for(int j=0;j<alts.length;j++){
                        if((i-1)<dimensions[0]) {
                            altitudes[i-1][j] = Integer.valueOf(alts[j]);
                        }
                    }
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeArray(){
        altitudes = new Integer[dimensions[0]][dimensions[1]];
        for(int i = 0; i<dimensions[0];i++){
            altitudes[i] = new Integer[dimensions[1]];
        }
    }

    private void printArray(){
        for(int i = 0; i<dimensions[0];i++){
            for(int j = 0; j<dimensions[1];j++) {
                System.out.print(altitudes[i][j] + " ");
            }
            System.out.println("\n");
        }
    }

    private void calculateDipAndLength(){
        for(int i = 0; i<dimensions[0];i++){
            for(int j = 0; j<dimensions[1];j++) {
                findLongestPath(i,j,0,0);
            }
        }
    }

    private void findLongestPath(int currentRow, int currentCol, int length, int dip){
        int topRow = currentRow -1;
        int topCol = currentCol;
        int rightRow = currentRow;
        int rightCol = currentCol + 1;
        int bottonRow = currentRow + 1;
        int bottomCol = currentCol;
        int leftRow = currentRow;
        int leftCol = currentCol - 1;
        Integer currentElement = altitudes[currentRow][currentCol];
        length++;
        boolean moreDepth = false;
        if(topRow >= 0 && currentElement > altitudes[topRow][topCol]){
            moreDepth = true;
            int nextDip = currentElement - altitudes[topRow][topCol];
            findLongestPath(topRow, topCol, length, dip + nextDip);
        }
        if(rightCol < dimensions[1] && currentElement > altitudes[rightRow][rightCol]){
            moreDepth = true;
            int nextDip = currentElement - altitudes[rightRow][rightCol];
            findLongestPath(rightRow, rightCol, length, dip + nextDip);
        }
        if(bottonRow < dimensions[0] && currentElement > altitudes[bottonRow][bottomCol]){
            moreDepth = true;
            int nextDip = currentElement - altitudes[bottonRow][bottomCol];
            findLongestPath(bottonRow, bottomCol, length, dip + nextDip);
        }
        if(leftCol >= 0 && currentElement > altitudes[leftRow][leftCol]){
            moreDepth = true;
            int nextDip = currentElement - altitudes[leftRow][leftCol];
            findLongestPath(leftRow, leftCol, length, dip + nextDip);
        }

        if(!moreDepth && (longestDip <= dip) && (longestLength <= length)){
            longestDip = dip;
            longestLength = length;
        }
    }

}

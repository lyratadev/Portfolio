/*
 * collect maximum number of coins on an n x m board
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


public class Main
{
    public static void main(String[] args) {
        /* test input
        int row = 5;
        int col = 6;
        int[][] data = {
                {0, 0, 0, 0, 1, 0},
                {0, 1, 0, 1, 0, 0},
                {0, 0, 0, 1, 0, 1},
                {0, 0, 1, 0, 0, 1},
                {1, 0, 0, 0, 1, 0}
        };

        int row = 3;
        int col = 2;
        int[][] data = {
                    {1,1},
                    {0,0},
                    {0,1}
        };
        

       int row = 4;
       int col = 2;
       int[][] data = {
               {0, 0},
               {0, 0},
               {0, 0},
               {1, 0}
       };
       */


        Scanner in = new Scanner(System.in);
        int row = in.nextInt();      //tableSize table size
        int col = in.nextInt();           //collect args #
        int[][] data = new int[row][col];

        for (int i = 0; i < row;i++) {
            for (int j = 0; j < col; j++){
                data[i][j]= in.nextInt();
            }
        }


        ArrayList<String> sb = new ArrayList<String>();
        int rowIndex = 1;
        int colIndex = 1;

        data = padMatrix(data, row, col);
        //printMatrix(data);

        int[][] pathMatrix;
        pathMatrix = fetchPath(data, row, col);
        //printMatrix(pathMatrix);
        sb = backTrack(pathMatrix, row, col);

        for(int i = 0; i < sb.size(); i++) {
            System.out.print(sb.get(i));
        }
        System.out.println();
    }


    //HELPER FUNCTIONS

    //WORKS BACKWARDS FROM LAST ELEMENT OF ARRAY AND COMPARES PATHS TO THE START, ADDING ELEMENTS TO ARRAYLIST THAT NETS MOST COINS
    public static ArrayList<String> backTrack(int[][] data,int row, int col) {
        ArrayList<String> sb = new ArrayList<String>();

        sb.add("(" + (row) + "," + (col) + ")");
        sb.add("->");

        int colIndex = col;
        int rowIndex = row;

        Boolean flag = true;
        while ( flag )
        {
            int topValue  = data[rowIndex - 1][colIndex]; //offset row -1
            int leftValue = data[rowIndex][(colIndex - 1)]; //offset col -1

            if (leftValue > topValue) {
                sb.add("(" + (rowIndex) + "," + (colIndex - 1) + ")");
                sb.add("->");
                colIndex--;
                //System.out.println(colIndex + "= colindex \n" + rowIndex + " rowIndex" );

            }
            else if (topValue > leftValue) {
                //ADD TOP VALUE
                sb.add("(" + (rowIndex - 1) + "," + (colIndex) + ")");
                sb.add("->");
                rowIndex--;
            }
            else if (topValue == leftValue) {
                sb.add("(" + (rowIndex) + "," + (colIndex - 1) + ")");
                sb.add("->");
                colIndex--;
            }

            if (colIndex == 1 && rowIndex == 1) {
                flag = false;
            }
        } //end WHILE

        sb.remove(sb.size()-1);
        sb.add("Path:");
        sb.add("Max coins:" + data[row][col] + "\n");
        Collections.reverse(sb);

        return sb;
    }

    //FETCHES THE MOST OPTIMAL PATH BY ASSIGNING VALUES TO ELEMENTS BASED ON COINS PICKED UP
    public static int[][] fetchPath(int[][] data,int row, int col){
        for (int rowIndex = 1; rowIndex < row+1; rowIndex++) {
            for (int colIndex=1; colIndex < col+1;colIndex++){
                int value = data[rowIndex][colIndex];
                int topValue = data[rowIndex-1][colIndex];
                int leftValue = data[rowIndex][colIndex-1];

                if (topValue > leftValue) {
                    data[rowIndex][colIndex] = value+topValue;
                } else if (leftValue > topValue) {
                    data[rowIndex][colIndex] = value+leftValue;
                } else {
                    if ( (leftValue != -1 && topValue != -1) ) {
                        //System.out.println(colIndex);
                        data[rowIndex][colIndex] = value + leftValue;
                    } //IF VALUE IS NOT NEGATIVE IN BUFFER ROW + COL, ADD LEFT VALUE
                }
            }
        }
        return data;
    }

    //PADS MATRIX WITH -1 VALUES FOR FIRST ROW AND FIRST COLUMN
    public static int[][] padMatrix(int[][] matrix, int row, int col)
    {
        //data[rows][cols]
        int[][] newMatrix = new int[row+1][col+1];
        //CREATE PADDING ROW AND COL
        for (int i = 0; i < newMatrix[0].length;i++) {
            newMatrix[0][i] = -1;
        }
        for (int j = 0; j< row+1;j++){
            newMatrix[j][0] = -1;
        }
        //FILL ARRAY WITH OLD DATA
        for (int i = 0; i<matrix.length;i++) {
            for (int j = 0; j<matrix[0].length;j++) {
                newMatrix[i+1][j+1] = matrix[i][j];
            }
        }
        return newMatrix;
    }

    //PRINTS THE MATRIX PASSED INTO IT
    public static void printMatrix(int[][] matrix)
    {
        System.out.println("\nMatrix ");
        for (int i=0;i<matrix.length;i++){
            System.out.println(Arrays.toString(matrix[i]));
        }
    }
}


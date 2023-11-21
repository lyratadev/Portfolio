/*
 * Abstract: Write a C++ (or Java) program for hw5_3 to
 * simulate the operations of linear probing.

/* TEST INPUT
      7
      7
      insert 100
      insert 16
      insert 37
      displayStatus 3
      displayStatus 2
      search 37
      tableSize
 */


import java.util.Arrays;
import java.util.Scanner;
import java.util.*;
import java.lang.Math;

class Main {
    public static Boolean firstRun = true;

    public static void main(String[] args) {
        // Develop your program here.

        Scanner in = new Scanner(System.in);
        int tableSize = in.nextInt();      //tableSize table size
        int arg = in.nextInt();           //collect args #
        String[] input = new String[arg]; //init array to store args
        //in = new Scanner(System.in);      //flush buffer
        //in.next();
        in.nextLine();
        for (int i = 0; i < arg; i++) {       //collect arguements in loop
            input[i] = in.nextLine();
        }
        //System.out.println(Arrays.toString(input));

        int hashKey = tableSize;
        int[] hashTable = new int[tableSize];
        double loadFactor = .5; //elements/arraysize

        int count = 0;
        for (int element : hashTable)
        {
            hashTable[count] = -1;
            count++;
        }

        for (int i = 0; i < arg; i++)//execute args depending on case
        {
            if (input[i].contains("insert")) {
                /* inserts a key to the table */
                int val = getInt(input[i]);
                hashTable = insert(hashTable, val);
                hashKey = hashTable.length;

            }
            if (input[i].contains("displayStatus")) {
                /*display the status of an entry in the table*/
                int index = getInt(input[i]);
                String result = displayStatus(index,hashTable);
                System.out.println(result);
            }
            if (input[i].equals("tableSize")) {
                System.out.println(hashTable.length);
            }
            if (input[i].contains("search")) {
                int val = getInt(input[i]);
                int valHash = val%hashTable.length;
                Boolean found = search(val, valHash, hashTable);

                if (found) {
                System.out.println(val + " Found");
                }
                else
                System.out.println(val + " Not found");
            }
            if (input[i].equals("display")) {
                System.out.println(Arrays.toString(hashTable));
            }
        }//end for
        
    }//END MAIN

    //RETURNS TRUE IF NUMBER IS PRIME
    public static Boolean isPrime(int number)
    {
        if (number > 1) {
            for(int i = 2; i <= number/2 ;i++) {
            if(number%i == 0)
            return false;
            }
            return true;
        }
        return false;
    }

    public static Boolean overLoad(int[] hashTable){
        //System.out.println("Overload function");

        Boolean flag = false;
        int count = 1;
        for (int element : hashTable)
        {
            if (element != -1){
                count++;
            }
        }

        //System.out.println((double)(count) / (hashTable.length));
        //System.out.println("Length: " + hashTable.length + "Count: " + count);
        if (count > 1) {
            if ( (double)(count) / (hashTable.length) > .5) {
                //System.out.println("Overload!");
                flag = true;
            }
        }
        return flag;
    }

    //DOUBLES TABLE AND FINDS NEAREST PRIME NUMBER AS ARRAY SIZE
    public static int[] resizeRekey(int[]hashTable, int hashKey)
    {
        hashKey = 2*hashKey; //derive new hashkey by doubling
        while (!(isPrime(hashKey))) //find new prime value for hashkey
        {
            hashKey++;
        }
        int[] newTable = new int[hashKey]; //once found init new array of new prime hashkey value

        int count = 0;
        for (int x : newTable)
        {
            newTable[count]=-1;
            count++;
        }

        count = 0;
        for (int index = 0; index < hashTable.length; index++) //check the load balance and copy over values
        {
            if (( hashTable[index] != -1 ))
            {
                count++;
                int newIndex = hashTable[index]%newTable.length;
                newTable[newIndex] = hashTable[index];
                //System.out.println("new index for after hashing with " + newTable.length + "index:" + newIndex);
            }
        }
        return newTable;
    }

    //INSERTS VALUE INTO HASHTABLE
    public static int[] insert(int[] hashTable, int val){
        //System.out.println("Underload?" + underLoad(hashTable));
        if ((overLoad(hashTable))){
            //System.out.println("Resizing");
            hashTable = resizeRekey(hashTable,hashTable.length).clone();
        }
        int newKey = val%hashTable.length;
        if (hashTable[newKey] != -1) //collision
        {
            for (int i = newKey; i < hashTable.length;i++)
            {
                if (hashTable[i] == -1){
                    hashTable[i] = val;
                    return hashTable;
                }
            }
            for (int j = newKey; j > 0; j--){
                if (hashTable[j] == -1){
                    hashTable[j] = val;
                    return hashTable;
                }
            }
        }
        if (hashTable[newKey] == -1) {
            hashTable[newKey] = val;
        }
        else
        {
            hashTable = resizeRekey(hashTable,hashTable.length).clone();
            hashTable = insert(hashTable, val).clone(); //recursively try again
        }
        return hashTable;
    }


    //DISPLAY INDEX ELEMENT OR EMPTY
    public static String displayStatus(int index, int[]hashTable) {
        if (index < hashTable.length) {
            int element = hashTable[index];
            if (element != -1) {
                return Integer.toString(element);
            }
        }
            return "Empty";
    }

    //SEARCH FOR ELEMENT IN ARRAY & RETURN TRUE IF FOUND
    public static Boolean search(int val, int valHash, int[] hashTable) {
        int element = hashTable[valHash];
        for (int each : hashTable)
        {
            if (each == val){
                return true;
            }
        }
        return false;
    }

    //FETCH INT VALUE FROM STRING
    public static int getInt(String input){
        char[] chars = input.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char x : chars){
            if(Character.isDigit(x)){
                sb.append(x);
            }
        }
        int val = Integer.parseInt(sb.toString());
        return val;
    }
}
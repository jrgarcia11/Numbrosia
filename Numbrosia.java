import java. util.*;
import java.io.*;

public class Numbrosia {

 public static void main(String[] args) {
   
  String command = null;
  Scanner scan = null;
  Scanner reader = new Scanner(System.in);
  int defaultGameSize = 5;
  int[][] game = new int[defaultGameSize][defaultGameSize];
  int rowNum = 0;
  int counter = 0;
  int choice = 0;
  boolean error = false;
  boolean done = false;
  
  //Error detector loop
  while(!error){
   System.out.println("1)Random puzzle or 2)5x5 input file? [Input either 1 or 2]:");
   try{
    choice = reader.nextInt();
   } catch(InputMismatchException q){
    error = true;
    System.out.println("Invalid input");
    return;
   }
   if(choice == 1 || choice ==2){
    error = true;
   }
   else{
    System.out.println("Invalid input");
    error = false;
   }
   
  }
  error = false;
  if(choice == 2){     //You can create a custom 5x5 grid with a .txt file and play it. 
   try {               //Save the .txt file in the same directory as Numbrosia so it can find it.
    scan=new Scanner(new File("input.txt"));
   } catch (FileNotFoundException e) {
    e.printStackTrace();
    return;
   }
  
   for(int row = 0; row < game.length; row++){
    for(int col = 0; col < game[row].length; col++){
     game[row][col] = scan.nextInt();
    }
   }
  }
  if(choice == 1){
   int puzzleSize = 0;
   int difficulty = 0;
   while(!error){
    System.out.println("Puzzle size [Example: entering a 3 will create a 3x3 puzzle]: ");
    try{
    puzzleSize = reader.nextInt();
    } catch(InputMismatchException w){
     error = false;
     System.out.println("Invalid input");
     return;
    }
    if(puzzleSize <= 0){
     error = false;
     System.out.println("Puzzles cannot be 0 or negative, dummy!");
    }
    else{
     error = true;
    }
   }
   error = false;
   while(!error){
    System.out.println("Puzzle difficulty[1-Easy, 2-Medium, 3-Hard] Input either 1, 2, or 3: ");
    try{
    difficulty = reader.nextInt();
    } catch(InputMismatchException y){
     error = false;
     System.out.println("Invalid input");
     return;
    }
    if(difficulty <= 0 || difficulty > 3){
     error = false;
     System.out.println("Invalid input");
    }
    else{
     error = true;
    }
   }
   error = false;
   game = random(puzzleSize, difficulty);
  }
  System.out.println("Game Start! " +
    "\n \nCommands:" +
    "\n i rr for rotate right row i \n i rl for rotate left row i " +
    "\n i ru for rotate up column i \n i rd for rotate down column i " +
    "\n i +r for add 1 to row i \n i +c for add 1 to column i " +
    "\n i -r for subtract 1 from row i \n i -c for subtract 1 from column i" + 
    "\n\n Objective: All numbers on the grid must be 0. Good luck!");
  while(!done){
   gamePrinter(game);
   while(!error){
    System.out.println("Enter command: ");
    try{
    rowNum = reader.nextInt();
    } catch (InputMismatchException x){
     error = false;
    }
    try{
     command = reader.next();
     } catch (InputMismatchException x){
      error = false;
     }
    if(commandError(game, rowNum, command)==true){
     error = false;
     System.out.println("Invalid command");
    }
    else{
     error = true;
    }
   }
   error = false;
   game = commandReader(game, rowNum-1, command);
   counter++;
   if(checker(game) == true){
    done = true;
   }
  }
  if(done){
   gamePrinter(game);
   System.out.println("CONGRATULATIONS! YOU WIN!");  
   System.out.println("Number of moves: " + counter);
  }
}
 
 /*----------Helper methods----------*/
 public static boolean checker(int[][] a){
  boolean complete = false;
  int size = a.length*a.length;
  int count = 0;
  for(int row = 0; row < a.length; row++){
   for(int col = 0; col <a[row].length; col++){
    if(a[row][col]== 0){
     count++;
    }
   }
  }
  if(count == size){
   complete = true;
  }
  return complete;
 }
 public static boolean commandError(int[][] a, int b, String c){
  boolean err = false;
  if(c.equals("+r") || c.equals("-r") || c.equals("+c") || c.equals("-c")||
     c.equals("rd") || c.equals("ru") || c.equals("rl") || c.equals("rr")){
   err = false;
  }
  else{
   err = true;
  }
  if(b > a.length || b < 0){
   err = true;
  }
  return err;
 }
 public static void gamePrinter(int[][] a){
  for(int row = 0; row < a.length; row++){
   for(int col = 0; col < a[row].length; col++){
    if(a[row][col]<0){
     System.out.print(a[row][col] + " ");
    }
    else{
    System.out.print(" " + a[row][col] + " ");
    }
   }
   System.out.println("");
  }
 }
 public static int[][] commandReader(int[][] a, int b, String c){
  int[][] newGame = null;
  if(c.equals("rr")){
   newGame = rotateRight(a, b);
  }
  if(c.equals("rl")){
   newGame = rotateLeft(a, b);
  }
  if(c.equals("ru")){
   newGame = rotateUp(a, b);
  }
  if(c.equals("rd")){
   newGame = rotateDown(a, b);
  }
  if(c.equals("+r") || c.equals("-r") || c.equals("+c") || c.equals("-c")){
   newGame = mathMove(a, b, c);
  }

  return newGame;
 }
 
 //These next four rotation method's algorithms were a pain to code. Just look at how tricky it is to 
 //shift numbers around in an array!
 public static int[][] rotateRight(int[][] d, int e){
  int currentIndex = d[e].length-1;
  int lastNum = d[e][currentIndex];
  for(int i = 0; i < d[e].length-1; i++){
   d[e][currentIndex]=d[e][currentIndex-1];
   currentIndex--;
  }
  d[e][0] = lastNum;
  return d;
 }
 public static int[][] rotateLeft(int[][] f, int g){
  int firstNum = f[g][0];
  for(int i = 0; i < f[g].length-1; i++){
   f[g][i] = f[g][i+1];
  }
  f[g][f.length-1]=firstNum;
  return f;
 }
 public static int[][] rotateUp(int[][] f, int g){
  int firstElement = f[0][g];
  for(int i = 0; i < f.length-1; i++){
   f[i][g] = f[i+1][g];
  }
  f[f.length-1][g] = firstElement;
  return f;
 }
 public static int[][] rotateDown(int[][] f, int g){
  int currentIndex = f.length-1;
  int lastElement = f[currentIndex][g];
  for(int i = 0; i < f.length -1; i++){
   f[currentIndex][g]=f[currentIndex-1][g];
   currentIndex--;
  }
  f[0][g]=lastElement;
  return f;
 }
 public static int[][] mathMove(int[][] h, int m, String n){
  if(n.equals("+r")){
   for(int i = 0; i < h[m].length; i++){
    h[m][i] += 1; 
   }
  }
  if(n.equals("-r")){
   for(int i = 0; i < h[m].length; i++){
    h[m][i]-= 1;
   }
  }
  if(n.equals("+c")){
   for(int i = 0; i < h.length; i++){
    h[i][m] += 1;
   }
  }
  if(n.equals("-c")){
   for(int i = 0; i < h.length; i++){
    h[i][m] -= 1;
   }
  }
  return h;
 }
 public static int[][] random(int a, int b){
  Random generator = new Random();
  int[][] randomGame = new int[a][a];
  int startRange = 0;
  int endRange = 0;
  if(b == 1){
   startRange = 3;
   endRange = 1;
  }
  if(b == 2){
   startRange = 7;
   endRange = 3;
  }
  if(b == 3){
   startRange = 9;
   endRange = 4;
  }
  for(int row = 0; row <randomGame.length; row++){
   for(int col = 0; col < randomGame.length; col++){
    randomGame[row][col]= generator.nextInt(startRange) -endRange;
   }
  }
  return randomGame;
 }
}

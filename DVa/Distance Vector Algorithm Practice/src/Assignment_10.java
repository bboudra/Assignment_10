import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Assignment_10 {

	public static int[][] dVMatrix;
	public static int[] lVector;
	public static Map<Integer, Integer> neighbors = new HashMap<Integer, Integer>();
	public static int[] D0;
	public static int[] L0;
	public static int[] updatedD0;
	public static int[] updatedL0;
	public static int numRout;

	public static void main(String[] args) {
		Assignment_10 a10 = new Assignment_10();
		a10.getNumRoutFromUser();
		a10.readFiles();
		printNeighbors();
		printDVMatrix();
		printD0();
		printL0();
		boolean wishToContinue = true;
		while (wishToContinue) {
			updatedD0 = D0;
			updatedL0 = L0;
			int choice = a10.determineEvent();
			if (choice == 1) {
				changeLLC();
			} else {
				receiveDVM();
			}
			wishToContinue = a10.wishToContinue();
		}
	}

	public static void changeLLC() {
		Assignment_10 a10 = new Assignment_10();
		int router = a10.getRouterIndex();
		int newCost = a10.getNewCost();
		boolean needToNotifyNeighbors = updateRouters(router, newCost);
		if (needToNotifyNeighbors) {
			notifyNeighbors();
		} else {
			System.out.println("There is no need to notify any neighbor!");
		}
	}

	public static void notifyNeighbors() {
		for (int x : neighbors.keySet()) {
			System.out.print("V" + x);
			System.out.print("\t");
		}
		System.out.println();
		System.out.println("D0");
		for (int i = 1; i < numRout; i++) {
			System.out.println(updatedD0[i]);
			System.out.println("\t");
		}
		System.out.println();
		System.out.println("L0");
		for (int i = 1; i < numRout; i++) {
			System.out.println(updatedL0[i]);
			System.out.println("\t");
		}
	}

	public static boolean updateRouters(int router, int cost) {
		boolean update = false;
		for (int i = 0; i < numRout; i++) {
			if (D0[i] > dVMatrix[router][i] + cost) {
				update = true;
				updatedD0[i] = dVMatrix[router][i] + cost;
				updatedL0[i] = router;
			}
		}
		return update;
	}

	public int getRouterIndex() {
		System.out.println("Please enter the index of this neighboring router.");
		Scanner sc = new Scanner(System.in);
		int neighborRouter = sc.nextInt();
		if (neighborRouter > 0 && neighborRouter < this.getNumRout()) {
			return neighborRouter;
		} else {
			System.out.print("Your input is not valid");
			return getRouterIndex();
		}
	}

	public int getNewCost() {
		System.out.println("Please enter the new cost of this neighboring router.");
		Scanner sc = new Scanner(System.in);
		int cost = sc.nextInt();
		if (cost > 0) {
			return cost;
		} else {
			System.out.println("your input is not valid");
			return getNewCost();
		}

	}

	public static void receiveDVM() {
		Assignment_10 a10 = new Assignment_10();
		int neighboringNode = a10.getRouterIndex();
		int[] nUpdatedDVector= a10.getNeighborUpdatedDVector();
		boolean updated = updateRouters2(neighboringNode,nUpdatedDVector);
		if(updated)
		{
			notifyNeighbors();
		} else {
			System.out.println("There is no need to notify any neighbor!");
		}
			
	}
	
	public int[] getNeighborUpdatedDVector() {
		int[] dVector = new int[numRout];
		for(int i =0; i < numRout; i++)
		{
			dVector[i] = getNumberFromUser(i);
		}
		return dVector;
	}

	public int getNumberFromUser(int index) {
		System.out.println("Please enter the cost value at index: " +index);
		Scanner sc = new Scanner(System.in);
		int neighborRouter = sc.nextInt();
		if (neighborRouter >= 0)
		{
			return neighborRouter;
		} else {
			System.out.print("Your input is not valid");
			return getNumberFromUser(index);
		}

	}
	
	public static boolean updateRouters2(int router, int[] nDVector)
	{
		boolean update = false;
		for (int i = 0; i < numRout; i++) {
			if(D0[i] > nDVector[i] + dVMatrix[0][router])
			{
				update = true;
				updatedD0[i] = nDVector[i] + dVMatrix[0][router];
				updatedL0[i] = router;
			}
		}
		return update;
		
	}

	public boolean wishToContinue() {
		System.out.println("Do you wish to input a new event of change or receiving?\n" + "Please enter y or n");
		Scanner sc = new Scanner(System.in);
		String choice = sc.next();
		if (choice.equals("y")) {
			return true;
		} else if (choice.equals("n")) {
			return false;
		} else {
			System.out.println("Your input was not valid");
			return wishToContinue();
		}

	}

	public static int determineEvent() {
		System.out.println("Please type in the number of the following operation you wish to perform\n"
				+ "Event 1: a change in local link cost to a neighbor of router V0\n"
				+ "Event 2: receiving a distance vector message from a neighbor of router V0");
		Scanner options = new Scanner(System.in);
		int choice = options.nextInt();
		if (choice == 1 ||choice == 2) {
			return choice;
		} else {
			System.out.println("Your choice was not within the valid range");
			return determineEvent();
		}
	}

	public static void printNeighbors() {
		for (int key : neighbors.keySet()) {
			System.out.println("Key: " + key + " Value: " + neighbors.get(key));
		}
	}

	public static void printDVMatrix() {
		for (int[] i : dVMatrix) {
			for (int j : i) {
				System.out.print(j);
				System.out.print("\t");
			}
			System.out.println();
		}
	}

	public static void printD0() {
		for (int i : D0) {
			System.out.print(i);
			System.out.print("\t");
		}
		System.out.println();
	}

	public static void printL0() {
		for (int i : L0) {
			System.out.print(i);
			System.out.print("\t");
		}
		System.out.println();
	}

	public void getNumRoutFromUser() {
		System.out.println("Please enter the total number of routers in network");
		Scanner keyboardIn = new Scanner(System.in);
		int value = keyboardIn.nextInt();
		if (value < 2) {
			System.out.println("The value entered was less than 2.\nPlease enter a value greater than or equal to 2");
			this.getNumRoutFromUser();
		}
		setNumRout(value);
	}

	public void setNumRout(int numRout) {
		Assignment_10.numRout = numRout;
	}

	public int getNumRout() {
		return Assignment_10.numRout;
	}

	public void readFiles() {
		String filename1 = "cost.txt";
		String filename2 = "source_vectors.txt";
		String filename3 = "neighbor_vectors.txt";
		int valid1 = readFile1(filename1);
		int valid2 = readFile2(filename2);
		int valid3 = readFile3(filename3);
		if (valid1 != -1 || valid2 != -1 ||  valid3 != -1) {
			System.exit(1);
		}
	}

	public int readFile1(String filename) {
		FileReader fr = null;
		try {
			fr = new FileReader(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		BufferedReader fileIn = new BufferedReader(fr);
		String line;
		int linecount = 1;
		try {
			while ((line = fileIn.readLine()) != null) {
				String[] numbers = line.split("\t");
				int number1 = Integer.parseInt(numbers[0]);
				int number2 = Integer.parseInt(numbers[1]);
				if (0 < number1 && number1 < this.getNumRout() & number2 > 0)
					neighbors.put(number1, number2);
				else {
					System.out.println("line " + linecount + " in file " + filename
							+ "contains input that was not within the valid range.\n"
							+ "Please alter the file to be within the valid range and run the program again.");
					return linecount;
				}
				linecount++;
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return -1;
	}

	public int readFile2(String filename) {
		FileReader fr = null;
		try {
			fr = new FileReader(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		BufferedReader fileIn = new BufferedReader(fr);
		String line;
		int lineCount = 1;
		try {
			while ((line = fileIn.readLine()) != null) {
				String[] stringNumbers = line.split("\t");
				int stringNumbersLength = stringNumbers.length;
				int[] numbers = new int[stringNumbersLength];
				for (int i = 0; i < stringNumbersLength; i++) {
					numbers[i] = Integer.parseInt(stringNumbers[i]);
				}
				int numbersLength = numbers.length;
				if (numbersLength == this.getNumRout()) {
					if (lineCount == 1) {
						Assignment_10.D0 = numbers;
					} else if (lineCount == 2) {
						Assignment_10.L0 = numbers;
					} else {
						System.out.println("There are more lines in the file " + filename + " than there should be.\n"
								+ "Please alter this file to follow the formatting specifications specified\n"
								+ "in the assignment guidelines and run this program again.");
						return 3;
					}
				} else {
					System.out.println("There are an incorrect number of nodes on line " + lineCount + " in the file "
							+ filename + ".\n"
							+ "Please alter this file to follow the formatting specifications listed in\n"
							+ "the assignment guidelines and run this program again");
					return lineCount;
				}
				lineCount++;
			}
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		return -1;
	}

	public int readFile3(String filename) {
		FileReader fr = null;
		try {
			fr = new FileReader(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		BufferedReader fileIn = new BufferedReader(fr);
		String line;
		Assignment_10.dVMatrix = new int[Assignment_10.neighbors.size() + 1][this.getNumRout()];
		int lineCount = 1;
		try {
			Assignment_10.dVMatrix[0] = Assignment_10.D0;
			while ((line = fileIn.readLine()) != null) {
				String[] stringNumbers = line.split("\t");
				int stringNumbersLength = stringNumbers.length;
				int[] numbers = new int[stringNumbersLength - 1];
				for (int i = 1; i < stringNumbersLength; i++) {
					numbers[i - 1] = Integer.parseInt(stringNumbers[i]);
				}
				int numbersLength = numbers.length;
				if (numbersLength == this.getNumRout()) {
					if (lineCount < Assignment_10.neighbors.size() + 1)
						Assignment_10.dVMatrix[lineCount] = numbers;
					else {
						System.out.println("There are two many neighboring vectors in the file " + filename
								+ ". Please adjust\n"
								+ "The file so it matches the guidlines specified in Assignment_10 and \n"
								+ "run the program again");
						return lineCount;
					}
				} else {
					System.out.println("There are an incorrect number of nodes on line " + lineCount + " in the file "
							+ filename + ".\n"
							+ "Please alter this file to follow the formatting specifications listed in\n"
							+ "the assignment guidelines and run this program again");
					return lineCount;
				}
				lineCount++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

}

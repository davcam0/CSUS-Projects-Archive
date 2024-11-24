/*
Author: David Cameron (302449225)
Date Created: 10/03/2024
Project Name: CSC140 Assignment 3
Class: CSUS / FALL 2024/ CSC 140-01: Advanced Algorithm Design and Analysis
 */
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Sorting {
	static final int MAX_SIZE = 1000000;
	private static int maxDepth = 0;

	// Set this to true if you wish the arrays to be printed.
	final static boolean OUTPUT_DATA = false;
	
	public static String sortAlg= null;
	public static  int size = 0;
	
	public static void main(String[] args) {
		readInput();
		int [] data = new int[size];
		GenerateSortedData(data, size);
		maxDepth = 0;
		Sort(data, size,"Sorted Data");

		maxDepth = 0;
		GenerateNearlySortedData(data, size);
		Sort(data, size,"Nearly Sorted Data");

		maxDepth = 0;
		GenerateReverselySortedData(data, size);
		Sort(data, size,"Reversely Sorted Data");

		maxDepth = 0;
		GenerateRandomData(data, size);
		Sort(data, size,"Random Data");
		System.out.println("\nProgram Completed Successfully.");
	}
	
	public static void readInput(){
		System.out.println("  I:\tInsertion Sort");
		System.out.println("  M:\tMergeSort");
		System.out.println("  Q:\tQuickSort");
		System.out.println("  S:\tSTLSort");
	    System.out.println("Enter sorting algorithm:");
	    Scanner reader = new Scanner(System.in);
	    sortAlg = reader.next();
	    System.out.println(sortAlg);
		String sortAlgName = "";
		
		if(sortAlg.equals("I"))
			sortAlgName = "Insertion Sort";
		else if(sortAlg.equals("M"))
			sortAlgName = "MergeSort";
		else if(sortAlg.equals("Q"))
			sortAlgName = "QuickSort";
		else if(sortAlg.equals("S"))
			sortAlgName = "STLSort";
		else {
			System.out.println("Unrecognized sorting algorithm Code:"+sortAlg);
			System.exit(1);
		}
		System.out.println("Enter input size: ");
	    size = reader.nextInt();
		System.out.println("\nSorting Algorithm: " + sortAlgName);
        System.out.println("\nInput Size = "  + size);
	}
	
	/******************************************************************************/

	public static void GenerateSortedData(int[] data, int size) {
		int i;
		
		for(i=0; i<size; i++)
			data[i] = i * 3 + 5;
	}
	/*****************************************************************************/
	public static void GenerateNearlySortedData(int[] data, int size) {
		int i;
		
		GenerateSortedData(data, size);
		
		for(i=0; i<size; i++)
			if(i % 10 == 0)
				if(i+1 < size)
					data[i] = data[i+1] + 7;
	}
	/*****************************************************************************/

	public static void GenerateReverselySortedData(int[] data, int size) {
		int i;
		
		for(i = 0; i < size; i++)
			data[i] = (size-i) * 2 + 3;
	}
	/*****************************************************************************/

	public static void GenerateRandomData(int[] data, int size) {
		int i;
		for(i = 0; i < size; i++)
			data[i] = new Random().nextInt(1000000);
	}
	/*****************************************************************************/

	
	public static void Sort(int[] data, int size,  String string) {
		System.out.print("\n"+string+":");
		if (OUTPUT_DATA) {
			printData(data, size, "Data before sorting:");
		}

		// Sorting is about to begin ... start the timer!
		long start_time = System.nanoTime();
			if (sortAlg.equals("I")) {
			InsertionSort(data, size);
			}
			else if (sortAlg.equals("M")) {
			MergeSort(data, 0, size-1);
			}
			else if (sortAlg.equals("Q")) {
			QuickSort(data, 0, size-1);
			}
			else if (sortAlg.equals("S")) {
			STLSort(data, size);
			}
		else {
			System.out.print("Invalid sorting algorithm!");
			System.out.print("\n");
			System.exit(1);
		}

		// Sorting has finished ... stop the timer!
		
		double elapsed = System.nanoTime()-start_time;
		elapsed=elapsed/1000000;


		if (OUTPUT_DATA) {
			printData(data, size, "Data after sorting:");
		}


		if (IsSorted(data, size)) {
			System.out.print("\nCorrectly sorted ");
			System.out.print(size);
			System.out.print(" elements in ");
			System.out.print(elapsed);
			System.out.print("ms");
		}
		else {
			System.out.print("ERROR!: INCORRECT SORTING!");
			System.out.print("\n");
		}
		System.out.print("\n-------------------------------------------------------------\n");
	}
	
	/*****************************************************************************/

	public static boolean IsSorted(int[] data, int size) {
		int i;
		
		for(i=0; i<(size-1); i++) {
			if(data[i] > data[i+1])
				return false;
		}
		return true;
	}
	
	/*****************************************************************************/
	public static void InsertionSort(int[] data, int size) {
		insertionSort(data);
	}

	public static void insertionSort(int[] data) {
		insertionSort(data, 0, data.length - 1);
		System.out.println("InsertionSort");
	}

	public static void insertionSort(int[] data, int low, int high) {
		for (int i = low + 1; i <= high; i++) {
			final int currentValue = data[i];
			int j = i - 1;
			while (j >= low && data[j] > currentValue) {
				data[j + 1] = data[j--];
			}
			data[j + 1] = currentValue;
		}
	}
	/*****************************************************************************/

	public static void MergeSort(int[] data, int low, int high) {
		if (low >= high) {
			return;
		}
		final int mid = (low + high) / 2;
		MergeSort(data, low, mid);
		MergeSort(data, mid + 1, high);
		Combine(data, low, mid, high);
		//System.out.println("MergeSort");
	}

	private static void Combine(int[] data, int low, int mid, int high) {
		final int n1 = mid - low + 1;
		final int n2 = high - mid;

		int[] left = new int[n1 + 1];
		int[] right = new int[n2 + 1];

        System.arraycopy(data, low, left, 0, n1);
		System.arraycopy(data, mid + 1, right, 0, n2);

		final int large = Math.max(left[n1 - 1], right[n2 - 1]);

		left[n1] = large;
		right[n2] = large;

		int i = 0, j = 0;

		for (int k = low; k <= high; k++) {
			if (left[i] < right[j]) {
				data[k] = left[i++];
			}
			else {
				data[k] = right[j++];
			}
		}
	}

	/*****************************************************************************/
	/*
	This method is an improvement upon the normal single pivot Quicksort. This implements uses dual pivot quicksort
	to reduce the depth of recursion and then when the array reaches 40 or less elements, it uses insertionSort to finish
	sorting.
	 */
	private static void QuickSortImproved(int[] data, int low, int high, int depth) {
		if (low >= high) {
			return;
		}

		// Switch to InsertionSort for small arrays(size <= 40)
		if (high - low <= 40) {
			insertionSort(data, low, high);
			return;
		}

		// Track recursion depth
		if (maxDepth < depth) {
			maxDepth = depth;
		}

		// Create 2 pivots and partition into 3 sub arrays
		int[] pivots = Partition(data, low, high);
		int leftPivot = pivots[0];	// low side pivot
		int rightPivot = pivots[1]; // high side pivot

		// Recursively Sort the 3 sub arrays
		QuickSortImproved(data, low, leftPivot - 1, depth + 1);
		QuickSortImproved(data, leftPivot + 1, rightPivot - 1, depth + 1);
		QuickSortImproved(data, rightPivot + 1, high, depth + 1);

	}

	private static int[] Partition(int[] data, int low, int high) {
		Random rand = new Random();
		// Select two distinct random pivot indices within the range [low, high]
		int first = rand.nextInt(high - low + 1) + low;
		int second;
		do {
			second = rand.nextInt(high - low + 1) + low;
		} while (second == first);  // Ensure the second pivot is distinct

		// Move the pivot elements to low and high positions
		swap(first, low, data);
		swap(second, high, data);

		// Ensure left (low) pivot  is smaller than right (high) pivot
		if (data[low] > data[high]) {
			swap(low, high, data);
		}

		int i = low + 1;	// index to iterate the array
		int leftPtr = low + 1;	// Boundary for elements < leftPivot
		int rightPtr = high - 1;	// Boundary for elements > rightPivot

		while (i <= rightPtr) {
			if (data[i] < data[low]) {
				swap(i, leftPtr++, data);
			} else if (data[i] > data[high]) {
				swap(i, rightPtr--, data);
				continue;
			}
			i++;
		}

		// Place the pivots in their final positions
		swap(low, leftPtr - 1, data);		// Move leftPtr pivot to its final position
		swap(high, rightPtr + 1, data);	// Move right pivot to its final position

		return new int[]{leftPtr -1, rightPtr + 1};	// Return the final positions of the pivots
	}

    /*****************************************************************************/
	// Choose the QuickSort variant by commenting/uncommenting the variants you wish to test
	public static void QuickSort(int[] data, int low, int high) {
//		QuickSortBasic(data, low, high);	// This version will choose a pivot point from the end of the array. (Part 1)
//		QuickSortRandom(data, low, high);	// This version will choose a pivot point from a random index in the array. (Part 2.1)
//		QuickSortMedianOfThree(data, low, high);	// This version will choose a pivot from the median of three indexes chosen at random. (Part 2.2)
//		QuickSortWithInsertion(data, low, high);	// This version will choose a pivot from a random index but will switch to insertionSort when the array length is less than 40. (Part 2.3)
		QuickSortImproved(data, low, high);	// This version will use a Dual Pivot Quicksort but will switch to insertionSort when the array length is less than 40. (Extra Credit)
		System.out.println("Max Depth Reached : "+ maxDepth);
	}

	public static void QuickSortBasic(int[] data, int low, int high) {
		System.out.println("\nQuicksort with Basic Pivot");
		QuickSort(data, low, high, "basic", 0, false);
	}

	public static void QuickSortRandom(int[] data, int low, int high) {
		System.out.println("\nQuicksort with Random Pivot");
		QuickSort(data, low, high, "random", 0, false);
	}

	public static void QuickSortMedianOfThree(int[] data, int low, int high) {
		System.out.println("\nQuicksort with Median of Three Pivot");
		QuickSort(data, low, high, "median", 0, false);
	}

	public static void QuickSortWithInsertion(int[] data, int low, int high) {
		System.out.println("\nQuicksort With InsertionSort");
		QuickSort(data, low, high, "random", 0, true);
	}

	public static void QuickSortImproved(int[] data, int low, int high) {
		System.out.println("\nQuicksort Improved");
		QuickSortImproved(data, low, high, 0);
	}

	private static void QuickSort(int[] data, int low, int high, String pivotType, int depth, boolean useInsertionSort) {
		if (low >= high) {
			return;
		}

		// Optionally switch to InsertionSort for small arrays(size <= 40)
		if (useInsertionSort && high - low <= 40) {
			insertionSort(data, low, high);
			return;
		}

		// Track recursion depth
		if (maxDepth < depth) {
			maxDepth = depth;
		}

		// Partition the array and choose the pivot based on the pivotType
		final int mid = Partition(data, low, high, pivotType);

		QuickSort(data, low, mid - 1, pivotType, depth + 1, useInsertionSort);
		QuickSort(data, mid + 1, high, pivotType, depth + 1, useInsertionSort);
	}

	private static int Partition(int[] data, int low, int high, String pivotType) {
		switch (pivotType) { // Choose which pivot selection method by pivotType
			case "basic":
				// Do nothing special. Uses the last element as a pivot without any swap
				break;
			case "random":
				setPivotRandom(data, low, high); // Pick an index at random and swap it with the element in the last position
				break;
			case "median":
				setPivotMedianOfThree(data, low, high);
				break;
			default:
				System.err.println("Invalid pivot type!");
				System.exit(1);
		}

		final int pivotValue = data[high];
		int left = low - 1;

		for (int right = low; right < high; right++) {
			if (data[right] < pivotValue) {
				left++;
				swap(left, right, data);
			}

		}
		swap(left + 1, high, data);
		return left + 1;
	}

	// Random Pivot Selection
	private static void setPivotRandom(int[] data, int low, int high) {
		final int pivotIndex = new Random().nextInt(high - low + 1) + low;
		swap(pivotIndex, high, data);
	}

	// Median of three selection
	private static void setPivotMedianOfThree(int[] data, int low, int high) {
		Random rand = new Random();
		final int first = rand.nextInt(high - low + 1) + low;
		final int second = rand.nextInt(high - low + 1) + low;
		final int third = rand.nextInt(high - low + 1) + low;

		if ((data[first] > data[second] && data[first] < data[third]) ||
				(data[first] < data[second] && data[first] > data[third])) {
			swap(first, high, data);
		} else if ((data[second] > data[first] && data[second] < data[third]) ||
				(data[second] < data[first] && data[second] > data[third])) {
			swap(second, high, data);
		} else {
			swap(third, high, data);
		}
	}

	/*****************************************************************************/

	public static void STLSort(int[] data, int size) {
		Arrays.sort(data);
		System.out.println("STLSort");
	}

	/*****************************************************************************/
	
	public static void swap(int x , int y , int[] data) {
		int temp = data[x];
		data[x] = data[y];
	    data[y] = temp;
	}
	
	/*****************************************************************************/
	
	public static void printData(int[] data, int size, String title) {
		int i;

		System.out.print("\n");
		System.out.print(title);
		System.out.print("\n");
		for (i = 0; i < size; i++) {
			System.out.print(data[i]);
			System.out.print(" ");
			if (i % 10 == 9 && size > 10) {
				System.out.print("\n");
			}
		}
	}
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class SortOperations {
	
	public static void selectionSortString(String array[]){
		for(int j=0; j < array.length-1; j++){
		      int min = j;
		      for(int k=j+1; k < array.length; k++){
		    	  if(array[k].compareTo(array[min])<0){
		    		  min = k;  
		    	  }
		      }
		      String temp = array[j];
		      array[j] = array[min];
		      array[min] = temp;
		}
	}
	
	public static void mergeSortString(String[] names) {
        if (names.length > 2) {
            String[] left = new String[names.length / 2];
            String[] right = new String[names.length - names.length / 2];

            for (int i = 0; i < left.length; i++) {
                left[i] = names[i];
            }

            for (int i = 0; i < right.length; i++) {
                right[i] = names[i + names.length / 2];
            }

            mergeSortString(left);
            mergeSortString(right);
            mergeString(names, left, right);
        }
    }

    public static void mergeString(String[] names, String[] left, String[] right) {
        int a = 0;
        int b = 0;
        for (int i = 0; i < names.length; i++) {
            if (b >= right.length || (a < left.length && left[a].compareToIgnoreCase(right[b]) < 0)) {
                names[i] = left[a];
                a++;
            } else {
                names[i] = right[b];
                b++;
            }
        }
    }
	
	public static void bubbleSortString(String arr[]){
		String temp;
		for (int j = 0; j < arr.length; j++) {
			for (int i = j + 1; i < arr.length; i++) {
		            // comparing strings
		            if (arr[i].compareTo(arr[j]) < 0) {
		               temp = arr[j];
		               arr[j] = arr[i];
		               arr[i] = temp;
		            }
		         }
		}
	}
	
	public static void bubbleSort(int arr[]){ 
		int n = arr.length; 
		for (int i = 0; i < n-1; i++){
			for (int j = 0; j < n-i-1; j++){
				if (arr[j] > arr[j+1]){ 
					// swap arr[j+1] and arr[i] 
					int temp = arr[j]; 
					arr[j] = arr[j+1]; 
					arr[j+1] = temp; 
				}
			}
		}
	}
	
	public static void selectionSort(int arr[]){ 
	    int n = arr.length; 
	    for(int i = 0; i < n-1; i++){
	        int min_idx = i; 
	        for (int j = i+1; j < n; j++){
	        	if(arr[j] < arr[min_idx]){
	        		min_idx = j;
	        	}
	        } 
	        int temp = arr[min_idx]; 
	        arr[min_idx] = arr[i]; 
	        arr[i] = temp; 
	    } 
	}
	 
	public static void insertionSort(int arr[]){
		int n = arr.length; 
		for(int i = 1; i < n; ++i){
			int key = arr[i]; 
			int j = i - 1; 
			while (j >= 0 && arr[j] > key){ 
				arr[j + 1] = arr[j]; 
				j = j - 1; 
			}
			arr[j + 1] = key; 
		}
	 }
	 
	public static void heapSort(int arr[]){ 
		int n = arr.length;

		for (int i = n / 2 - 1; i >= 0; i--){
			heapify(arr, n, i);
		}

		for (int i=n-1; i>=0; i--){
			int temp = arr[0]; 
			arr[0] = arr[i]; 
			arr[i] = temp; 
			heapify(arr, i, 0); 
		}
	 }
	 
	public static void heapify(int arr[], int n, int i){
		int largest = i;
		int l = 2*i + 1;
		int r = 2*i + 2;
		
		if (l < n && arr[l] > arr[largest]){
			largest = l; 
		}
		 
		if (r < n && arr[r] > arr[largest]){
			largest = r; 
		}
		 
		if (largest != i){ 
			int swap = arr[i]; 
			arr[i] = arr[largest]; 
			arr[largest] = swap; 
			heapify(arr, n, largest); 
		}
	}
	 
	public static void bucketSort(int[] a, int maxVal){
		int [] bucket=new int[maxVal+1];

		for (int i=0; i<bucket.length; i++){
			bucket[i]=0;
		}
		for (int i=0; i<a.length; i++){
			bucket[a[i]]++;
		}

		int outPos=0;
		for (int i=0; i<bucket.length; i++){
			for (int j=0; j<bucket[i]; j++){
				a[outPos++]=i;
			}
		}
	}
	 
	public static void merge(int arr[], int l, int m, int r){
		int n1 = m - l + 1; 
		int n2 = r - m; 

		int L[] = new int [n1]; 
		int R[] = new int [n2]; 
	  
		for (int i=0; i<n1; ++i){
			L[i] = arr[l + i]; 
		}
		for (int j=0; j<n2; ++j){
			R[j] = arr[m + 1+ j]; 
		}

		int i = 0, j = 0; 
		int k = l; 
		while (i < n1 && j < n2){ 
			if (L[i] <= R[j]){ 
				arr[k] = L[i]; 
				i++;
			} 
			else{ 
				arr[k] = R[j]; 
				j++; 
			} 
			k++; 
		} 

		while (i < n1){ 
			arr[k] = L[i]; 
			i++; 
			k++; 
		} 
	  
		while (j < n2){ 
			arr[k] = R[j]; 
			j++; 
			k++; 
		} 
	}
	 
	public static void mergeSort(int arr[], int l, int r){ 
		 if (l < r){
			 int m = (l+r)/2; 

			 mergeSort(arr, l, m); 
			 mergeSort(arr , m+1, r); 
			 merge(arr, l, m, r); 
		 } 
	}
	 
	public static int partition(int arr[], int low, int high){ 
		int pivot = arr[high];  
		int i = (low-1); 
		for (int j=low; j<high; j++){ 
			if (arr[j] < pivot){ 
				i++; 
				int temp = arr[i]; 
				arr[i] = arr[j]; 
				arr[j] = temp; 
			}
		}

		int temp = arr[i+1]; 
		arr[i+1] = arr[high]; 
		arr[high] = temp; 

		return i+1; 
	}
	
	public static void quickSort(int arr[], int low, int high){ 
		if (low < high){
			int pi = partition(arr, low, high);
			quickSort(arr, low, pi-1); 
			quickSort(arr, pi+1, high); 
		} 
	}
	
	public static int getMax(int arr[], int n){ 
	    int mx = arr[0]; 
	    for (int i = 1; i < n; i++){
	        if (arr[i] > mx){ 
	            mx = arr[i];
	        }
	    }
	    return mx; 
	} 

	public static void countSortRadix(int arr[], int n, int exp){
		int output[] = new int[n]; // output array 
	    int i; 
	    int count[] = new int[10]; 
	    Arrays.fill(count,0); 
	    
	    for (i = 0; i < n; i++){
	    	count[ (arr[i]/exp)%10 ]++; 
	    }
	   
	    for (i = 1; i < 10; i++){
	        count[i] += count[i - 1]; 
	    }


	    for (i = n - 1; i >= 0; i--){
	    	output[count[ (arr[i]/exp)%10 ] - 1] = arr[i]; 
	        count[ (arr[i]/exp)%10 ]--; 
	    } 

	    for (i = 0; i < n; i++){ 
	        arr[i] = output[i]; 
	    }
	}
	
	public static void radixSort(int arr[], int n){ 
		int m = getMax(arr, n); 

	    for (int exp = 1; m/exp > 0; exp *= 10){ 
	        countSortRadix(arr, n, exp);
	    }
	} 
	
	public static void countSort(int[] arr){ 
		int max = Arrays.stream(arr).max().getAsInt(); 
		int min = Arrays.stream(arr).min().getAsInt(); 
		int range = max - min + 1; 
		int count[] = new int[range]; 
		int output[] = new int[arr.length]; 
		for (int i = 0; i < arr.length; i++){ 
			count[arr[i] - min]++; 
		} 

		for (int i = 1; i < count.length; i++){ 
			count[i] += count[i - 1]; 
		} 

		for (int i = arr.length - 1; i >= 0; i--){ 
			output[count[arr[i] - min] - 1] = arr[i]; 
			count[arr[i] - min]--; 
		} 

		for (int i = 0; i < arr.length; i++){ 
			arr[i] = output[i]; 
		} 
	} 
	
	public static int shellSort(int arr[]){ 
		int n = arr.length; 
		for (int gap = n/2; gap > 0; gap /= 2){ 
			for (int i = gap; i < n; i += 1){ 
				int temp = arr[i]; 
				int j; 
				for (j = i; j >= gap && arr[j - gap] > temp; j -= gap){ 
					arr[j] = arr[j - gap]; 
				}
				arr[j] = temp; 
			} 
		}
		return 0; 
	}
	
	public static int getNextGap(int gap){ 
		gap = (gap*10)/13; 
	    if (gap < 1){
	        return 1; 
	    }
	    return gap; 
	 } 

	public static void combSort(int arr[]){ 
	    int n = arr.length; 
	    int gap = n; 
	    boolean swapped = true; 
	    
	    while (gap != 1 || swapped == true){
	    	gap = getNextGap(gap); 
	        swapped = false; 
	        for (int i=0; i<n-gap; i++){
	        	if (arr[i] > arr[i+gap]){
	        		int temp = arr[i]; 
	                arr[i] = arr[i+gap]; 
	                arr[i+gap] = temp; 
	                swapped = true; 
	            } 
	        } 
	    } 
	}
	
	public static class Node{ 
		int key; 
		Node left, right; 

		public Node(int item){ 
			key = item; 
			left = right = null; 
		} 
	}
	public static Node root; 
	
	SortOperations(){
	root = null;  
	}
	public static void insert(int key){ 
		root = insertRec(root, key); 
	} 

	public static Node insertRec(Node root, int key){ 
		if (root == null){ 
			root = new Node(key); 
			return root; 
		} 

		if (key < root.key){
			root.left = insertRec(root.left, key);
		}
		else if (key > root.key){
			root.right = insertRec(root.right, key); 
		}
		return root; 
	} 

	public static void inorderRec(Node root){ 
		if (root != null){ 
			inorderRec(root.left); 
			System.out.print(root.key + " "); 
			inorderRec(root.right); 
		} 
	} 
	public static void treeSort(int arr[]){ 
		for(int i = 0; i < arr.length; i++){ 
			insert(arr[i]); 
		} 

	} 
	
	public static void printArray(int arr[]){ 
		int n = arr.length; 
		for (int i=0; i<n; ++i) 
			System.out.print(arr[i] + " "); 
		System.out.println(); 
	} 
	
	public static void printArrayString(String arr[]){ 
		int n = arr.length; 
		for (int i=0; i<n; ++i) 
			System.out.print(arr[i] + "\n "); 
		System.out.println(); 
	} 
}
import java.util.*;


public class shorting  {

    public static void DisplayAray(int arr[]){
        for(int i=0; i<arr.length; i++){
            System.out.println(arr[i]+" ");

        }
        System.out.println();


    }
    public static void main(String[] args){
         int arr[]={1,10,11,16};
    //short buuble 

//     for(int i=0; i<arr.length-1;i++){
//         for (int j=0; j<arr.length-1-i;j++){
//             if (arr[j]>arr[j+1]){
        
//             //swaiping without third variable
            
//              arr[j] ^= arr[j + 1];
//     arr[j + 1] ^= arr[j];
//     arr[j] ^= arr[j + 1];
//         }
//     }
// }
//selection sort

// for(int i=0;i<arr.length-1;i++){

//      int small=i;
//      for( int j= i+1; j<arr.length;j++){
//         if(arr[small]>arr[j]){
//             small=j;

//             int temp=arr[small];
//             arr[small]=arr[i];
//             arr[j]=temp;
//         }
//insertion sort
  for(int i=1; i<arr.length; i++) {
           int current = arr[i];
           int j = i - 1;
               while(j >= 0 && arr[j] > current) {
                   //Keep swapping
                   arr[j+1] = arr[j];
                   j--;
               }
           arr[j+1] = current;
       
            
     }

    
    DisplayAray(arr);
}}
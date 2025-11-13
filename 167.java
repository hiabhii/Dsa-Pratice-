class Solution {
    public int[] twoSum(int[] numbers, int target) {
int left = 0, right = numbers.length-1;
  int [] res= new int [2]; 
  while(left<right){ //for crossing right left
    int sum = numbers[left]+numbers[right];  //addition
    if(sum==target){   //condition
    res[0]=left+1;     //true so print array address1
    res[1]=right+1;    //true so print array address2 
    return res;   //print result
    }
    else if(sum<target){
        left++ ;  //not meet left increse 
        
    }
  else {
    right--;   //right decrese 
  }}
return res;
        
    }
}
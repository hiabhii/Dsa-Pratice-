//leetcode_26 
public int removeDuplicates(int[] nums){
    
    int n = nums.length;//array
    int j=1;//j pointer start from 1 because first element is always unique
    for(int i=0;i<n;i++){  //
        if(nums[i]!= nums[j-1]){
            nums[j]= nums[i];
            j++;
        }
    return j;

    }
}

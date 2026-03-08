class Solution {
    public void nextPermutation(int[] nums) {
        int n = nums.length;

        int i = n - 1;

        // find the first decreasing element from right
        while (i > 0 && nums[i] <= nums[i - 1]) {
            i--;
        }

        if (i != 0) {
            int index = i;

            // find the element just greater than nums[i - 1]
            for (int j = n - 1; j >= i; j--) {
                if (nums[j] > nums[i - 1]) {
                    index = j;
                    break;
                }
            }

            // swap
            int temp = nums[i - 1];
            nums[i - 1] = nums[index];
            nums[index] = temp;
        }

        // reverse from i to end
        reverse(nums, i, n - 1);
    }

    // helper reverse function (same as reverse in C++)
    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;

            left++;
            right--;
        }
    }
}
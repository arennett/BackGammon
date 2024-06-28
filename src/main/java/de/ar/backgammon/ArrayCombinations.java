package de.ar.backgammon;

import java.util.ArrayList;
import java.util.List;

public class ArrayCombinations {

    public static void main(String[] args) {
        int[] nums = {2, 2, 2,2};
        List<List<Integer>> result = generateCombinations(nums);

        for (List<Integer> combination : result) {
            System.out.println(combination);
        }
    }

    public static List<List<Integer>> generateCombinations(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        generate(nums, 0, current, result);
        return result;
    }

    private static void generate(int[] nums, int index, List<Integer> current, List<List<Integer>> result) {
        if (index == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Include the current element
        current.add(nums[index]);
        generate(nums, index + 1, current, result);

        // Exclude the current element
        current.remove(current.size() - 1);
        generate(nums, index + 1, current, result);
    }
}
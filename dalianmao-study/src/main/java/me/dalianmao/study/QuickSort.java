package me.dalianmao.study;

import java.util.Arrays;

/**
 * @author xiezhenzong
 *
 */
public class QuickSort {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int[] array = { 1, 23, 3, -9, 2, 1, 3, 4, 5, 65, 34, 2, 21 };
        sort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    public static void sort(int[] array, int start, int end) {
        if (end <= start) {
            return;
        }
        int pivot = array[start], i = start, j = end;
        while (i < j) {
            while (array[j] > pivot && j > i) {
                j--;
            }
            if (i < j) {
                array[i++] = array[j];
            }
            while (array[i] < pivot && i < j) {
                i++;
            }
            if (i < j) {
                array[j--] = array[i];
            }
        }
        array[i] = pivot;
        sort(array, start, i - 1);
        sort(array, i + 1, end);
    }

}

package me.dalianmao.study;

import java.util.Arrays;

/**
 * 小根堆的简单实现
 *
 * @author xiezhenzong
 *
 */
public class MinHeap {

    public static void main(String[] args) {
        MinHeap heap = new MinHeap(9);
        for (int i = 8; i >= 0; i--) {
            heap.add(i);
            System.out.println(heap.toString());
        }
        for (int i = 0; i < 9; i++) {
            System.out.println(heap.toString() + " -> pop: " + heap.pop() + " -> " + heap.toString() + ", size: " + heap.size());
        }
    }

    private int size = 0; // 即作为堆大小，也作为下一个要插入元素的下标
    private int capacity;
    private int[] heap;

    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new int[capacity];
    }

    public boolean add(int x) {
        if (size == capacity) {
            return false;
        }
        heap[size++] = x;
        heapifyUp(size - 1);
        return true;
    }

    public int top() {
        if (size == 0) {
            throw new IllegalStateException("There is not any number in heap.");
        } else {
            return heap[0];
        }
    }

    public int pop() {
        int top = top(), last = heap[--size];
        if (size == 0) {
            return top;
        }
        heap[0] = last;
        heapifyDown(0);
        return top;
    }

    public int size() {
        return size;
    }

    private void heapifyDown(int index) {
        int pointer = index;
        while (pointer < size) {
            int left = left(pointer), right = right(pointer), target = pointer;
            if (left < size && heap[left] < heap[target]) {
                target = left;
            }
            if (right < size && heap[right] < heap[target]) {
                target = right;
            }
            if (target != pointer) {
                int temp = heap[target];
                heap[target] = heap[pointer];
                heap[pointer] = temp;
                pointer = target;
            } else {
                break;
            }
        }
    }

    private void heapifyUp(int index) {
        int pointer = index;
        while (pointer > 0) {
            int parent = parent(pointer);
            if (heap[pointer] < heap[parent]) {
                int temp = heap[parent];
                heap[parent] = heap[pointer];
                heap[pointer] = temp;
                pointer = parent;
            } else {
                break;
            }
        }
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int left(int index) {
        return index * 2 + 1;
    }

    private int right(int index) {
        return (index + 1) * 2;
    }

    @Override
    public String toString() {
        return Arrays.toString(heap);
    }

}

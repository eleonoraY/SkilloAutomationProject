package Lecture14;

import org.testng.annotations.Test;

public class Lecture14ParallelTests {
    @Test
    public void parallel1() throws InterruptedException {
        System.out.println("Start of parallel1:");
        Thread.sleep(2000);
        int a = 15;
        int b = 10;
        int sum = a+b;
        System.out.println("This is the last test with the following sum: " + sum);
    }
    @Test
    public void parallel2() throws InterruptedException {
        System.out.println("Start of parallel2:");
        Thread.sleep(5000);
        int a = 15;
        int b = 10;
        int sum = a+b;
        System.out.println("This is the last test with the following sum: " + sum);
    }
}

package Lecture14;

import org.testng.annotations.Test;

public class Lecture14S {

    @Test
    public void suiteTest1() throws InterruptedException {
        int a = 5;
        int b = 10;
        int sum = a+b;
        System.out.println("suiteTest1:");
        Thread.sleep(2000);
        System.out.println("The sum is: " + sum);
    }

    @Test(groups = { "smoke", "checkintest" })
    public void suiteTest2() throws InterruptedException {
        int a = 5;
        int b = 10;
        int sum = a+b;
        System.out.println("suiteTest2:");
        Thread.sleep(10000);
        System.out.println("The sum is: " + sum);
    }
}

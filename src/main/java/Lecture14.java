package Lecture14;

import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class Lecture14 {

    @DataProvider(name="testData")
    public Object[][] createData(){
        return new Object[][] {
                { 25, 25 },
                { 26, 37},
        };
    }
    @Test(dataProvider = "testData")
    public void dataTest(int num1, int num2){
        int sum = num1+ num2;
        Assert.assertEquals(num1, num2, "The two texts are not equal");
        System.out.println("The sum is: " + sum);
    }
    @BeforeClass
    public void myBeforeClassMethod(){
        System.out.println("This is authentication method for the class");
    }
    @BeforeMethod
    public void myBeforeTestMethod(){
        System.out.println("Before every test method I reset data");
    }
    @BeforeMethod
    public void secondBeforeTestMethod(){
        System.out.println("Second Before every test");
    }
    @AfterClass
    public void myAfterClassMethod(){
        System.out.println("This is close the authentication method for the class");
    }
    @AfterMethod
    public void myAfterTestMethod(){
        System.out.println("After every test method I reset data");
    }
    @AfterMethod
    public void secondAfterTestMethod(){
        System.out.println("Second After every test");
    }

    @Test(groups = { "smoke", "checkintest" })
    public void myFirstTest(){
        int a = 5;
        int b = 10;
        int sum = a+b;
        System.out.println("myFirstTest:");
        System.out.println("The sum is: " + sum);
    }
    @Test(groups = { "smoke", "checkintest" })
    public void myFirstFailedTest(){
        System.out.println("myFirstFailedTest:");
        int a = 5;
        int b = 10;
        int sum = a+b;
        //Assert.assertTrue(false, "Failure message is logged here instead of this one");
        //Assert.assertEquals(a,b,"The number are not equal");
        String actual = "ala bala";
        String expected = "la bala";
        Assert.assertEquals(actual, expected, "The two texts are not equal");
        System.out.println("The sum is: " + sum);
    }
    @Test
    public void myFirstSoftTest(){
        System.out.println("myFirstSoftTest:");
        SoftAssert softAssert = new SoftAssert();

        int a = 5;
        int b = 10;
        int sum = a+b;
        softAssert.assertEquals(a,b,"The number are not equal asjhkfgaskjfgkals");

        String actual = "ala bala";
        String expected = "la bala";
        softAssert.assertEquals(actual, expected, "The two texts are not equal akjfgalksfn");

        System.out.println("The sum is: " + sum);

        softAssert.assertAll();
        System.out.println("The sum is: " + sum);
    }
    @Test
    public void myLast(){
        System.out.println("myLastTest:");
        int a = 15;
        int b = 10;
        int sum = a+b;
        System.out.println("This is the last test with the following sum: " + sum);
    }
}

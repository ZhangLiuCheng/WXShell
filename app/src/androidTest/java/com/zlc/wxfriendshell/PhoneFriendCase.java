package com.zlc.wxfriendshell;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

@RunWith(AndroidJUnit4.class)
public class PhoneFriendCase {

    private UiDevice mDevice;
    private int searchNumber;
    private int successNumber;
    private boolean running;

    private final String message = "老朋友你好，还记得我吗？";

    @Before
    public void setUp() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        running = true;
        //		mDevice.pressHome();

        /*
        // 打开微信
        UiObject wxApp = new UiObject(new UiSelector().text("微信"));
        wxApp.clickAndWaitForNewWindow();

        // 如果不在首界面，返回到首界面
        while (toBack()) {
        }
        */

//        toBack();
    }


    @Test
    public void testDemo() {
//        if (true) return;
        // 点击右上角+
        UiObject2 fuObj = mDevice.findObject(By.res("com.tencent.mm:id/ra"));
        System.out.println("-------->  点击右上角+");
        fuObj.clickAndWait(Until.newWindow(), 2000);
        // 点击添加朋友
        UiObject2 fvObj = mDevice.findObject(By.text("添加朋友"));
        System.out.println("-------->  添加朋友");
        fvObj.clickAndWait(Until.newWindow(), 2000);

        while (running) {
            try {
                searchNumber++;
                boolean success = addFriend(getRandomPhone());
                if (success) {
                    successNumber++;
                    System.out.println("成功添加好友： " + successNumber + "人");
                }
                if (successNumber > 20 || searchNumber > 100) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("添加好友失败  ");
            }
        }
    }

    private String getRandomPhone() {
        String number = "138";
        Random random = new Random();
        for (int j = 0; j < 8; j++) {
            number += random.nextInt(9);
        }
        return number;
    }

    private boolean toBack() {
        System.out.println("-------->  返回");
        mDevice.wait(Until.findObject(By.desc("返回")), 6000);
        UiObject2 backObj = mDevice.findObject(By.desc("返回"));

//        mDevice.wait(Until.findObject(By.res("com.tencent.mm:id/m0")), 6000);
//        UiObject2 backObj = mDevice.findObject(By.res("com.tencent.mm:id/m0"));
        if (backObj == null) {
            System.out.println("-------->  返回 失败");
            return false;
        }
        System.out.println("-------->  返回 成功");
        backObj.clickAndWait(Until.newWindow(), 5000);
        return true;
    }

    private boolean addFriend(String phone) {
        // 搜索框
        UiObject2 serachTextObj = mDevice.findObject(By.res("com.tencent.mm:id/dkw"));
        if (serachTextObj != null) {
            System.out.println("-------->  搜索框");
            serachTextObj.clickAndWait(Until.newWindow(), 5000);
        }

        // 输入号码
        UiObject2 accountTextObj = mDevice.findObject(By.res("com.tencent.mm:id/m6"));
        if (accountTextObj != null) {
            System.out.println("-------->  输入号码");
            accountTextObj.setText(phone);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 点击搜索
        mDevice.wait(Until.findObject(By.res("com.tencent.mm:id/s3")), 15000);
        UiObject2 serachObj = mDevice.findObject(By.res("com.tencent.mm:id/s3"));
        if (null != serachObj) {
            System.out.println("-------->  点击搜索");
            serachObj.clickAndWait(Until.newWindow(), 5000);
        }

        // 点击添加到通讯录
        UiObject2 makeFriendObj = mDevice.findObject(By.text("添加到通讯录"));
        if (makeFriendObj == null) {
            System.out.println("-------->  用户不存在或操作搜索超过数量");
            toBack();
            return false;
        }
        System.out.println("-------->  点击添加到通讯录");
        makeFriendObj.clickAndWait(Until.newWindow(), 5000);

        // 点击发送按钮
        UiObject2 sendObj = mDevice.findObject(By.text("发送"));
        if (sendObj != null) {
            System.out.println("-------->  点击发送按钮");
            sendObj.clickAndWait(Until.newWindow(), 50000);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-------->  睡眠");
        toBack();
        return true;
    }

    @After
    public void tearDown() {
        System.out.println("-------->  tearDown");

        mDevice = null;
        running = false;
    }
}

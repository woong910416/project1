package com.project1;

public class ThreadPay implements Runnable {
	
	@Override
	public void run() {
		
		try {
			System.out.print("������ �������Դϴ�");
			Thread.sleep(600);
			System.out.print(".");
			Thread.sleep(600);
			System.out.print(".");
			Thread.sleep(600);
			System.out.print(".");
			Thread.sleep(600);
			System.out.println();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class ThreadNum implements Runnable {

	@Override
	public void run() {
		try {
			System.out.print("������ȣ �߼����Դϴ�");
			Thread.sleep(600);
			System.out.print(".");
			Thread.sleep(600);
			System.out.print(".");
			Thread.sleep(600);
			System.out.print(".");
			Thread.sleep(600);
			
		} catch (Exception e) {
		}
	}
}

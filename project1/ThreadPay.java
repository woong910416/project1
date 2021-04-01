package com.project1;

public class ThreadPay implements Runnable {
	
	@Override
	public void run() {
		
		try {
			System.out.print("결제가 진행중입니다");
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
			System.out.print("인증번호 발송중입니다");
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

package com.project1;

import java.util.Scanner;

public class BusMain {
	
	static BusVO bv = new BusVO();
	static Bus ob = new Bus();
	static Reservation rv = new Reservation();

	public static void start() {
		try {
			
			Scanner sc = new Scanner(System.in);
			int n;
			
			while (true) {
				do {
					System.out.print("\n(메뉴선택)1.회원가입 2.로그인 3.예매하기 " + "4.예매내역 5.비밀번호찾기 6.종료\n메뉴를 선택해주세요. ");
					n = sc.nextInt();
				} while (n < 1 || n > 6);

				switch (n) {
				case 1: // 회원가입
					ob.join();
					break;

				case 2: // 로그인
					if (ob.login())
						loginState();
					break;

				case 3: // 예매하기
					int n1;

					do {
						System.out.print("1.회원예매 2.비회원예매");
						n1 = sc.nextInt();
					} while (n1 < 1 || n1 > 2);

					switch (n1) {
					case 1: // 예매하기 - 회원예매
						if (ob.login())
							rv.reservationM(BusVO.getId());
						break;
					case 2:
						rv.reservationB();// 예매하기 - 비회원예매
						break;
					}
					break;

				case 4: // 예매내역
					int n2;

					do {
						System.out.print("1.회원 2.비회원");
						n2 = sc.nextInt();
					} while (n2 < 1 || n2 > 2);

					switch (n2) {
					case 1:
						if(ob.login()) {
						rv.reservationMCheck(BusVO.getId());
						}
						break;
					case 2:
						rv.reservationBCheck();
						break;
					}
					break;

				case 5: // 비밀번호 찾기
					ob.findPw();
					break;

				case 6: // 종료
					System.exit(0); //이거 왜 안되지?
				}
			}
		} catch (Exception e) {
		}
	}

	public static void loginState() { // 로그인 상태 메뉴
		
		Scanner sc = new Scanner(System.in);
		int n;

		try {
			do {
				System.out.print("(메뉴선택)1.예매하기 2.예매내역 3.비밀번호변경 4.로그아웃");
				n = sc.nextInt();
			} while (n < 1 || n > 4);
			
			switch (n) {
			case 1: // 예매하기
				rv.reservationM(BusVO.getId());
				break;

			case 2: // 예매내역
				rv.reservationMCheck(BusVO.getId());
				break;

			case 3: // 비밀번호 변경
				ob.update();
				break;
				
			case 4: // 로그아웃
				break;
			}
			
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {

		Thread tb = new Thread(new ThreadBus());
		Thread tsw = new Thread(new ThreadSungWoong());

		try {
			tb.start();
			tb.join();
			tsw.start();
			tsw.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		while (true) {
			start();
		}
	}
}
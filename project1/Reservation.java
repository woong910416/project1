package com.project1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Scanner;

@SuppressWarnings("serial")
public class Reservation implements Serializable {

	Scanner sc = new Scanner(System.in);
	adminVO vo1 = new adminVO();
	Thread tt = new Thread(new ThreadTicket());
	Thread tp = new Thread(new ThreadPay());
	Thread tn = new Thread(new ThreadNum());

	public void reservation() {

		int l1 = 0; // 출발지역
		int l2 = 0; // 도착지역
		int c1 = 0; // 출발도시
		int c2 = 0; // 도착도시

		try {
			// 출발지역 선택
			do {
				System.out.println("출발 지역을 선택해주세요.");
				for (int i = 0; i < vo1.busLocation.length; i++) {
					System.out.print((i + 1) + "." + vo1.busLocation[i] + " ");
				}
				System.out.print("11.뒤로가기");
				l1 = sc.nextInt() - 1;

				if (l1 == 10)
					BusMain.loginState();

				System.out.println("출발 도시를 선택해주세요.");
				for (int i = 0; i < 3; i++) {
					System.out.print((i + 1) + "." + vo1.busAll[l1][i] + " ");
				}
				System.out.print("4.뒤로가기");
				c1 = sc.nextInt() - 1;

			} while (l1 < 0 || l1 > 10 || c1 < 0 || c1 > 3 || c1 == 3);
			vo1.setDeparture(vo1.busLocation[l1]);
			vo1.setDepartureCity(vo1.busAll[l1][c1]);

			// 도착지역 선택
			do {
				System.out.println("도착 지역을 선택해주세요.");
				for (int i = 0; i < vo1.busLocation.length; i++) {
					System.out.print((i + 1) + "." + vo1.busLocation[i] + " ");
				}
				System.out.print("11.뒤로가기");
				l2 = sc.nextInt() - 1;

				if (l2 == 10)
					BusMain.loginState();

				System.out.println("도착 도시를 선택해주세요.");
				for (int i = 0; i < 3; i++) {
					System.out.print((i + 1) + "." + vo1.busAll[l2][i] + " ");
				}
				System.out.print("4.뒤로가기");
				c2 = sc.nextInt() - 1;

			} while (l2 < 0 || l2 > 10 || c2 < 0 || c2 > 2 || c2 == 3 || (l1 == l2 && c1 == c2));
			vo1.setDestination(vo1.busLocation[l2]);
			vo1.setDestinationCity(vo1.busAll[l2][c2]);
		} catch (Exception e) {
		}
		busDay(l1, l2, c1, c2);
	}

	private void busDay(int l1, int l2, int c1, int c2) {

		Calendar ca = Calendar.getInstance();

		int m = ca.get(Calendar.MONTH);
		int d = ca.get(Calendar.DAY_OF_MONTH);
		int day;

		int[] dayPlus = new int[7];

		try {
			for (int i = 0; i < 7; i++) {
				ca.add(Calendar.DATE, 1);
				dayPlus[i] = ca.getTime().getDate();
			}
			do {
				System.out.print("출발지:" + vo1.busAll[l1][c1] + "--->도착지:" + vo1.busAll[l2][c2] + "\n날짜를 선택해주세요\n" + "("
						+ (m + 1) + "월) ");

				System.out.print(d + "일 ");
				for (int i = 0; i < dayPlus.length; i++) {
					System.out.print(dayPlus[i] + "일 ");
				}

				day = sc.nextInt();

			} while (day < dayPlus[0] - 1 || day > dayPlus[6]);
			vo1.setDepartureDay(day);
			if (day != 0) {
				busTime(l1, c1, l2, c2);
			}

		} catch (Exception e) {
		}
	}

	private void busTime(int i, int j, int k, int l) {
		try {
			System.out.println("출발지 :" + vo1.busAll[i][j] + "\t도착지: " + vo1.busAll[k][l]);
			if (i == 0 || k == 0) {
				ticket(vo1.busTime, i, j, k, l);
			} else if (i == 1 || i == 7 || i == 9 || k == 1 || k == 7 || k == 9) {
				ticket(vo1.busTime1, i, j, k, l);
			} else {
				ticket(vo1.busTime2, i, j, k, l);
			}
		} catch (Exception e) {
		}
	}

	private void ticket(String[] busTime, int i, int j, int k, int l) throws Exception {

		int select;
		int inwon;

		do {
			System.out.println("버스 시간");
			for (int a = 0; a < busTime.length; a++) {
				System.out.print((a + 1) + "." + busTime[a] + " ");
			}
			select = sc.nextInt();
		} while (select < 1 || select > busTime.length);
		vo1.setDepartureTime(busTime[select - 1]);

		do {
			System.out.print("몇 장 예매하시겠습니까?(최대 6매) ");
			inwon = sc.nextInt();
		} while (inwon < 1 || inwon > 6);
		vo1.setInwon(inwon);

		System.out.print("(" + vo1.getDepartureDay() + "일 " + busTime[select - 1] + ")" + " 요금:" + busPrice(i, k, inwon)
				+ "원" + " 예매하시겠습니까?[Y/N]");
		char yn = (char) System.in.read();

		if (yn == 'y' || yn == 'Y') {
			pay();
		} else {
		}
	}

	private int busPrice(int i, int k, int a) { // 버스 요금 계산, [i][j] 지역에서 [k][l] 지역 가는 버스요금

		String[] grade = { "일반", "우등" };

		int defaultPrice = 10000;
		int upgrade = 4000;
		int addPrice = 2400;

		int tot;
		tot = ((defaultPrice + (Math.abs(i - k) * addPrice))) * a;
		vo1.setBusFare(tot);
		return tot;
	}

	private void pay() {

		int code, select;
		String cardNum, expireDate, cvc, phoneNum;
		boolean cardNumLeng = false;
		boolean cardNumNum = false;
		boolean expireDateLeng = false;
		boolean expireDateNum = false;
		boolean cvcLeng = false;
		boolean cvcNum = false;
		boolean phoneNumLeng = false;
		boolean phoneNumNum = false;
		int rd = (int) ((Math.random() * 899999)) + 100000;

		try {
			do {
				System.out.print("1. 카드결제  2. 휴대폰결제");
				select = sc.nextInt();

			} while (select < 0 || select > 2);

			// -- 카드결제
			if (select == 1) {
				do {
					System.out.print("카드번호 16자리를 입력해주세요.('-' 제외) ");
					cardNum = sc.next();

					// 16자리 길이
					if (cardNum.length() != 16) {
						cardNumLeng = true;
						System.out.println("16자리가 아닙니다.");
					} else {
						cardNumLeng = false;
					}

					// 숫자가 아닌 것 입력
					char[] ch = cardNum.toCharArray();
					for (int i = 0; i < ch.length; i++) {
						if (ch[i] < 48 || ch[i] > 57) {
							cardNumNum = true;
						} else {
							cardNumNum = false;
						}
						
					}
					if(cardNumNum)
						System.out.println("숫자만 입력할 수 있습니다.");
				} while (cardNumLeng || cardNumNum);

				do {
					System.out.print("카드 유효기간을 입력해주세요.(2022년 11월이면 '1122') ");
					expireDate = sc.next();

					// 4자리 길이
					if (expireDate.length() != 4) {
						expireDateLeng = true;
						System.out.println("4자리가 아닙니다.");
					} else {
						expireDateLeng = false;
					}

					// 숫자가 아닌 것 입력
					char[] ch = expireDate.toCharArray();
					for (int i = 0; i < ch.length; i++) {
						if (ch[i] < 48 || ch[i] > 57) {
							expireDateNum = true;
						} else {
							expireDateNum = false;
						}
					}
					if(expireDateNum)
						System.out.println("숫자만 입력할 수 있습니다.");
				} while (expireDateLeng || expireDateNum);

				do {
					System.out.print("CVC 앞 3자리를 입력해주세요. ");
					cvc = sc.next();

					// 3자리 길이
					if (cvc.length() != 3) {
						cvcLeng = true;
						System.out.println("3자리가 아닙니다.");
					} else {
						cvcLeng = false;
					}

					// 숫자가 아닌 것 입력
					char[] ch = cvc.toCharArray();
					for (int i = 0; i < ch.length; i++) {
						if (ch[i] < 48 || ch[i] > 57) {
							cvcNum = true;
						} else {
							cvcNum = false;
						}
					}
					if(cvcNum)
						System.out.println("숫자만 입력할 수 있습니다.");
				} while (cvcLeng || cvcNum);
				tp.start();
				tp.join();

				tt.start();
				tt.join();

				System.out.println("예매가 완료 되었습니다.");
			}

			// -- 휴대폰결제
			if (select == 2) {
				do {
					System.out.print("휴대폰 번호를 입력해주세요. ");
					phoneNum = sc.next();
					
					// 11자리 길이
					if (phoneNum.length() != 11) {
						System.out.println("잘못 된 번호입니다.");
						phoneNumLeng = true;
					} else {
						phoneNumLeng = false;
					}

					// 숫자가 아닌 것 입력
					char[] ch = phoneNum.toCharArray();
					for (int i = 0; i < ch.length; i++) {
						if (ch[i] < 48 || ch[i] > 57) {
							phoneNumNum = true;
						} else {
							phoneNumNum = false;
						}
					}
					if(phoneNumNum)
						System.out.println("숫자만 입력할 수 있습니다.");
				} while (phoneNumLeng || phoneNumNum);

				tn.start();
				tn.join();
				// 스레드
				do {
					System.out.print("\n(" + rd + ") 인증 번호 6자리를 입력해주세요. ");
					code = sc.nextInt();
					if (code != rd) {
						System.out.println("잘못 된 인증번호입니다.");

					} else {
						tt.start();
						tt.join();
						System.out.println("예매가 완료 되었습니다.");
					}
				} while (code != rd);
			}
		} catch (Exception e) {
		}
	}

	public void reservationM(String str) { // 회원 예매
		
		reservation(); // 예매
		saveM(str); // 회원 예매정보 직렬화
	}

	public void reservationB() { // 비회원 예매

		String name, jumin;
		boolean flag = false;

		try {
			System.out.print("이름을 입력하세요.");
			name = sc.next();
			vo1.setNameB(name);

			do {
				System.out.print("주민등록번호를 입력해주세요.('-' 반드시 입력) ");
				jumin = sc.next();

				if (Bus.juminCheck(jumin)) {
					flag = true;
				} else {
					flag = false;
				}
			} while (flag);

			vo1.setJuminB(jumin);



			if (!flag) {
				reservation();
				// 비회원 정보 직렬화
				saveB(jumin, name);
			}
		} catch (Exception e) {
		}
	}

	public void reservationMCheck(String str) { // 회원 예매내역 확인 (역직렬화 후 출력)

		String path = "c:\\doc\\" + str + "reservation.txt";
		File file = new File(path);

		try {
			if (file.exists()) {

				restartM(str);

				System.out.println("(예매내역) " + "출발지:" + vo1.getDeparture() + " " + vo1.getDepartureCity() + "→ 도착지:"
						+ vo1.getDestination() + " " + vo1.getDestinationCity() + " / 출발일시: " + vo1.getDepartureDay()
						+ "일 " + vo1.getDepartureTime() + " / " + vo1.getInwon() + "명");

			} else {
				System.out.println("예매내역이 없습니다. ");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public void reservationBCheck() { // 비회원 예매내역 확인 (역직렬화 후 출력)

		String juminB;

		try {

			System.out.print("주민등록번호를 입력하세요.('-' 반드시 입력) ");
			juminB = sc.next();

			String path = "c:\\doc\\" + juminB + "reservation.txt";
			File file = new File(path);

			if (file.exists()) {
				restartB(juminB);

				System.out.println("(예매내역) " + "출발지:" + vo1.getDeparture() + " " + vo1.getDepartureCity() + "→ 도착지:"
						+ vo1.getDestination() + " " + vo1.getDestinationCity() + " / 출발일시: " + vo1.getDepartureDay()
						+ "일 " + vo1.getDepartureTime() + " / " + vo1.getInwon() + "명");

			} else {
				System.out.println("예매내역이 없습니다. ");
			}
		} catch (Exception e) {
		}
	}

	public adminVO saveM(String str) { // 회원 예매정보 직렬화

		try {
			String path = "c:\\doc\\test.txt";
			String parent = path.substring(0, path.lastIndexOf("\\"));

			File f = new File(parent);

			if (!f.exists())
				f.mkdirs();

			FileOutputStream fos = new FileOutputStream("c:\\doc\\" + str + "reservation.txt", true);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(vo1);

			oos.close();
			fos.close();
		} catch (Exception e) {
		}
		return vo1;
	}

	public adminVO restartM(String str) { // 회원 예매정보 역직렬화

		try {
			String path = "c:\\doc\\" + str + "reservation.txt";

			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);

			vo1 = (adminVO) ois.readObject();

			fis.close();
			ois.close();

		} catch (Exception e) {
		}
		return vo1;
	}

	public void saveB(String str, String str1) { // 비회원 예매정보 직렬화

		try {
			// 경로 없으면 생성
			String path = "c:\\doc\\membership.txt";
			String parent = path.substring(0, path.lastIndexOf("\\"));

			File f = new File(parent);

			if (!f.exists())
				f.mkdirs();

			// 주민등록번호로 파일 생성
			FileOutputStream fos = new FileOutputStream("c:\\doc\\" + str + "reservation.txt", true);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(vo1);

			oos.close();
			fos.close();
		} catch (Exception e) {
		}
	}

	public adminVO restartB(String str) { // 비회원 예매정보 역직렬화

		try {
			FileInputStream fis = new FileInputStream("c:\\doc\\" + str + "reservation.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);

			vo1 = (adminVO) ois.readObject();

			ois.close();
			fis.close();
		} catch (Exception e) {
		}
		return vo1;
	}
}

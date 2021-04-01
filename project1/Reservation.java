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

		int l1 = 0; // �������
		int l2 = 0; // ��������
		int c1 = 0; // ��ߵ���
		int c2 = 0; // ��������

		try {
			// ������� ����
			do {
				System.out.println("��� ������ �������ּ���.");
				for (int i = 0; i < vo1.busLocation.length; i++) {
					System.out.print((i + 1) + "." + vo1.busLocation[i] + " ");
				}
				System.out.print("11.�ڷΰ���");
				l1 = sc.nextInt() - 1;

				if (l1 == 10)
					BusMain.loginState();

				System.out.println("��� ���ø� �������ּ���.");
				for (int i = 0; i < 3; i++) {
					System.out.print((i + 1) + "." + vo1.busAll[l1][i] + " ");
				}
				System.out.print("4.�ڷΰ���");
				c1 = sc.nextInt() - 1;

			} while (l1 < 0 || l1 > 10 || c1 < 0 || c1 > 3 || c1 == 3);
			vo1.setDeparture(vo1.busLocation[l1]);
			vo1.setDepartureCity(vo1.busAll[l1][c1]);

			// �������� ����
			do {
				System.out.println("���� ������ �������ּ���.");
				for (int i = 0; i < vo1.busLocation.length; i++) {
					System.out.print((i + 1) + "." + vo1.busLocation[i] + " ");
				}
				System.out.print("11.�ڷΰ���");
				l2 = sc.nextInt() - 1;

				if (l2 == 10)
					BusMain.loginState();

				System.out.println("���� ���ø� �������ּ���.");
				for (int i = 0; i < 3; i++) {
					System.out.print((i + 1) + "." + vo1.busAll[l2][i] + " ");
				}
				System.out.print("4.�ڷΰ���");
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
				System.out.print("�����:" + vo1.busAll[l1][c1] + "--->������:" + vo1.busAll[l2][c2] + "\n��¥�� �������ּ���\n" + "("
						+ (m + 1) + "��) ");

				System.out.print(d + "�� ");
				for (int i = 0; i < dayPlus.length; i++) {
					System.out.print(dayPlus[i] + "�� ");
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
			System.out.println("����� :" + vo1.busAll[i][j] + "\t������: " + vo1.busAll[k][l]);
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
			System.out.println("���� �ð�");
			for (int a = 0; a < busTime.length; a++) {
				System.out.print((a + 1) + "." + busTime[a] + " ");
			}
			select = sc.nextInt();
		} while (select < 1 || select > busTime.length);
		vo1.setDepartureTime(busTime[select - 1]);

		do {
			System.out.print("�� �� �����Ͻðڽ��ϱ�?(�ִ� 6��) ");
			inwon = sc.nextInt();
		} while (inwon < 1 || inwon > 6);
		vo1.setInwon(inwon);

		System.out.print("(" + vo1.getDepartureDay() + "�� " + busTime[select - 1] + ")" + " ���:" + busPrice(i, k, inwon)
				+ "��" + " �����Ͻðڽ��ϱ�?[Y/N]");
		char yn = (char) System.in.read();

		if (yn == 'y' || yn == 'Y') {
			pay();
		} else {
		}
	}

	private int busPrice(int i, int k, int a) { // ���� ��� ���, [i][j] �������� [k][l] ���� ���� �������

		String[] grade = { "�Ϲ�", "���" };

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
				System.out.print("1. ī�����  2. �޴�������");
				select = sc.nextInt();

			} while (select < 0 || select > 2);

			// -- ī�����
			if (select == 1) {
				do {
					System.out.print("ī���ȣ 16�ڸ��� �Է����ּ���.('-' ����) ");
					cardNum = sc.next();

					// 16�ڸ� ����
					if (cardNum.length() != 16) {
						cardNumLeng = true;
						System.out.println("16�ڸ��� �ƴմϴ�.");
					} else {
						cardNumLeng = false;
					}

					// ���ڰ� �ƴ� �� �Է�
					char[] ch = cardNum.toCharArray();
					for (int i = 0; i < ch.length; i++) {
						if (ch[i] < 48 || ch[i] > 57) {
							cardNumNum = true;
						} else {
							cardNumNum = false;
						}
						
					}
					if(cardNumNum)
						System.out.println("���ڸ� �Է��� �� �ֽ��ϴ�.");
				} while (cardNumLeng || cardNumNum);

				do {
					System.out.print("ī�� ��ȿ�Ⱓ�� �Է����ּ���.(2022�� 11���̸� '1122') ");
					expireDate = sc.next();

					// 4�ڸ� ����
					if (expireDate.length() != 4) {
						expireDateLeng = true;
						System.out.println("4�ڸ��� �ƴմϴ�.");
					} else {
						expireDateLeng = false;
					}

					// ���ڰ� �ƴ� �� �Է�
					char[] ch = expireDate.toCharArray();
					for (int i = 0; i < ch.length; i++) {
						if (ch[i] < 48 || ch[i] > 57) {
							expireDateNum = true;
						} else {
							expireDateNum = false;
						}
					}
					if(expireDateNum)
						System.out.println("���ڸ� �Է��� �� �ֽ��ϴ�.");
				} while (expireDateLeng || expireDateNum);

				do {
					System.out.print("CVC �� 3�ڸ��� �Է����ּ���. ");
					cvc = sc.next();

					// 3�ڸ� ����
					if (cvc.length() != 3) {
						cvcLeng = true;
						System.out.println("3�ڸ��� �ƴմϴ�.");
					} else {
						cvcLeng = false;
					}

					// ���ڰ� �ƴ� �� �Է�
					char[] ch = cvc.toCharArray();
					for (int i = 0; i < ch.length; i++) {
						if (ch[i] < 48 || ch[i] > 57) {
							cvcNum = true;
						} else {
							cvcNum = false;
						}
					}
					if(cvcNum)
						System.out.println("���ڸ� �Է��� �� �ֽ��ϴ�.");
				} while (cvcLeng || cvcNum);
				tp.start();
				tp.join();

				tt.start();
				tt.join();

				System.out.println("���Ű� �Ϸ� �Ǿ����ϴ�.");
			}

			// -- �޴�������
			if (select == 2) {
				do {
					System.out.print("�޴��� ��ȣ�� �Է����ּ���. ");
					phoneNum = sc.next();
					
					// 11�ڸ� ����
					if (phoneNum.length() != 11) {
						System.out.println("�߸� �� ��ȣ�Դϴ�.");
						phoneNumLeng = true;
					} else {
						phoneNumLeng = false;
					}

					// ���ڰ� �ƴ� �� �Է�
					char[] ch = phoneNum.toCharArray();
					for (int i = 0; i < ch.length; i++) {
						if (ch[i] < 48 || ch[i] > 57) {
							phoneNumNum = true;
						} else {
							phoneNumNum = false;
						}
					}
					if(phoneNumNum)
						System.out.println("���ڸ� �Է��� �� �ֽ��ϴ�.");
				} while (phoneNumLeng || phoneNumNum);

				tn.start();
				tn.join();
				// ������
				do {
					System.out.print("\n(" + rd + ") ���� ��ȣ 6�ڸ��� �Է����ּ���. ");
					code = sc.nextInt();
					if (code != rd) {
						System.out.println("�߸� �� ������ȣ�Դϴ�.");

					} else {
						tt.start();
						tt.join();
						System.out.println("���Ű� �Ϸ� �Ǿ����ϴ�.");
					}
				} while (code != rd);
			}
		} catch (Exception e) {
		}
	}

	public void reservationM(String str) { // ȸ�� ����
		
		reservation(); // ����
		saveM(str); // ȸ�� �������� ����ȭ
	}

	public void reservationB() { // ��ȸ�� ����

		String name, jumin;
		boolean flag = false;

		try {
			System.out.print("�̸��� �Է��ϼ���.");
			name = sc.next();
			vo1.setNameB(name);

			do {
				System.out.print("�ֹε�Ϲ�ȣ�� �Է����ּ���.('-' �ݵ�� �Է�) ");
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
				// ��ȸ�� ���� ����ȭ
				saveB(jumin, name);
			}
		} catch (Exception e) {
		}
	}

	public void reservationMCheck(String str) { // ȸ�� ���ų��� Ȯ�� (������ȭ �� ���)

		String path = "c:\\doc\\" + str + "reservation.txt";
		File file = new File(path);

		try {
			if (file.exists()) {

				restartM(str);

				System.out.println("(���ų���) " + "�����:" + vo1.getDeparture() + " " + vo1.getDepartureCity() + "�� ������:"
						+ vo1.getDestination() + " " + vo1.getDestinationCity() + " / ����Ͻ�: " + vo1.getDepartureDay()
						+ "�� " + vo1.getDepartureTime() + " / " + vo1.getInwon() + "��");

			} else {
				System.out.println("���ų����� �����ϴ�. ");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public void reservationBCheck() { // ��ȸ�� ���ų��� Ȯ�� (������ȭ �� ���)

		String juminB;

		try {

			System.out.print("�ֹε�Ϲ�ȣ�� �Է��ϼ���.('-' �ݵ�� �Է�) ");
			juminB = sc.next();

			String path = "c:\\doc\\" + juminB + "reservation.txt";
			File file = new File(path);

			if (file.exists()) {
				restartB(juminB);

				System.out.println("(���ų���) " + "�����:" + vo1.getDeparture() + " " + vo1.getDepartureCity() + "�� ������:"
						+ vo1.getDestination() + " " + vo1.getDestinationCity() + " / ����Ͻ�: " + vo1.getDepartureDay()
						+ "�� " + vo1.getDepartureTime() + " / " + vo1.getInwon() + "��");

			} else {
				System.out.println("���ų����� �����ϴ�. ");
			}
		} catch (Exception e) {
		}
	}

	public adminVO saveM(String str) { // ȸ�� �������� ����ȭ

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

	public adminVO restartM(String str) { // ȸ�� �������� ������ȭ

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

	public void saveB(String str, String str1) { // ��ȸ�� �������� ����ȭ

		try {
			// ��� ������ ����
			String path = "c:\\doc\\membership.txt";
			String parent = path.substring(0, path.lastIndexOf("\\"));

			File f = new File(parent);

			if (!f.exists())
				f.mkdirs();

			// �ֹε�Ϲ�ȣ�� ���� ����
			FileOutputStream fos = new FileOutputStream("c:\\doc\\" + str + "reservation.txt", true);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(vo1);

			oos.close();
			fos.close();
		} catch (Exception e) {
		}
	}

	public adminVO restartB(String str) { // ��ȸ�� �������� ������ȭ

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

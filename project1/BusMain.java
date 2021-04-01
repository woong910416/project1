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
					System.out.print("\n(�޴�����)1.ȸ������ 2.�α��� 3.�����ϱ� " + "4.���ų��� 5.��й�ȣã�� 6.����\n�޴��� �������ּ���. ");
					n = sc.nextInt();
				} while (n < 1 || n > 6);

				switch (n) {
				case 1: // ȸ������
					ob.join();
					break;

				case 2: // �α���
					if (ob.login())
						loginState();
					break;

				case 3: // �����ϱ�
					int n1;

					do {
						System.out.print("1.ȸ������ 2.��ȸ������");
						n1 = sc.nextInt();
					} while (n1 < 1 || n1 > 2);

					switch (n1) {
					case 1: // �����ϱ� - ȸ������
						if (ob.login())
							rv.reservationM(BusVO.getId());
						break;
					case 2:
						rv.reservationB();// �����ϱ� - ��ȸ������
						break;
					}
					break;

				case 4: // ���ų���
					int n2;

					do {
						System.out.print("1.ȸ�� 2.��ȸ��");
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

				case 5: // ��й�ȣ ã��
					ob.findPw();
					break;

				case 6: // ����
					System.exit(0); //�̰� �� �ȵ���?
				}
			}
		} catch (Exception e) {
		}
	}

	public static void loginState() { // �α��� ���� �޴�
		
		Scanner sc = new Scanner(System.in);
		int n;

		try {
			do {
				System.out.print("(�޴�����)1.�����ϱ� 2.���ų��� 3.��й�ȣ���� 4.�α׾ƿ�");
				n = sc.nextInt();
			} while (n < 1 || n > 4);
			
			switch (n) {
			case 1: // �����ϱ�
				rv.reservationM(BusVO.getId());
				break;

			case 2: // ���ų���
				rv.reservationMCheck(BusVO.getId());
				break;

			case 3: // ��й�ȣ ����
				ob.update();
				break;
				
			case 4: // �α׾ƿ�
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
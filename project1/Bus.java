package com.project1;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

@SuppressWarnings("serial")
public class Bus implements Serializable{
	
	public Bus() {}
	
	Scanner sc = new Scanner(System.in);
	BusVO vo = new BusVO();

	public void join() { // ȸ������ �޼ҵ�

		boolean flag = true;
		try {

			// ���̵� �Է�
			do {
				System.out.print("���̵� �Է����ּ���. ");
				vo.setId(sc.next());
				vo.setId(vo.getId().toLowerCase()); // �빮��->�ҹ���

				if (vo.getId().length() < 8 || vo.getId().length() > 15) {
					System.out.println("���̵�� 8~15���Դϴ�.");
				}
				
				//���̵� �ߺ� �˻�
				idCheck(vo.getId());
				
			} while (vo.getId().length() < 8 || vo.getId().length() > 15 || idCheck(vo.getId()));

			// ��й�ȣ �Է�
			do {
				System.out.print("����� ��й�ȣ�� �Է����ּ���. ");
				vo.setPw(sc.next());

				if (!(pwCheck(vo.getPw())))
					System.out.println("��й�ȣ�� 8~20��, ����, ����, Ư�����ڰ� ���ԵǾ�� �մϴ�.");

			} while (!(pwCheck(vo.getPw())));

			// ��й�ȣ ��Ȯ��
			String pw1;
			do {
				System.out.print("�Է��� ��й�ȣ�� �� �� �� �Է����ּ���. ");
				pw1 = sc.next();

				if (!(pw1.equals(vo.getPw()))) {
					System.out.print("�Է��� ��й�ȣ�� ��ġ���� �ʽ��ϴ�." + "\n�ٽ� �Է����ּ���.\n");
				}
			} while (!(pw1.equals(vo.getPw())));

			// �̸� �Է�
			System.out.print("�̸�: ");
			vo.setName(sc.next());

			// �ֹε�Ϲ�ȣ �Է�
			do {
				System.out.print("�ֹε�Ϲ�ȣ�� �Է����ּ���.('-' �ݵ�� �Է�) ");
				vo.setJumin(sc.next());
				if (juminCheck(vo.getJumin())) {
					flag = true;
				} else {
					flag = false;
				}
			} while (flag);

			// ����ȭ
			
			save(vo.getId());

		} catch (Exception e) {
		}
	}

	public boolean login() { // �α��� �޼ҵ�

		boolean flag = false;
		String id, pw;
		int s;

		try {
			do {
				System.out.print("���̵�: ");
				id = sc.next();
				vo.setId(id); //..

				if (fileCheck(id)) {
					restart(id); //ȸ������ ������ȭ
				} else {
					System.out.println("���̵� �������� �ʽ��ϴ�.");
					BusMain.start();
				}
			} while (!(fileCheck(id)));

			do {
				System.out.print("��й�ȣ: ");
				pw = sc.next();

				if (pw.equals(vo.getPw())) {
					System.out.println(vo.getId() + "�� ȯ���մϴ�.");
					flag = true;
					
				}else if (!(pw.equals(vo.getPw()))) {
					System.out.println("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
					flag = false;
					
					System.out.print("1.ó������  2.��й�ȣ �ٽ� �Է�");
					s = sc.nextInt();

					switch(s) {
					case 1 : BusMain.start(); break;
					case 2 : break;
					}
				}
			} while (!(pw.equals(vo.getPw())));

		} catch (Exception e) {
		}
		return flag;
	}

	public void update() { // ��й�ȣ ���� �޼ҵ�

        String pw, pw1, pw2;
        boolean flag1 = false;
        boolean flag2 = false;

        try {
            // ���� ��й�ȣ Ȯ��
            System.out.print("���� ��й�ȣ �Է����ּ���. ");
            pw = sc.next();

            if (!(pw.equals(vo.getPw()))) {
                System.out.println("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
                update();
            }

            // ���� �� ��й�ȣ �Է�
            do {
                System.out.print("������ ��й�ȣ �Է����ּ���. ");
                pw1 = sc.next();

                // ��й�ȣ ���� �˻� �޼ҵ�
                if (!(pwCheck(pw1))) {
                    System.out.println("��й�ȣ�� 8~20��,����,����,Ư�����ڰ� ���ԵǾ�� �մϴ�.");
                    flag1 = true; //��й�ȣ Ʋ���Ծ� --->�ٽ��Է�
                }else {//��й�ȣ ���Ŀ� �´µ�

                    // �Է��� ��й�ȣ�� ������ ��й�ȣ�� ������ �ȵ�
                    if (pw.equals(pw1)) {
                        System.out.println("������ ��й�ȣ�� �ٲ� �� �����ϴ�.");
                        flag2 = true;
                    }else {//��й�ȣ ���Ŀ� �°� �������� ������ 
                        // ��й�ȣ ��Ȯ�� --> ���Ŀ��� �°� ��й�ȣ�� �������������� ����
                        do {
                            System.out.print("�Է��� ��й�ȣ�� �� �� �� �Է����ּ���. ");
                            pw2 = sc.next();

                            if (!(pw2.equals(pw1))) {
                                System.out.println("�Է��� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
                            }
                        } while (!(pw2.equals(pw1)));

                        System.out.println("��й�ȣ�� �����Ǿ����ϴ�.");


                        vo.setPw(pw2);

                        // ����ȭ(������ ��й�ȣ ����)
                        save(vo.getId());
                        flag2 = false;
                    }

                    flag1 = false;
                }

            } while(flag1 || flag2);//�Ѵ� false�����;��� �ϳ��� true�� �ٽ� �Է�

        } catch (Exception e) {
        }
    }

	public void findPw() { // ���̵�,�̸�,�ֹε�Ϲ�ȣ�� ��й�ȣ ã��

		boolean flag = false;
		String id, name, jumin;

		try {
			// ���̵� �Է�
			do {
				System.out.print("���̵� �Է����ּ���. ");
				id = sc.next();

				// ���̵� �����ϸ� ������ȭ
				if (fileCheck(id)) {
					restart(id);
				} else {
					System.out.println("���̵� �������� �ʽ��ϴ�.");
					findPw();
				}
			} while (!(id.equals(vo.getId())));

			// �̸� �Է�
			System.out.print("�̸��� �Է����ּ���. ");
			name = sc.next();

			// �ֹε�Ϲ�ȣ �Է�
			do {
				System.out.print("�ֹε�Ϲ�ȣ�� �Է����ּ���. ");
				jumin = sc.next();

				// �ֹε�Ϲ�ȣ '-' ������ �߰�
				if (!(jumin.length() < 7)) {
					char ch = jumin.charAt(6);
					if (ch != '-') {
						StringBuffer sb = new StringBuffer(jumin);
						sb.insert(6, '-');
						jumin = sb.toString();
					}
				}
				if (!(jumin.equals(vo.getJumin()))) {
					System.out.println("�ֹε�Ϲ�ȣ�� �߸� �Է��ϼ̽��ϴ�.");
					flag = true;
				} else {
					flag = false;
				}
			} while (flag);

			// �̸�, �ֹε�Ϲ�ȣ, ���̵� ��� ��ġ�ϸ� ��й�ȣ �˷��ֱ�
			if (name.equals(vo.getName()) && jumin.equals(vo.getJumin()) && id.equals(vo.getId())) {
				System.out.print("PW: " + vo.getPw() + "\n");
			} else {
				System.out.print("��� �� ȸ���� �ƴմϴ�.\n");
			}
		} catch (Exception e) {
		}
	}

	public static boolean juminCheck(String str) { // �ֹε�Ϲ�ȣ ���� Ȯ�� �޼ҵ�

		boolean flag = false;

		// �ֹε�Ϲ�ȣ '-' ���� �� ���̰� 14�� �ƴϸ� flag�� true
		if (str.length() < 14 || str.length() != 14 || str.length() > 14) {
			System.out.println("�߸� �� �ֹε�Ϲ�ȣ�Դϴ�. �ٽ� �Է����ּ���.");
			flag = true;
		}

		// �ֹε�Ϲ�ȣ 7��° �ڸ��� '-'�� ������ flag�� true

		char[] ch = str.toCharArray();
		if (ch.length > 5) {
			if (ch[6] != '-') {
				flag = true;
			}
		}
		return flag;
	}

	public boolean idCheck(String str) {
		
		boolean flag = false;
		
		String path = "c:\\doc\\" + str + ".txt";
		File file = new File(path);

		if(file.exists()) {
			System.out.println("������ ���̵� �����մϴ�.");
			flag = true;
		}else {
			flag = false;
		}
		
		return flag;
	}


	public boolean pwCheck(String str) { // ��й�ȣ ���� �˻� �޼ҵ�

		boolean flag = true;

		// 8~20�ڰ� �ƴϸ� flag�� false
		if (str.length() < 8 || str.length() > 20)
			flag = false;

		// ������Ư������ ȥ��
		int eng = 0; // �����ڸ� +1
		int num = 0; // ���ڸ� +1
		int spe = 0; // Ư������ ������ +1

		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);

			if ((ch >= 65 && ch <= 90) || (ch >= 'a' && ch <= 'z')) {
				eng++;
			} else if (ch >= '0' && ch <= '9') {
				num++;
			} else if (ch >= 33 && ch <= 47 || ch >= 58 && ch <= 64) {
				spe++;
			}
		}
		if (eng == 0 || num == 0 || spe == 0)
			flag = false;

		return flag;
	}

	public boolean fileCheck(String str) { //���� ���� Ȯ��

		boolean flag = false;

		File f = new File("c:\\doc\\" + str + ".txt");

		if (f.exists()) {
			flag = true;
		}

		return flag;
	}

	// --------------------------------------------------------------------------------------------------
	public void save(String str) { // ȸ������ ����ȭ
		try {
			String path = "c:\\doc\\test.txt";
			String parent = path.substring(0, path.lastIndexOf("\\"));

			File f = new File(parent);

			if (!f.exists())
				f.mkdirs();

			FileOutputStream fos = new FileOutputStream("c:\\doc\\" + str + ".txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(vo); // BusVO

			oos.close();
			fos.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public BusVO restart(String str) { // ȸ������ ������ȭ
		try {
			FileInputStream fis = new FileInputStream("c:\\doc\\" + str + ".txt");
			ObjectInputStream ois = new ObjectInputStream(fis);

			vo = (BusVO) ois.readObject();

			ois.close();
			fis.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return vo;
	}
}

//int a=0;
//int b=0;		

//// �ֹε�Ϲ�ȣ �տ� 6�ڸ� + '-' + �ڿ� 7�ڸ��� �ƴϸ� flag�� true
//		char[] ch = str.toCharArray();
//		for (int i = 0; i < 6; i++) {
//			if (ch[i] < 48 || ch[i] > 57) {
//				a += 1;
//			}
//		}
//		for (int i = 7; i < 15; i++) {
//			if (ch[i] < 48 || ch[i] > 57) {
//				b += 1;
//			}
//		}
//		if(!(a==0 && b==0)) {
//			System.out.println("�߸� �� �ֹε�Ϲ�ȣ�Դϴ�. �ٽ� �Է����ּ���.");
//			flag = true;
//		}

//// �ֹε�Ϲ�ȣ 7��° �ڸ��� '-' ������ '-' �߰� 123-456-7891234  123-45-6789123
//if (!(str.length() < 7)) {
//	char ch = str.charAt(6);
//	if (ch != '-') {
//		StringBuffer sb = new StringBuffer(str);
//		sb.insert(6, '-');
//		str = sb.toString();
//		vo.setJumin(str);
//	}
//}


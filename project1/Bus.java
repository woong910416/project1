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

	public void join() { // 회원가입 메소드

		boolean flag = true;
		try {

			// 아이디 입력
			do {
				System.out.print("아이디를 입력해주세요. ");
				vo.setId(sc.next());
				vo.setId(vo.getId().toLowerCase()); // 대문자->소문자

				if (vo.getId().length() < 8 || vo.getId().length() > 15) {
					System.out.println("아이디는 8~15자입니다.");
				}
				
				//아이디 중복 검사
				idCheck(vo.getId());
				
			} while (vo.getId().length() < 8 || vo.getId().length() > 15 || idCheck(vo.getId()));

			// 비밀번호 입력
			do {
				System.out.print("사용할 비밀번호를 입력해주세요. ");
				vo.setPw(sc.next());

				if (!(pwCheck(vo.getPw())))
					System.out.println("비밀번호는 8~20자, 영어, 숫자, 특수문자가 포함되어야 합니다.");

			} while (!(pwCheck(vo.getPw())));

			// 비밀번호 재확인
			String pw1;
			do {
				System.out.print("입력한 비밀번호를 한 번 더 입력해주세요. ");
				pw1 = sc.next();

				if (!(pw1.equals(vo.getPw()))) {
					System.out.print("입력한 비밀번호와 일치하지 않습니다." + "\n다시 입력해주세요.\n");
				}
			} while (!(pw1.equals(vo.getPw())));

			// 이름 입력
			System.out.print("이름: ");
			vo.setName(sc.next());

			// 주민등록번호 입력
			do {
				System.out.print("주민등록번호를 입력해주세요.('-' 반드시 입력) ");
				vo.setJumin(sc.next());
				if (juminCheck(vo.getJumin())) {
					flag = true;
				} else {
					flag = false;
				}
			} while (flag);

			// 직렬화
			
			save(vo.getId());

		} catch (Exception e) {
		}
	}

	public boolean login() { // 로그인 메소드

		boolean flag = false;
		String id, pw;
		int s;

		try {
			do {
				System.out.print("아이디: ");
				id = sc.next();
				vo.setId(id); //..

				if (fileCheck(id)) {
					restart(id); //회원정보 역직렬화
				} else {
					System.out.println("아이디가 존재하지 않습니다.");
					BusMain.start();
				}
			} while (!(fileCheck(id)));

			do {
				System.out.print("비밀번호: ");
				pw = sc.next();

				if (pw.equals(vo.getPw())) {
					System.out.println(vo.getId() + "님 환영합니다.");
					flag = true;
					
				}else if (!(pw.equals(vo.getPw()))) {
					System.out.println("비밀번호가 일치하지 않습니다.");
					flag = false;
					
					System.out.print("1.처음으로  2.비밀번호 다시 입력");
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

	public void update() { // 비밀번호 변경 메소드

        String pw, pw1, pw2;
        boolean flag1 = false;
        boolean flag2 = false;

        try {
            // 현재 비밀번호 확인
            System.out.print("현재 비밀번호 입력해주세요. ");
            pw = sc.next();

            if (!(pw.equals(vo.getPw()))) {
                System.out.println("비밀번호가 일치하지 않습니다.");
                update();
            }

            // 수정 할 비밀번호 입력
            do {
                System.out.print("수정할 비밀번호 입력해주세요. ");
                pw1 = sc.next();

                // 비밀번호 형식 검사 메소드
                if (!(pwCheck(pw1))) {
                    System.out.println("비밀번호는 8~20자,영어,숫자,특수문자가 포함되어야 합니다.");
                    flag1 = true; //비밀번호 틀리게씀 --->다시입력
                }else {//비밀번호 형식에 맞는데

                    // 입력한 비밀번호와 수정할 비밀번호가 같으면 안됨
                    if (pw.equals(pw1)) {
                        System.out.println("동일한 비밀번호로 바꿀 수 없습니다.");
                        flag2 = true;
                    }else {//비밀번호 형식에 맞고 동일하지 않으면 
                        // 비밀번호 재확인 --> 형식에도 맞고 비밀번호가 동일하지않으면 실행
                        do {
                            System.out.print("입력한 비밀번호를 한 번 더 입력해주세요. ");
                            pw2 = sc.next();

                            if (!(pw2.equals(pw1))) {
                                System.out.println("입력한 비밀번호와 일치하지 않습니다.");
                            }
                        } while (!(pw2.equals(pw1)));

                        System.out.println("비밀번호가 수정되었습니다.");


                        vo.setPw(pw2);

                        // 직렬화(수정한 비밀번호 저장)
                        save(vo.getId());
                        flag2 = false;
                    }

                    flag1 = false;
                }

            } while(flag1 || flag2);//둘다 false가나와야함 하나라도 true면 다시 입력

        } catch (Exception e) {
        }
    }

	public void findPw() { // 아이디,이름,주민등록번호로 비밀번호 찾기

		boolean flag = false;
		String id, name, jumin;

		try {
			// 아이디 입력
			do {
				System.out.print("아이디를 입력해주세요. ");
				id = sc.next();

				// 아이디가 존재하면 역직렬화
				if (fileCheck(id)) {
					restart(id);
				} else {
					System.out.println("아이디가 존재하지 않습니다.");
					findPw();
				}
			} while (!(id.equals(vo.getId())));

			// 이름 입력
			System.out.print("이름을 입력해주세요. ");
			name = sc.next();

			// 주민등록번호 입력
			do {
				System.out.print("주민등록번호를 입력해주세요. ");
				jumin = sc.next();

				// 주민등록번호 '-' 없으면 추가
				if (!(jumin.length() < 7)) {
					char ch = jumin.charAt(6);
					if (ch != '-') {
						StringBuffer sb = new StringBuffer(jumin);
						sb.insert(6, '-');
						jumin = sb.toString();
					}
				}
				if (!(jumin.equals(vo.getJumin()))) {
					System.out.println("주민등록번호를 잘못 입력하셨습니다.");
					flag = true;
				} else {
					flag = false;
				}
			} while (flag);

			// 이름, 주민등록번호, 아이디가 모두 일치하면 비밀번호 알려주기
			if (name.equals(vo.getName()) && jumin.equals(vo.getJumin()) && id.equals(vo.getId())) {
				System.out.print("PW: " + vo.getPw() + "\n");
			} else {
				System.out.print("등록 된 회원이 아닙니다.\n");
			}
		} catch (Exception e) {
		}
	}

	public static boolean juminCheck(String str) { // 주민등록번호 형식 확인 메소드

		boolean flag = false;

		// 주민등록번호 '-' 포함 총 길이가 14가 아니면 flag에 true
		if (str.length() < 14 || str.length() != 14 || str.length() > 14) {
			System.out.println("잘못 된 주민등록번호입니다. 다시 입력해주세요.");
			flag = true;
		}

		// 주민등록번호 7번째 자리에 '-'이 없으면 flag에 true

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
			System.out.println("동일한 아이디가 존재합니다.");
			flag = true;
		}else {
			flag = false;
		}
		
		return flag;
	}


	public boolean pwCheck(String str) { // 비밀번호 형식 검사 메소드

		boolean flag = true;

		// 8~20자가 아니면 flag에 false
		if (str.length() < 8 || str.length() > 20)
			flag = false;

		// 영문자특수문자 혼용
		int eng = 0; // 영문자면 +1
		int num = 0; // 숫자면 +1
		int spe = 0; // 특수문자 있으면 +1

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

	public boolean fileCheck(String str) { //파일 존재 확인

		boolean flag = false;

		File f = new File("c:\\doc\\" + str + ".txt");

		if (f.exists()) {
			flag = true;
		}

		return flag;
	}

	// --------------------------------------------------------------------------------------------------
	public void save(String str) { // 회원정보 직렬화
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

	public BusVO restart(String str) { // 회원정보 역직렬화
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

//// 주민등록번호 앞에 6자리 + '-' + 뒤에 7자리가 아니면 flag에 true
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
//			System.out.println("잘못 된 주민등록번호입니다. 다시 입력해주세요.");
//			flag = true;
//		}

//// 주민등록번호 7번째 자리에 '-' 없으면 '-' 추가 123-456-7891234  123-45-6789123
//if (!(str.length() < 7)) {
//	char ch = str.charAt(6);
//	if (ch != '-') {
//		StringBuffer sb = new StringBuffer(str);
//		sb.insert(6, '-');
//		str = sb.toString();
//		vo.setJumin(str);
//	}
//}


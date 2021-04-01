package com.project1;

import java.io.Serializable;

public class adminVO implements Serializable {

	public static String[] busLocation = { "����", "���Ϻ�", "��Ⳳ��", "����", "���", "�泲", "����", "����", "�뱸", "�λ�" };

	public static String[][] busAll = { { "������", "��ӹ����͹̳�", "��õ" }, // 0
			{ "�ϻ�", "����", "������" }, // 1
			{ "��õ", "��õ", "����" }, // 2
			{ "����", "����", "����" }, // 3
			{ "����", "û��", "��õ" }, // 4
			{ "���", "����", "õ��" }, // 5
			{ "����", "����", "����" }, // 6
			{ "����", "��õ", "����" }, // 7
			{ "����", "�뱸", "����" }, // 8
			{ "�λ�", "���", "â��" } }; // 9

	public static String[] busTime = { "06:30", "09:20", "11:50", "13:00", "15:00", "17:40", "19:50", "21:30",
			"22:20" };
	public static String[] busTime1 = { "09:20", "13:00", "17:20", "20:40" };
	public static String[] busTime2 = { "11:00", "17:30" };

	// ���ų���
	private String departure; // �������
	private String departureCity; // �����������
	private String destination; // ��������
	private String destinationCity; // ������������
	private String departureTime; // ��� �ð�
	private int busFare; // �������
	private int departureDay; // ��� ��¥
	private int inwon; // ���� ����

	// ��ȸ�� ����
	private static String nameB; // ��ȸ�� �̸�
	private static String juminB; // ��ȸ�� �ֹε�Ϲ�ȣ

	public static String[] getBusLocation() {
		return busLocation;
	}

	public static void setBusLocation(String[] busLocation) {
		adminVO.busLocation = busLocation;
	}

	public static String[][] getBusAll() {
		return busAll;
	}

	public static void setBusAll(String[][] busAll) {
		adminVO.busAll = busAll;
	}

	public static String[] getBusTime() {
		return busTime;
	}

	public static void setBusTime(String[] busTime) {
		adminVO.busTime = busTime;
	}

	public static String[] getBusTime1() {
		return busTime1;
	}

	public static void setBusTime1(String[] busTime1) {
		adminVO.busTime1 = busTime1;
	}

	public static String[] getBusTime2() {
		return busTime2;
	}

	public static void setBusTime2(String[] busTime2) {
		adminVO.busTime2 = busTime2;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public int getBusFare() {
		return busFare;
	}

	public void setBusFare(int busFare) {
		this.busFare = busFare;
	}

	public int getDepartureDay() {
		return departureDay;
	}

	public void setDepartureDay(int departureDay) {
		this.departureDay = departureDay;
	}

	public static String getNameB() {
		return nameB;
	}

	public void setNameB(String nameB) {
		this.nameB = nameB;
	}

	public static String getJuminB() {
		return juminB;
	}

	public void setJuminB(String juminB) {
		this.juminB = juminB;
	}
	public int getInwon() {
		return inwon;
	}

	public void setInwon(int inwon) {
		this.inwon = inwon;
	}
}

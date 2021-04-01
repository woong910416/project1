package com.project1;

import java.io.Serializable;

public class adminVO implements Serializable {

	public static String[] busLocation = { "서울", "경기북부", "경기남부", "강원", "충북", "충남", "전북", "전남", "대구", "부산" };

	public static String[][] busAll = { { "동서울", "고속버스터미널", "인천" }, // 0
			{ "일산", "구리", "의정부" }, // 1
			{ "부천", "이천", "평택" }, // 2
			{ "강릉", "속초", "원주" }, // 3
			{ "충주", "청주", "제천" }, // 4
			{ "논산", "세종", "천안" }, // 5
			{ "군산", "전주", "정읍" }, // 6
			{ "광주", "순천", "목포" }, // 7
			{ "경주", "대구", "포항" }, // 8
			{ "부산", "울산", "창원" } }; // 9

	public static String[] busTime = { "06:30", "09:20", "11:50", "13:00", "15:00", "17:40", "19:50", "21:30",
			"22:20" };
	public static String[] busTime1 = { "09:20", "13:00", "17:20", "20:40" };
	public static String[] busTime2 = { "11:00", "17:30" };

	// 예매내역
	private String departure; // 출발지역
	private String departureCity; // 출발지역도시
	private String destination; // 도착지역
	private String destinationCity; // 도착지역도시
	private String departureTime; // 출발 시간
	private int busFare; // 버스요금
	private int departureDay; // 출발 날짜
	private int inwon; // 예매 개수

	// 비회원 정보
	private static String nameB; // 비회원 이름
	private static String juminB; // 비회원 주민등록번호

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

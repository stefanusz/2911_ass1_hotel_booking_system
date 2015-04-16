import java.util.*;
import java.text.DateFormatSymbols;

public class Room {

	String											roomNumber;
	String											roomType;
	Map<Integer, LinkedHashMap<Integer, String>>	reservationMap;

	/**
	 * Create the room object with different fields
	 * 
	 * @param roomNumber
	 *            room number for the particular room
	 * @param roomType
	 *            the type of room either single / double / triple
	 */

	public Room(String roomNumber, String roomType) {
		reservationMap = new LinkedHashMap<Integer, LinkedHashMap<Integer, String>>();

		for (int i = 0; i < 12; i++) {
			reservationMap.put(i, new LinkedHashMap<Integer, String>());
		}
		this.roomNumber = roomNumber;
		this.roomType = roomType;
	}

	/**
	 * To insert to the LinkedHashMap of LinkedHashMap for the reservation. All
	 * the inputs are received via Hotel object.
	 * 
	 * @param username
	 *            Username for the particular reservation
	 * @param month
	 *            The particular month for reservation
	 * @param date
	 *            The particular start date for reservation
	 * @param numNight
	 *            The number of night for the reservation
	 * @return It will return a true or false status depending on the outcome of
	 *         insertion.
	 */
	public boolean manualInsert(String username, String month, String date,
			String numNight) {
		boolean status = false;

		int nights = Integer.parseInt(numNight);
		int startDate = Integer.parseInt(date);
		int newMonth = changeMonthToInt(month);

		int counter = 0;

		while (counter != nights) {

			reservationMap.get(newMonth).put((startDate + counter), username);

			counter++;
		}
		return status;
	}

	/**
	 * Check whether a given date and number of night is a valid reservation or
	 * not.
	 * 
	 * @param startDate
	 *            given start date from Hotel class.
	 * @param numNight
	 *            give number night of a particular reservation.
	 * @return status either its true or false
	 */

	public boolean checkValidRes(Calendar startDate, int numNight) {

		Calendar newStart = (Calendar) startDate.clone();

		boolean status = true;

		for (int i = 0; i < numNight; i++) {

			if (reservationMap.get(newStart.get(Calendar.MONTH)).containsKey(
					newStart.get(Calendar.DATE))) {
				status = false;
				break;
			}

			newStart.add(Calendar.DATE, 1);

		}

		return status;
	}

	/**
	 * Add reservation into the linkedHashMap of linkedHashMap
	 * 
	 * @param username
	 *            the username from Hotel
	 * @param startDate
	 *            date for the start of a particular reservation via Hotel Class
	 * @param numNight
	 *            the number of night required for reservation
	 * @return status will return a true or false at the end
	 */

	public boolean addReservation(String username, Calendar startDate,
			int numNight) {

		boolean status = checkValidRes(startDate, numNight);

		// System.out.println("the numnight is "+ numNight);
		Calendar latestDate = (Calendar) startDate.clone();
		int counter = numNight;
		// System.out.println("the STATUS is "+ status);
		if (status) {

			// System.out.println("the counter "+ counter);
			while (counter != 0) {
				reservationMap.get(latestDate.get(Calendar.MONTH)).put(
						latestDate.get(Calendar.DATE), username);
				latestDate.add(Calendar.DATE, 1);
				counter--;
			}
		}

		return status;
	}

	/**
	 * This method is to cancel the reservation by taking 4 inputs String
	 * username, int month, int date, int numNight
	 * 
	 * @param username
	 *            the username from Hotel
	 * @param month
	 *            the month from Hotel
	 * @param date
	 *            the date from Hotel
	 * @param numNight
	 *            the number of night from Hotel
	 * @return status it will return either true or false
	 */

	public boolean cancelReservation(String username, int month, int date,
			int numNight) {

		boolean status = false;

		int counter = 0;

		while (counter != numNight) {

			String checker = reservationMap.get(month).get(date + counter);

			if (checker == null) {
				return status;
			}
			counter++;
		}

		int counter2 = 0;

		while (counter2 != numNight) {

			reservationMap.get(month).remove(date + counter2);
			status = true;
			counter2++;

		}

		return status;

	}

	/**
	 * To check whether a particular reservation exist in the hashmap or not.
	 * 
	 * @param username
	 *            Get the username from the hotel
	 * @param month
	 *            Get the month from hotel
	 * @param date
	 *            Get the date from hotel
	 * @param numNight
	 *            Get the number of night
	 * @return It will return a status either true or false depending on the
	 *         result
	 */
	public boolean checkResInHash(String username, int month, int date,
			int numNight) {
		boolean status = false;

		int counter = 0;

		while (counter != numNight) {

			String checker = reservationMap.get(month).get(date + counter);

			if (checker == null) {
				return status;
			}
			counter++;
		}

		status = true;
		return status;
	}

	/**
	 * This method will change the month in the given input to an integer from a
	 * 3 letters string.
	 * 
	 * @param month
	 *            The input in the form of String.
	 * @return It will return an integer upon conversion of the string month.
	 */
	public static int changeMonthToInt(String month) {
		// TO SET MONTH TO INT FROM THE INPUT.
		int newMonth = 0;
		if (month.equalsIgnoreCase("jan")) {
			newMonth = 0;
		} else if (month.equalsIgnoreCase("feb")) {
			newMonth = 1;
		} else if (month.equalsIgnoreCase("mar")) {
			newMonth = 2;
		} else if (month.equalsIgnoreCase("apr")) {
			newMonth = 3;
		} else if (month.equalsIgnoreCase("may")) {
			newMonth = 4;
		} else if (month.equalsIgnoreCase("jun")) {
			newMonth = 5;
		} else if (month.equalsIgnoreCase("jul")) {
			newMonth = 6;
		} else if (month.equalsIgnoreCase("aug")) {
			newMonth = 7;
		} else if (month.equalsIgnoreCase("sep")) {
			newMonth = 8;
		} else if (month.equalsIgnoreCase("oct")) {
			newMonth = 9;
		} else if (month.equalsIgnoreCase("nov")) {
			newMonth = 10;
		} else if (month.equalsIgnoreCase("dec")) {
			newMonth = 11;
		}

		return newMonth;
	}

}

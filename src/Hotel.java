import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class Hotel {

	ArrayList<Room>	rooms;
	String			hotelName;

	private String	roomNumber;
	private int		roomType;

	/**
	 * Create the Hotel object with all the different fields.
	 * 
	 * @rooms ArrayList that will contain the object rooms for the particular
	 *        hotel.
	 * @param input
	 *            The name of the Hotel from the given input
	 */
	public Hotel(String input) {

		hotelName = input;
		rooms = new ArrayList<Room>();

	}

	/**
	 * To return you the particular short style month. E.g jan, feb and etc.
	 * 
	 * @input Get a particular month from an integer input
	 * @return a month in short 3 letters style
	 */
	public String getMonth(int input) {
		return new DateFormatSymbols().getShortMonths()[input - 1];
	}

	/**
	 * Check that the given start date, room type and number of nights are
	 * allowed to be booked in. The variables are received from System Manager
	 * Class.
	 * 
	 * @param startDate
	 *            the start date of the reservation
	 * @param roomType
	 *            the type of room it required single/double/triple
	 * @param numNight
	 *            the number of night it required
	 * @param roomNumber
	 *            this is an array list of string of room numbers to cross check
	 *            in System Manager Class
	 * @return Room It will return the object Room
	 */
	public Room checkAllRoom(Calendar startDate, String roomType, int numNight,
			ArrayList<String> roomNumber) {

		Room roomFound = null;
		boolean status;

		for (Room r : rooms) {

			if (r.roomType.equalsIgnoreCase(roomType)
					&& (!roomNumber.contains(r.roomNumber))) {

				status = r.checkValidRes(startDate, numNight);

				if (status) {
					roomFound = r;
					break;
				}

			}
		}

		return roomFound;

	}

	/**
	 * This method is called via System Manager to loop through all the rooms in
	 * the particular hotel.
	 * 
	 * @param hotelName
	 *            The hotel name will be given for printing.
	 *  It will return nothing and only printing.
	 */
	public void loopThroughRoom(String hotelName) {
		for (Room r : rooms) {

			int numNights = 0;
			int month = 1;
			String user = "";
			int date = 0;
			for (int i = 0; i < 12; i++) {
				for (int j = 1; j < 32; j++) {

					if (r.reservationMap.get(i).containsKey(j)) {

						if (!user.equals(r.reservationMap.get(i).get(j))) {
							user = r.reservationMap.get(i).get(j);
							date = j;
							numNights++;

						} else {

							numNights++;
						}
					} else {

						if (numNights != 0) {
							String monthString = getMonth(month);
							System.out.println(hotelName + " " + r.roomNumber
									+ " " + monthString + " " + date + " "
									+ numNights + " " + user);
							month++;
						}

						numNights = 0;
						user = "";
					}
				}
			}

		}
	}

	/**
	 * To add reservation into the 2d LinkedHashMap
	 * 
	 * @param username
	 *            The name of the user that want to book given via System
	 *            Manager
	 * @param startDate
	 *            The start date of the reservation
	 * @param numNight
	 *            The number of nights they want to book
	 * @param roomType
	 *            The type of room they want to book. Either
	 *            single/double/triple
	 * @return checkStatus It will return a true or false indicating whether
	 *         adding to the LinkedHashMap has been sucessfull or not.
	 */
	public boolean addReservationViaRoom(String username, Calendar startDate,
			int numNight, String roomType) {

		boolean checkStatus = false;
		for (Room r : rooms) {

			if (r.roomType.equals(roomType)) {

				boolean status = r
						.addReservation(username, startDate, numNight);

				if (status == true) {
					checkStatus = true;

					break;
				}
			}

		}

		return checkStatus;
	}

	/**
	 * To get a particular room index in a given hotel.
	 * 
	 * @param roomNum
	 *            Need to supply the room number in a string.
	 * @return The return will be an integer.
	 */
	public int getRoomIndex(String roomNum) {
		int result = 0;

		for (int index = 0; index < rooms.size(); index++) {
			if (rooms.get(index).roomNumber.equals(roomNum)) {
				result = index;
			}
		}
		return result;

	}

	/**
	 * To add room into a particular hotel.
	 * 
	 * @param roomNumber
	 *            the room number of the room that you want to add
	 * @param roomType
	 *            the type of the room it will be either single/double/triple
	 * There is no return.
	 */
	public void addRoom(String roomNumber, String roomType) {

		boolean status = rooms.contains(roomNumber);

		if (status == true) {
			System.out.println("The room " + roomNumber + "already exist!");
			return;
		} else {
			Room newRoom = new Room(roomNumber, roomType);
			rooms.add(newRoom);

		}

	}

	/**
	 * To view all the room a particular hotel have.
	 */
	public void viewAllRoom() {

		for (Room r : rooms) {

			System.out.print(" Number-> " + r.roomNumber + " type->"
					+ r.roomType);
		}
	}

	/**
	 * Cancelling a particular reservation.
	 * 
	 * @param username
	 *            The name user that want to cancel the room
	 * @param roomNum
	 *            The room number that wants to be cancelled
	 * @param month
	 *            The month of the booking
	 * @param date
	 *            The date of the booking that wants to be cancelled
	 * @param numNight
	 *            How many more days after the date given that want to be
	 *            cancelled.
	 * @return status It will return a true or a false depending on the outcome
	 */
	public boolean cancelViaHotel(String username, String roomNum, int month,
			int date, int numNight) {
		boolean status = false;

		for (Room r : rooms) {

			if (r.roomNumber.equals(roomNum)) {

				status = r.cancelReservation(username, month, date, numNight);

			}
		}

		return status;

	}

	/**
	 * To get a particular room data from the given room number.
	 * 
	 * @param roomNumber
	 *            Take in the room number from the want that you want to find
	 *            out.
	 * @return It will return a room object
	 */
	public Room getRoomData(String roomNumber) {

		Room foundRoom = null;

		for (Room r : rooms) {

			if (r.roomNumber.equalsIgnoreCase(roomNumber)) {
				foundRoom = r;
			}
		}

		return foundRoom;

	}

	/**
	 * To check whether a reservation exist.
	 * 
	 * @param username
	 *            Need username to check
	 * @param roomNum
	 *            Need the room number
	 * @param month
	 *            Need the particular month
	 * @param date
	 *            Need the particular date
	 * @param numNight
	 *            Need the number of nights
	 * @return It will return a boolean status either true or false depending on
	 *         the outcome.
	 */
	public boolean checkReservationExistInRoom(String username, String roomNum,
			int month, int date, int numNight) {

		boolean status = false;

		for (Room r : rooms) {

			if (r.roomNumber.equalsIgnoreCase(roomNum)) {

				status = r.checkResInHash(username, month, date, numNight);
			}

		}

		return status;
	}

	/**
	 * To insert a manual reservation, the inputs are received via System
	 * Manager. The function will check for the particular hotel room and then
	 * go deeper to room.
	 * 
	 * @param username
	 *            Username for the reservation name
	 * @param month
	 *            Month for the reservation
	 * @param date
	 *            Date for the reservation
	 * @param numNight
	 *            Number of nights for the reservation
	 * @param roomType
	 *            The particular room type.
	 * @param numRoom
	 *            The room number
	 * @return It will return a status true or false depending on the outcome.
	 */
	public boolean manualInsert(String username, String month, String date,
			String numNight, String roomType, String numRoom) {

		boolean status = false;

		for (Room r : rooms) {
			if (r.roomNumber.equalsIgnoreCase(numRoom)) {
				status = r.manualInsert(username, month, date, numNight);
			}
		}

		return status;

	}

}

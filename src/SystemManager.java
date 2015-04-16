import java.util.*;

public class SystemManager {

	// PRIVATE FIELDS FOR ADDING HOTEL AND ROOM.
	private String		hotelName;
	private int			roomType;
	private int			roomNumber;
	ArrayList<Hotel>	hotels;
	private boolean		status;

	/**
	 * Construct the System Manager object with an arraylist of Hotel inside it.
	 */
	public SystemManager() {
		hotels = new ArrayList<Hotel>();
	}

	/**
	 * A method to input a single insertion base on the hotel name and number.
	 * @param input The form is a string of array from the main hotel booking system. 
	 * @return It will return a true or false status depending on the outcome. 
	 */
	public boolean singleInsertion(String[] input) {
		boolean status = false;
		
		
		//GETTING EACH INPUT FROM THE PARTICULAR ARRAY POSITION
		String username = input[1];
		String month = input[2];
		String date = input[3];
		String numNight = input[4];
		String roomType = input[5];
		String hotelName = input[7];
		String numRoom = input[8];

		for (Hotel h : hotels) {

			if (h.hotelName.equalsIgnoreCase(hotelName)) {
				System.out.println("enter here");
				status = h.manualInsert(username, month, date, numNight,
						roomType, numRoom);

			}

		}
		return status;
	}

	/**
	 * To print all the reservation a particular hotel have.
	 * 
	 * @param hotelName
	 *            You will supply the name of the Hotel.
	 */
	public void printAllReservation(String hotelName) {

		int hotelIndex = getHotelIndex(hotelName);

		if (hotelIndex == -1) {

		} else {

			hotels.get(hotelIndex).loopThroughRoom(hotelName);
		}

	}

	/**
	 * Add a new hotel to the array list.
	 * 
	 * @param newHotel
	 *            A hotel object will be supplied when adding.
	 */
	public void addHotel(Hotel newHotel) {

		this.status = hotelExist(newHotel.hotelName);

		if (status != true) {
			hotels.add(newHotel);
		}

	}

	/**
	 * This method will check whether the booking requested can be made under
	 * this particular hotel. It will go through all the rooms make sure that it
	 * can accommodate all the rooms requested if it true then it will process
	 * the reservation.
	 * 
	 * @param input
	 *            WIll be an array containing a full stream of input from the
	 *            particular file. With the command Book
	 * @return status Either true or false to indicate whether its sucessfull or
	 *         not.
	 */
	public boolean hotelToAccomomodateRes(String[] input) {

		boolean status = false;
		String hotelToBeBook;
		int originalRoomsNeeded = 0;
		int roomTotalChecker;

		String hotelToUse;
		
		// GET ORIGINAL ROOM NUMBER NEEDED FOR THE BOOKING.
		for (int a = 5; a < input.length; a = a + 2) {
			if (input[a].equalsIgnoreCase("single")) {
				roomTotalChecker = changeStringToInt(input[a + 1]);
				originalRoomsNeeded = roomTotalChecker + originalRoomsNeeded;
			}
			if (input[a].equalsIgnoreCase("double")) {
				roomTotalChecker = changeStringToInt(input[a + 1]);
				originalRoomsNeeded = roomTotalChecker + originalRoomsNeeded;
			}
			if (input[a].equalsIgnoreCase("triple")) {
				roomTotalChecker = changeStringToInt(input[a + 1]);
				originalRoomsNeeded = roomTotalChecker + originalRoomsNeeded;
			}
		}

		// PROCESSING INPUT HERE AND SET THEM TO VARIABLE. 
		String username = input[1];
		int newMonth = changeMonthToInt(input[2]);
		int newDate = changeStringToInt(input[3]);
		int numNight = changeStringToInt(input[4]);

		Calendar start = new GregorianCalendar();
		start.set(Calendar.DATE, newDate);
		start.set(Calendar.MONTH, newMonth);

		//START TO LOOP THROUGH THE HOTEL. 
		for (Hotel h : hotels) {

			hotelToUse = h.hotelName;
			ArrayList<String> roomNumber = new ArrayList<String>();

			//TO TAKE INTO ACCOUNT ALL PARAMETER GIVEN AFTER THE INDEX 5 OF THE ARRAY STRING INPUT AND JUST CHECKING
			//WHETHER IT COULD BE PUT INTO THE SAME HOTEL
			for (int i = 5; i < input.length; i++) {

				// ALL THE DIFFERENT IF FOR THE DIFFERENT ROOM TYPE 
				if (input[i].equals("single")) {

					int numRoom = changeStringToInt(input[i + 1]);

					Room found1 = null;

					while (numRoom != 0) {
			
						found1 = h.checkAllRoom(start, "1", numNight,
								roomNumber);

						if (found1 == null) {
							break;
						}

						if (roomNumber.contains(found1.roomNumber)) {

							continue;

						} else {
							roomNumber.add(found1.roomNumber);

							numRoom--;
						}

					}

				}

				if (input[i].equals("double")) {

					int numRoom = changeStringToInt(input[i + 1]);

					Room found1 = null;

					while (numRoom != 0) {

						found1 = h.checkAllRoom(start, "2", numNight,
								roomNumber);
						if (found1 == null) {

							break;
						}

						if (roomNumber.contains(found1.roomNumber)) {

							continue;

						} else {
							roomNumber.add(found1.roomNumber);

							numRoom--;

						}
					}

				}
				if (input[i].equals("triple")) {

					int numRoom = changeStringToInt(input[i + 1]);

					Room found1 = null;

					while (numRoom != 0) {

						found1 = h.checkAllRoom(start, "3", numNight,
								roomNumber);

						if (found1 == null) {

							break;
						} else if (roomNumber.contains(found1.roomNumber)) {
							continue;

						} else {
							roomNumber.add(found1.roomNumber);
							numRoom--;

						}

					}
				}

			}

			// IF THE CONDITION IS MET E.G ALL THE BOOKING FALL UNDER THE SAME HOTEL THEN
			// PROCESS THE BOOKING
			if (originalRoomsNeeded == roomNumber.size()) {
				hotelToBeBook = h.hotelName;

				for (int i = 5; i < input.length; i++) {
					if (input[i].equals("single")) {

						int numRoom1 = changeStringToInt(input[i + 1]);

						while (numRoom1 != 0) {

							h.addReservationViaRoom(username, start, numNight,
									"1");

							numRoom1--;
						}

					}
					if (input[i].equals("double")) {

						int numRoom = changeStringToInt(input[i + 1]);

						while (numRoom != 0) {
							// System.out.println("the hotel is "+ h.hotelName);
							h.addReservationViaRoom(username, start, numNight,
									"2");

							numRoom--;
						}

					}
					if (input[i].equals("triple")) {

						int numRoom = changeStringToInt(input[i + 1]);

						while (numRoom != 0) {
							// System.out.println("the hotel is "+ h.hotelName);
							h.addReservationViaRoom(username, start, numNight,
									"3");
							numRoom--;
						}

					}

				}

				//PRINT OUT AFTER SUCESSFULL PRINT
				System.out.print(hotelToBeBook + " ");
				for (String s : roomNumber) {
					System.out.print(s + " ");
				}
				System.out.println();
				status = true;
				break;
			}

		}

		return status;

	}

	/**
	 * This method is to add the reservation itself to the LinkedHashMap of
	 * LinkedHashMap in the Room class down the road. This is to iterate through
	 * it.
	 * 
	 * @param username
	 *            The user for the booking
	 * @param startDate
	 *            The start of date of the booking
	 * @param numNight
	 *            The number of night for that particular reservation
	 * @param roomType
	 *            The type of room that the user want.
	 */
	public void processReservation(String username, Calendar startDate,
			int numNight, String roomType) {

		boolean addStatus = false;
		for (Hotel h : hotels) {
			addStatus = h.addReservationViaRoom(username, startDate, numNight,
					roomType);

			if (addStatus == true) {
				System.out.println("SUCESS");
				break;
			}
		}

	}

	/**
	 * To view all the hotel in the hotel list.
	 */
	public void viewAll() {

		for (Hotel h : hotels) {
			System.out.println("the hotel is -> " + h.hotelName);
		}
	}

	/**
	 * To check whether a given hotel exist in the array List or not.
	 * 
	 * @param hotelName
	 *            The name of the hotel have to be supplied
	 * @return It will return a true or false depending on the outcome.
	 */
	public boolean hotelExist(String hotelName) {

		for (Hotel h : hotels) {

			if (h.hotelName.equals(hotelName)) {
				return true;
			}

		}
		return false;
	}

	/**
	 * To get the index of a particular hotel. The name of the hotel have to be
	 * supplied.
	 * 
	 * @param hotelname
	 *            This will be supplied for the given hotel name.
	 * @return Will return an integer for that particular hotel index.
	 */
	public int getHotelIndex(String hotelname) {
		int result = -1;

		for (int index = 0; index < hotels.size(); index++) {
			if (hotels.get(index).hotelName.equalsIgnoreCase(hotelname)) {
				result = index;
			}
		}

		return result;

	}

	/**
	 * To print all the available room in a particular hotel.
	 */
	public void viewAllRoomsInHotel() {

		for (Hotel h : hotels) {
			System.out.print("Hotel: " + h.hotelName + " ");

			if (h.rooms.size() == 0) {
				System.out.print("No Rooms");
			} else {
				for (int i = 0; i < h.rooms.size(); i++) {
					System.out.print(" Rm:" + h.rooms.get(i).roomNumber + ","
							+ h.rooms.get(i).roomType);
				}
			}
			System.out.println();
		}

	}

	/**
	 * To view all the rooms in a particular hotel.
	 * 
	 * @param hotelName
	 */
	public void viewAHotelRooms(String hotelName) {

		int indexHotel = getHotelIndex(hotelName);
		boolean status = hotelExist(hotelName);

		if (status == true) {

			for (Room r : hotels.get(indexHotel).rooms) {

				System.out.println("Room num: " + r.roomNumber + " type: "
						+ r.roomType);
			}

		} else {
			System.out.println("Hotel doesn't exist!");
		}

	}

	/**
	 * This method will process the cancellation request for a particular
	 * booking.
	 * 
	 * @param input
	 *            The input will be an array of string from Hotel Booking System
	 * @return It will return a true or false depending on the outcome of the
	 *         procedure.
	 */
	public boolean cancelReservationSystem(String[] input) {

		boolean status;
		status = false;
		String username = input[1];
		String hotelName = input[2];
		String roomNum = input[3];
		int month = changeMonthToInt(input[4]);
		int date = changeStringToInt(input[5]);
		int numNight = changeStringToInt(input[6]);

		for (Hotel h : hotels) {

			if (h.hotelName.equalsIgnoreCase(hotelName)) {

				status = h.cancelViaHotel(username, roomNum, month, date,
						numNight);

			}

		}

		return status;
	}

	public boolean reservationExist(String[] input) {

		boolean status = false;

		String username = input[1];
		String hotelName = input[2];
		String roomNum = input[3];
		int month = changeMonthToInt(input[4]);
		int date = changeStringToInt(input[5]);
		int numNight = changeStringToInt(input[6]);

		for (Hotel h : hotels) {

			if (h.hotelName.equalsIgnoreCase(hotelName)) {
				status = h.checkReservationExistInRoom(username, roomNum,
						month, date, numNight);
			}

		}

		return status;

	}

	public Room getRoomInfo(String[] input) {

		Room foundRoom = null;

		String hotelName = input[2];
		String roomNumber = input[3];

		for (Hotel h : hotels) {

			if (h.hotelName.equalsIgnoreCase(hotelName)) {

				foundRoom = h.getRoomData(roomNumber);

			}

		}

		return foundRoom;
	}

	/**
	 * To change any number string to integer and return it.
	 * 
	 * @param anyInput
	 *            input from the user
	 * @return It will return an integer after transformation
	 */
	public static int changeStringToInt(String anyInput) {
		int newInt = 0;

		newInt = Integer.parseInt(anyInput);

		return newInt;
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

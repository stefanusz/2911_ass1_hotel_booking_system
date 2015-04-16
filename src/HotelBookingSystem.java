import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class HotelBookingSystem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SystemManager newEntry = new SystemManager();

		// INPUT FROM A FILE AND READING FROM IT TESTER.
		try {
			Scanner s = new Scanner(new FileReader(args[0])); // args[0] is the
																// first command
																// line argument

			// TO GET ALL THE INPUT FROM THE FILE AND SPLIT IT BASE ON THE
			// COMMAND.
			while (s.hasNext() && s.hasNextLine()) {

				String[] input = s.nextLine().split(" ");

				if (input[0].equalsIgnoreCase("Book")) {

					newEntry.hotelToAccomomodateRes(input);

				}

				if (input[0].equalsIgnoreCase("Hotel")) {

					String name = input[1];
					Hotel newHotel1 = new Hotel(name);
					newEntry.addHotel(newHotel1);

					int hotelIndex = newEntry.getHotelIndex(name);
					newEntry.hotels.get(hotelIndex).addRoom(input[2], input[3]);

				}
				if (input[0].equalsIgnoreCase("Change")) {

					boolean status3 = newEntry.reservationExist(input);

					if (status3) {
						Room oldRoom = newEntry.getRoomInfo(input);

						// GETING ALL THE OLD ROOM BOOKING INFORMATION
						String username = input[1];
						String month = input[4];
						String date = input[5];
						String numNight = input[6];
						String roomType = null;

						if (oldRoom.roomType.equalsIgnoreCase("1")) {
							roomType = "single";
						} else if (oldRoom.roomType.equalsIgnoreCase("2")) {
							roomType = "double";
						} else if (oldRoom.roomType.equalsIgnoreCase("3")) {
							roomType = "triple";
						}

						// PUT ALL OLD ROOM BOOKING INFORMATION TO A ARRAY OF
						// STRING
						String[] oldInputBooking = new String[9];
						oldInputBooking[0] = "Book";
						oldInputBooking[1] = username;
						oldInputBooking[2] = month;
						oldInputBooking[3] = date;
						oldInputBooking[4] = numNight;
						oldInputBooking[5] = roomType;
						oldInputBooking[6] = "1";
						oldInputBooking[7] = input[2];
						oldInputBooking[8] = input[3];

						if (input[10].equalsIgnoreCase("1")) {

							roomType = "single";
						} else if (input[10].equalsIgnoreCase("2")) {
							roomType = "double";
						} else if (input[10].equalsIgnoreCase("3")) {
							roomType = "triple";
						}

						// PUT ALL NEW RES REQUEST IN A ARRAY OF STRING
						String[] newBooking = new String[7];
						newBooking[0] = "Book";
						newBooking[1] = username;
						newBooking[2] = input[7];
						newBooking[3] = input[8];
						newBooking[4] = input[9];
						newBooking[5] = roomType;
						newBooking[6] = "1";

						newEntry.cancelReservationSystem(input);

						boolean newBookingInsertion = newEntry
								.hotelToAccomomodateRes(newBooking);
						// System.out.println("the status of newbooking "+
						// newBookingInsertion);

						if (newBookingInsertion == false) {

							newEntry.singleInsertion(oldInputBooking);

						}

					} else {
						System.out.println("Change rejected");
					}

				}
				if (input[0].equalsIgnoreCase("Cancel")) {

					boolean status2 = newEntry.cancelReservationSystem(input);

					if (status2) {
						System.out.println("Reservation cancelled");
					} else {
						System.out.println("Cancellation rejected");
					}
				}
				if (input[0].equalsIgnoreCase("Print")) {

					newEntry.printAllReservation(input[1]);
				}

			}
			s.close();
		} catch (FileNotFoundException e) {
		}

	}

}

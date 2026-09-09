/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.sms.hotelreservationsystem;




/**
 *
 * @author Riel
 */

public class HotelReservationSystem {

    public static void main(String[] args) {
        Room room1 = new Room(1000,"DELUXE",2000);
        Room room2 = new Room(253, "PREMIUM",3500);
        Room room3 = new Room(531, "SINGLE", 1500);
        
        
        room1.setStatus(RoomStatus.OCCUPIED);
        System.out.println("Room 1 available? " + room1.isAvailable());
        
        
        System.out.println("Room 2 available?: " + room2.isAvailable());
        
        
        room3.setPricePerNight(-500);
        
        
        
    }
}

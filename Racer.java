/*
 * Programmer: Jackson Lee
 * Program: RaceRunner
 * Purpose: Take in a racer file from the user and determine who wins the race
 */
import java.util.Scanner;
import java.io.*;
public class Racer {
    private static int numberOfRacers;
    private static int raceLength;
    private static Racer[] drivers;
    private double racerSpeed;
    private int pitStop;
    private double driveTime;
    private double pitStopTime;
    private double total;
    private String name;
    public static void main(String[]args) {
        Racer racer = new Racer();
        racer.getInputFile();
    }
    //gets input file from user
    private void getInputFile() {
        System.out.print("Please choose an input file: ");
        Scanner scan = new Scanner(System.in);
        String fileName = scan.nextLine();
        System.out.println("--------------------");
        
        readInputFile(fileName);
    }
    //reads input file
    private void readInputFile(String inFile) {
        try {
            Scanner scan = new Scanner(new File(inFile));
            numberOfRacers = scan.nextInt();
            raceLength = scan.nextInt();
            drivers = new Racer[numberOfRacers];
            
            scan.useDelimiter("[0-9]+");
            int i = 0;
            
            while(scan.hasNext()) {
                String token = scan.next();
                if(token.trim().length() > 0) {
                    Racer raceCarDriver = new Racer();
                    raceCarDriver.name = token.trim();
                    drivers[i] = raceCarDriver;
                    i++;
                }
            }
            
            Scanner scanNumbers = new Scanner(new File(inFile));
            scanNumbers.nextInt();
            scanNumbers.nextInt();
            
            scanNumbers.useDelimiter("\\D+");
            i = 0;
            
            while(scanNumbers.hasNextInt()) {
                drivers[i].racerSpeed = scanNumbers.nextInt();
                drivers[i].pitStop = scanNumbers.nextInt();
                i++;
            }
            
        } catch(IOException e) {
            System.out.println("Failed to open file: "+inFile);
            System.out.println("Program terminated.");
        }
        
        selectWinner();
    }
    //retruns the winner of the race
    public void selectWinner() {
        int hours = 0;
        int minutes = 0;
        for(int i = 0; i < numberOfRacers; i++) {
            drivers[i].driveTime = 60 * (raceLength / drivers[i].racerSpeed);
            drivers[i].pitStopTime = 5 * ((raceLength / drivers[i].pitStop));
            hours = (int)(drivers[i].driveTime / 60);
            minutes = (int)(drivers[i].driveTime - (hours * 60) + drivers[i].pitStopTime);
            drivers[i].total = (60 * hours) + minutes;
            System.out.println(drivers[i].name + ": " + hours + " hr. " + minutes + " min. ");
        }
        
        double minTime = 10000000;
        String winner = " ";
        for(int j = 0; j < numberOfRacers; j++) {
            if(drivers[j].total < minTime) {
                minTime = drivers[j].total;
                winner = drivers[j].name;
            }
        }
        
        System.out.println("--------------------");
        System.out.println("Winner: " + winner);
    }
}
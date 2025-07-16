import java.util.List;
import java.util.Scanner;

public class IRCTCAPP
{
    private final Scanner scanner=new Scanner(System.in);

    private final UserService userService=new UserService();

    private final BookingService bookingService=new BookingService();

    public static void main(String[] args){

        new IRCTCAPP().start();
    }

    public void start(){
        while(true){
            System.out.println("\n ----------Welcome to IRCTC App ---------");
            if(!userService.isLoggedIn()){
                System.out.println("1. Register:");
                System.out.println("2. Login:");
                System.out.println("3. Exit:");
                System.out.println("Enter choice");

                int choice=scanner.nextInt();

                switch (choice){
                    case 1 -> register();
                    case 2 -> login();
                    case 3 -> exitApp();
                    default -> System.out.println("Invalid Choice.");
                }
            }
            else {
                showUserMenu();
            }
        }
    }

    public void register(){
        System.out.println("Enter Username: ");
        String username= scanner.next();
        System.out.println("Enter password: ");
        String password= scanner.next();
        System.out.println("Enter Full name: ");
        scanner.nextLine();
        String fullName= scanner.nextLine();
        System.out.println("Enter Contact: ");
        String contact=scanner.next();

        userService.registerUser(username,password,fullName,contact);
    }

    public void login(){
        System.out.println("Enter username: ");
        String username= scanner.next();
        System.out.println("Enter Password: ");
        String password= scanner.next();

        userService.loginUser(username, password);
    }

    public void showUserMenu(){
        while (userService.isLoggedIn()){
            System.out.println("\n ------------ user menu---------");
            System.out.println("1. search trains:");
            System.out.println("2. book ticket:");
            System.out.println("3. view my ticket:");
            System.out.println("4. cancel ticket:");
            System.out.println("5. view all tickets:");
            System.out.println("6. logout:");
            System.out.println("Enter choice:");

            int choice = scanner.nextInt();
            switch (choice){
                case 1 -> searchTrain();
                case 2 -> bookTicket();
                case 3 -> viewMyTicket();
                case 4 -> cancelTicket();
                case 5 -> bookingService.listAllTrains();
                case 6 -> userService.logOutUser();
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void searchTrain(){
        System.out.println("enter source station: ");
        String source = scanner.next();
        System.out.println("enter destination station: ");
        String destination = scanner.next();

        List<Train> trains= bookingService.searchTrain(source, destination);
        if (trains.isEmpty()){
            System.out.println("No trains found between " + source + "and "+ destination);
            return;
        }
        System.out.println("Trains found:");
        for (Train train:trains){
            System.out.println(train);
        }
        System.out.println("Do you want to book ticket ?(Yes/No):");
        String choice = scanner.next();
        if (choice.equalsIgnoreCase("Yes")){
            System.out.println("Enter Train ID to book:");
            int trainID= scanner.nextInt();
            System.out.println("enter numbers of seats:");
            int seats= scanner.nextInt();

            Ticket ticket = bookingService.bookTicket(userService.getCurrentUser(),trainID,seats);
            if (ticket!=null){
                System.out.println("Booking succesfull");
                System.out.println(ticket);
            }
            else {
                System.out.println("returning to user menu");

            }
        }
    }

    private void bookTicket(){
        System.out.println("enter source station: ");
        String source = scanner.next();
        System.out.println("enter destination station: ");
        String destination = scanner.next();

        List<Train> trains= bookingService.searchTrain(source, destination);
        if (trains.isEmpty()){
            System.out.println("No trains found between " + source + "and "+ destination);
            return;
        }
        System.out.println("Available trains ");
        for (Train train:trains){
            System.out.println(train);
        }

        System.out.println("Enter Train ID to book:");
        int trainID= scanner.nextInt();
        System.out.println("enter numbers of seats:");
        int seats= scanner.nextInt();

        Ticket ticket = bookingService.bookTicket(userService.getCurrentUser(),trainID,seats);
        if (ticket!=null){
            System.out.println("Booking succesfull");
            System.out.println(ticket);
        }

    }
    private void viewMyTicket(){
        List<Ticket> ticketByUser = bookingService.getTicketByUser(userService.getCurrentUser());
        if (ticketByUser.isEmpty()){
            System.out.println("No ticket booked yet:");
        }
        else {
            System.out.println("your ticket:");
            for (Ticket ticket:ticketByUser){
                System.out.println(ticket);
            }
        }
    }

    private void cancelTicket(){
        System.out.println("Enter ticket id to cancel ");
        int ticketID =scanner.nextInt();
        bookingService.cancelTicket(ticketID,userService.getCurrentUser());
    }

    public void exitApp(){
        System.out.println("Thank you for using IRCTC App.");
        System.exit(0);
    }
}

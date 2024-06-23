package HospitalManagement;

import javax.print.Doc;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagment {

private  static  String url="jdbc:mysql://localhost:3306/hospital";
private  static String username="root";

private  static  String password="root";

    public static void main(String[] args) {

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try
        {
            Connection connection = DriverManager.getConnection(url,username,password);

            Scanner scanner = new Scanner(System.in);

Patient  patient = new Patient(connection,scanner);

Doctor doctor = new Doctor(connection);


           while (true)
           {
               System.out.println("HOSPTTAL MANGAMENT SYSTEM ");

               System.out.println(" 1. add patients");
               System.out.println(" 2. view patients");
               System.out.println(" 3. view doctors");
               System.out.println(" 4. Book Appointment");
               System.out.println(" 5. Exit");

               int choice = scanner.nextInt();

               switch (choice)
               {
                   case 1:
                       //add patient

                       patient.addPatient();
                       break;
                   case  2:

                       patient.viewPatient();
                       System.out.println();
                       break;
                   case 3:
                       //view doctor

                       doctor.viewDoctor();
                       System.out.println();
                       break;

                   case 4:
                       //book appointtment
                       BookAppointment(patient,doctor,connection,scanner);

                       System.out.println();
                       break;

                   case 5:
                       return;

                   default:
                       System.out.println("invalid choice");


               }



           }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }



    }

    public static void BookAppointment(Patient patient, Doctor doctor,Connection connection, Scanner scanner)
    {

        System.out.println("Enter the patient id: ");

        int patient_id = scanner.nextInt();

        System.out.println("Enter the doctor id: ");

        int doctor_id = scanner.nextInt();

        System.out.println("Enter the appointment date in format YY-MM-DD : ");

        String Appointment_date = scanner.nextLine();

        if (patient.checkPatient(patient_id) && doctor.checkDoctor(doctor_id))
        {


            if(checkDoctorAvailability(patient_id,Appointment_date,connection))
            {
                String query="INSERT INTO appointment(patient_id,doctor_id,appointment_date) values(?,?,?)";

                try
                {
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                   preparedStatement.setInt(1,patient_id);
                   preparedStatement.setInt(2,doctor_id);
                   preparedStatement.setString(3,Appointment_date);

                   int affectedRow = preparedStatement.executeUpdate();
                   if (affectedRow>0)
                   {
                       System.out.println("Appointment successfull");
                   }else
                   {
                       System.out.println("Appointment unsuccessfull");
                   }


                }catch (SQLException e)
                {
                    e.printStackTrace();
                }


            }else {
                System.out.println("doctor or patient does not availability");
            }


        }else
        {
            System.out.println("Patient or doctor does not exit");
        }

    }


    public static boolean  checkDoctorAvailability(int doctor_id,String AppointmentDate, Connection connection)
    {

        String query = "SELECT COUNT(*) FROM appointment where doctor_id= ? AND appointment_date= ?";

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,doctor_id);
            preparedStatement.setDate(2, Date.valueOf(AppointmentDate));

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {

                int count = resultSet.getInt(1);
                if(count==0)
                {
                    return true;
                }else
                {
                    return false;
                }

            }else
            {
                return  false;
            }


        }catch (SQLException e)
        {
            e.printStackTrace();
        }
return false;
    }



}

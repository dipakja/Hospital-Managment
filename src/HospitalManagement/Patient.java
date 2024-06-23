package HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection,Scanner scanner)
    {
        this.connection=connection;
        this.scanner=scanner;
    }

    public  void addPatient() {
        System.out.println("Enter the Patient Name : ");

        String patient_name = scanner.next();

        System.out.println("Enter the Patient Age : ");
        int patient_age = scanner.nextInt();

        System.out.println("Enter the Patient Gender");
        String patient_gender = scanner.next();

        try {

            String query = "INSERT INTO patients(name,age,gender) values(?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, patient_name);
            preparedStatement.setInt(2, patient_age);
            preparedStatement.setString(3, patient_gender);


            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Insertion succefully");
            } else {
                System.out.println("failed to add patient");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        public void viewPatient()
        {
            String q = "SELECT * FROM patients";
            try
            {
             PreparedStatement preparedStatement = connection.prepareStatement(q);

                ResultSet resultSetset = preparedStatement.executeQuery();

                System.out.println("Patients Data");

                System.out.println("+--------+---------------------+-----------+---------+");
                System.out.println("|  Id    |        Name         |    Age    |  Gender |");
                System.out.println("+--------+---------------------+-----------+---------+");
                while (resultSetset.next())
                {
                    String name =resultSetset.getString("name");
                    int age =resultSetset.getInt("age");
                    int id =resultSetset.getInt("id");
                    String gender =resultSetset.getString("gender");

                    System.out.println("|"+id+"|"+"|"+name+"|"+ "|"+age+"|"+"|"+gender+"|");

                }

            }catch (SQLException e)
            {
                e.printStackTrace();
            }



        }

        public boolean  checkPatient(int id)
        {

            String query ="select * from patients where id=?";


            try
            {
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setInt(1,id);

                 ResultSet resultSet =preparedStatement.executeQuery();
                 if (resultSet.next())
                 {
                     return true;
                 }



            }catch (SQLException e)
            {
            e.printStackTrace();
            }


return false;



        }





    }





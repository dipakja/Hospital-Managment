package HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;
    private Scanner scanner;

    public Doctor(Connection connection)
    {
        this.connection=connection;

    }

    public  void addPatient() {
        System.out.println("Enter the Doctor Name : ");
        String doctor_name = scanner.next();



        System.out.println("Enter the specialization/Department");
        String doctor_department = scanner.next();

        try {

            String query = "INSERT INTO doctors(name,department) values(?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, doctor_name);
            preparedStatement.setString(2, doctor_department);



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
    public void viewDoctor()
    {
        String q = "SELECT * FROM doctors";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(q);

            ResultSet resultSetset = preparedStatement.executeQuery();

            System.out.println("Doctors Data");

            System.out.println("+--------+---------------------+-----------+---------+");
            System.out.println("|  Id    |        Name         |     Department |");
            System.out.println("+--------+---------------------+--------------+");
            while (resultSetset.next())
            {
                String name =resultSetset.getString("name");
                int id =resultSetset.getInt("id");
                String department =resultSetset.getString("department");

                System.out.println("|"+id+"|"+"|"+name+"|"+ "|"+department+"|");

            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }



    }

    public boolean  checkDoctor(int id)
    {

        String query ="select * from doctors where id=?";


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

package grp16.tripmate.vehicle.database.Vehicle;

import grp16.tripmate.db.connection.DatabaseConnection;
import grp16.tripmate.db.connection.IDatabaseConnection;
import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;
import grp16.tripmate.vehicle.model.Vehicle.IVehicleFactory;
import grp16.tripmate.vehicle.model.Vehicle.Vehicle;
import grp16.tripmate.vehicle.model.Vehicle.VehicleDbColumnNames;
import grp16.tripmate.vehicle.model.Vehicle.VehicleFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VehicleDatabase implements IVehicleDatabase
{
    private final ILogger logger = new MyLoggerAdapter(this);

    IVehicleQueryBuilder queryBuilder;

    private final IDatabaseConnection dbConnection;

    private final IVehicleFactory factory;

    public VehicleDatabase()
    {
        queryBuilder = VehiclesQueryBuilder.getInstance();
        dbConnection = new DatabaseConnection();
        factory = VehicleFactory.getInstance();
    }

    public List<Vehicle> resultSetToVehicles(ResultSet rs) throws Exception
    {
        List<Vehicle> results = new ArrayList<>();
        while (rs.next()) {
            Vehicle vehicleObj = factory.getNewVehicle();
            vehicleObj.setId(rs.getInt(VehicleDbColumnNames.ID));
            vehicleObj.setName(rs.getString(VehicleDbColumnNames.NAME));
            vehicleObj.setNumberOfSeats(rs.getInt(VehicleDbColumnNames.NUMBEROFSEATS));
            vehicleObj.setDescription(rs.getString(VehicleDbColumnNames.DESCRIPTION));
            vehicleObj.setRegistrationNumber(rs.getString(VehicleDbColumnNames.REGISTRATIONNUMBER));
            vehicleObj.setIsAvailable(rs.getBoolean(VehicleDbColumnNames.ISAVAILABLE));
            vehicleObj.setIsForLongJourney(rs.getBoolean(VehicleDbColumnNames.ISFORLONGJOURNEY));
            vehicleObj.setRatePerKm(rs.getFloat(VehicleDbColumnNames.RATEPERKM));
            vehicleObj.setVehicleCategory(rs.getInt(VehicleDbColumnNames.VEHICLECATEGORY));
            results.add(vehicleObj);
        }
        return results;
    }
    public Vehicle resultSetToVehicle(ResultSet rs) throws Exception
    {
        Vehicle vehicleObj = factory.getNewVehicle();
        while (rs.next()) {
            vehicleObj.setId(rs.getInt(VehicleDbColumnNames.ID));
            vehicleObj.setName(rs.getString(VehicleDbColumnNames.NAME));
            vehicleObj.setNumberOfSeats(rs.getInt(VehicleDbColumnNames.NUMBEROFSEATS));
            vehicleObj.setDescription(rs.getString(VehicleDbColumnNames.DESCRIPTION));
            vehicleObj.setRegistrationNumber(rs.getString(VehicleDbColumnNames.REGISTRATIONNUMBER));
            vehicleObj.setIsAvailable(rs.getBoolean(VehicleDbColumnNames.ISAVAILABLE));
            vehicleObj.setIsForLongJourney(rs.getBoolean(VehicleDbColumnNames.ISFORLONGJOURNEY));
            vehicleObj.setRatePerKm(rs.getFloat(VehicleDbColumnNames.RATEPERKM));
            vehicleObj.setVehicleCategory(rs.getInt(VehicleDbColumnNames.VEHICLECATEGORY));
        }
        return vehicleObj;
    }

    public List<Vehicle> getVehiclesByPostId(int postId)
    {
        return new ArrayList<>();
    }
    public List<Vehicle> getAllVehicles()
    {
        String query = queryBuilder.getAllVehicles();
        return selectQueryExecute(query);
    }
    public List<Vehicle> getVehiclesByUserId(int userId)
    {
        return new ArrayList<>();
    }
    public Vehicle getVehicleById(int vehicleId)
    {
        String query = queryBuilder.getVehicleById(vehicleId);
        return getVehicleByIdSelectQueryExecute(query);
    }

    private Vehicle getVehicleByIdSelectQueryExecute(String query)
    {
        Vehicle vehicleObj = factory.getNewVehicle();
        try
        {
            final Connection connection = dbConnection.getDatabaseConnection();
            final ResultSet vehicleRS = connection.createStatement().executeQuery(query);
            vehicleObj = resultSetToVehicle(vehicleRS);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }
        return vehicleObj;
    }
    private List<Vehicle> selectQueryExecute(String query)
    {
        List<Vehicle> vehicles = new ArrayList<>();
        try
        {
            final Connection connection = dbConnection.getDatabaseConnection();
            final ResultSet vehicleRS = connection.createStatement().executeQuery(query);
            vehicles = resultSetToVehicles(vehicleRS);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }
        return vehicles;
    }
}
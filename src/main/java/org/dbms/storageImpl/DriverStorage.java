package org.dbms.storageImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.dbms.models.Car;
import org.dbms.models.Client;
import org.dbms.models.Driver;
import org.dbms.models.Order;
import org.dbms.searchModels.DriverSearch;
import org.dbms.storageContracts.ICarStorage;
import org.dbms.storageContracts.IDriverStorage;
import org.springframework.stereotype.Component;


@Component
public class DriverStorage implements IDriverStorage {

    private final static String readRecords;
    private final static String readPage;
    private final static String readCount;
    private final static String readFiltered;
    private final static String readFilteredCount;
    private final static String readRecordById;
    private final static String insertRecord;
    private final static String deleteRecord;

    static
    {
        readRecords = "SELECT id, name, licenseYear, carId FROM drivers";
        readCount = "SELECT COUNT(*) FROM drivers";
        readPage = new StringBuilder()
                .append("SELECT id, name, licenseYear, carId FROM drivers")
                .append("\n")
                .append("ORDER BY id")
                .append("\n")
                .append("OFFSET ?")
                .append("\n")
                .append("LIMIT ?").toString();
        readRecordById = new StringBuilder()
                .append("SELECT id, name, licenseYear, carId FROM drivers")
                .append("\n")
                .append("WHERE id = ?").toString();
        readFiltered = new StringBuilder()
                .append("SELECT id, name, licenseYear, carId FROM drivers")
                .append("\n")
                .append("WHERE ").toString();
        readFilteredCount = new StringBuilder()
                .append("SELECT COUNT(*) FROM drivers")
                .append("\n")
                .append("WHERE ").toString();
        insertRecord = "INSERT INTO drivers (name, licenseYear, carId) VALUES(?, ?, ?)";
        deleteRecord = "DELETE FROM drivers WHERE id = ?";
    }

    private final Connection connection;
    private final ICarStorage carStorage;

    public DriverStorage(Connection connection, ICarStorage carStorage) {
        this.connection = connection;
        this.carStorage = carStorage;
    }

    @Override
    public List<Driver> readAll() {
        ArrayList<Driver> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readRecords);

            ResultSet resultSet = preparedStatement.executeQuery();

            list.ensureCapacity(resultSet.getFetchSize());

            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                Date licenseYear = resultSet.getDate(3);
                Long carId = resultSet.getLong(4);

                Car c = carStorage.getElementById(carId);

                list.add(new Driver(id, name, licenseYear, c));
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Driver> readPage(int size, int page) {
        ArrayList<Driver> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readPage);
            preparedStatement.setInt(1, page - 1);
            preparedStatement.setInt(2, size);

            ResultSet resultSet = preparedStatement.executeQuery();

            list.ensureCapacity(resultSet.getFetchSize());

            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                Date licenseYear = resultSet.getDate(3);
                Long carId = resultSet.getLong(4);

                Car c = carStorage.getElementById(carId);

                list.add(new Driver(id, name, licenseYear, c));
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getCount() {
        int res = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readCount);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                res = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int getFilteredCount(DriverSearch filter) {
        if(filter.expirienceTo == null && filter.expirienceFrom == null) return getCount();

        StringBuilder query = new StringBuilder(readFilteredCount);

        boolean isFirst = true;

        if(filter.expirienceFrom != null) {
            if(!isFirst) {
                query.append(" AND ");
            }

            query.append("EXTRACT(YEAR FROM age(NOW(), licenseYear)) >= ?");
            query.append("\n");
            isFirst = false;
        }

        if(filter.expirienceTo != null) {
            if(!isFirst) {
                query.append(" AND ");
            }

            query.append("EXTRACT(YEAR FROM age(NOW(), licenseYear)) <= ?");
            query.append("\n");
        }

        int res = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());

            int index = 1;

            if(filter.expirienceFrom != null) {
                preparedStatement.setInt(index++, filter.expirienceFrom);
            }

            if(filter.expirienceTo != null) {
                preparedStatement.setInt(index, filter.expirienceTo);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                res = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Driver> getFilteredList(DriverSearch filter) {
        if(filter.expirienceTo == null && filter.expirienceFrom == null) return readAll();

        StringBuilder query = new StringBuilder(readFiltered);

        boolean isFirst = true;

        if(filter.expirienceFrom != null) {
            if(!isFirst) {
                query.append(" AND ");
            }

            query.append("EXTRACT(YEAR FROM age(NOW(), licenseYear)) >= ?");
            query.append("\n");
            isFirst = false;
        }

        if(filter.expirienceTo != null) {
            if(!isFirst) {
                query.append(" AND ");
            }

            query.append("EXTRACT(YEAR FROM age(NOW(), licenseYear)) <= ?");
            query.append("\n");
        }

        ArrayList<Driver> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());

            int index = 1;

            if(filter.expirienceFrom != null) {
                preparedStatement.setInt(index++, filter.expirienceFrom);
            }

            if(filter.expirienceTo != null) {
                preparedStatement.setInt(index, filter.expirienceTo);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            list.ensureCapacity(resultSet.getFetchSize());

            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                Date licenseYear = resultSet.getDate(3);
                Long carId = resultSet.getLong(4);

                Car c = carStorage.getElementById(carId);

                list.add(new Driver(id, name, licenseYear, c));
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Driver> getFilteredPage(DriverSearch filter, int size, int page) {
        if(filter.expirienceTo == null && filter.expirienceFrom == null) return readPage(size, page);

        StringBuilder query = new StringBuilder(readFiltered);

        boolean isFirst = true;

        if(filter.expirienceFrom != null) {
            if(!isFirst) {
                query.append(" AND ");
            }

            query.append("EXTRACT(YEAR FROM age(NOW(), licenseYear)) >= ?");
            query.append("\n");
            isFirst = false;
        }

        if(filter.expirienceTo != null) {
            if(!isFirst) {
                query.append(" AND ");
            }

            query.append("EXTRACT(YEAR FROM age(NOW(), licenseYear)) <= ?");
            query.append("\n");
        }

        query.append("ORDER BY id");
        query.append("\n");
        query.append("OFFSET ?");
        query.append("LIMIT ?");


        ArrayList<Driver> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            int index = 1;

            if(filter.expirienceFrom != null) {
                preparedStatement.setInt(index++, filter.expirienceFrom);
            }

            if(filter.expirienceTo != null) {
                preparedStatement.setInt(index++, filter.expirienceTo);
            }

            preparedStatement.setInt(index++, page - 1);
            preparedStatement.setInt(index, size);


            ResultSet resultSet = preparedStatement.executeQuery();

            list.ensureCapacity(resultSet.getFetchSize());

            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                Date licenseYear = resultSet.getDate(3);
                Long carId = resultSet.getLong(4);

                Car c = carStorage.getElementById(carId);

                list.add(new Driver(id, name, licenseYear, c));
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Driver getElementById(Long driverId) {
        Driver res = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readRecordById);
            preparedStatement.setLong(1, driverId);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            Long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            Date licenseYear = resultSet.getDate(3);
            Long carId = resultSet.getLong(4);

            Car c = carStorage.getElementById(carId);

            res = new Driver(id, name, licenseYear, c);

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void insert(Driver driver) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertRecord);

            preparedStatement.setString(1, driver.getName());
            preparedStatement.setDate(2, new java.sql.Date(driver.getLicenseYear().getTime()));
            preparedStatement.setLong(3, driver.getCar().getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long driverId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteRecord);
            preparedStatement.setLong(1, driverId);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package com.fyle.test.dao;

import com.fyle.test.models.Branch;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.db.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class BankDao {
    @Inject
    private Database db;

    private static final Logger logger = LoggerFactory.getLogger(BankDao.class);

    public CompletionStage<Optional<Branch>> getBrancDetailsByIfsc(String ifscCode){
        Connection connection = db.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM BRANCHES where ifsc = ?");
            preparedStatement.setString(1,ifscCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("Query executed");
            Branch branch =  new Branch();
            while (resultSet.next()){
                branch.setBankId(resultSet.getLong("bank_id"));
                branch.setAddress(resultSet.getString("address"));
                branch.setCity(resultSet.getString("city"));
                branch.setDistrict(resultSet.getString("district"));
                branch.setState(resultSet.getString("state"));
                branch.setIfscCode(resultSet.getString("ifsc"));
                branch.setBranch(resultSet.getString("branch"));
            }
            return CompletableFuture.completedFuture(Optional.of(branch));
        } catch (SQLException e) {
            logger.info("Exception while fetching branch details {}", e);
            e.printStackTrace();
            return CompletableFuture.completedFuture(Optional.empty());
        }
    }

    public CompletionStage<List<Branch>> getAllBranches (String city, String bankName){
        Connection connection = db.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM BRANCHES b, banks bk where b.city = ? and bk.name = ? AND b.bank_id = bk.id");
            preparedStatement.setString(1,city);
            preparedStatement.setString(2,bankName);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Branch> branches =  new ArrayList<>();
            while (resultSet.next()){
                Branch branch =  new Branch();
                branch.setBankId(resultSet.getLong("bank_id"));
                branch.setAddress(resultSet.getString("address"));
                branch.setCity(resultSet.getString("city"));
                branch.setDistrict(resultSet.getString("district"));
                branch.setState(resultSet.getString("state"));
                branch.setIfscCode(resultSet.getString("ifsc"));
                branch.setBranch(resultSet.getString("branch"));
                branches.add(branch);
            }
            return CompletableFuture.completedFuture(branches);
        } catch (SQLException e) {
            logger.info("Exception while fetching branch details {}", e);
            e.printStackTrace();
            return CompletableFuture.completedFuture(Collections.emptyList());
        }

    }

}

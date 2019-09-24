package com.fyle.test.service.impl;

import com.fyle.test.dao.BankDao;
import com.fyle.test.models.Branch;
import com.fyle.test.service.BankService;
import com.google.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public class BankServiceImpl implements BankService {

    @Inject
    BankDao bankDao;


    @Override
    public CompletionStage<Optional<Branch>> getBranchDetails(String ifscCode) {
        validateIfscCode(ifscCode);
        return bankDao.getBrancDetailsByIfsc(ifscCode);
    }

    @Override
    public CompletionStage<List<Branch>> getAllBranches(String city, String bankName) {
        return bankDao.getAllBranches(city, bankName);
    }


    private void validateIfscCode(String ifscCode){
        //if not valid then raise an EXCEPTION else return
    }
}

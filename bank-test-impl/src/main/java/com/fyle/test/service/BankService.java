package com.fyle.test.service;

import com.fyle.test.models.Branch;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface BankService {
    CompletionStage<Optional<Branch>> getBranchDetails(String ifscCode);

    CompletionStage<List<Branch>> getAllBranches(String city, String bankName);
}

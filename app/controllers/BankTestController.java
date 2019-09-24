package controllers;

import com.fyle.test.models.Branch;
import com.fyle.test.service.BankService;
import com.google.inject.Inject;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

public class BankTestController extends Controller {

    @Inject
    private BankService bankService;

    public CompletionStage<Result> getBranchDetails(){
        Map<String, String[]> params = request().queryString();
        String ifscCode = params.get("ifscCode")[0];
        return bankService.getBranchDetails(ifscCode).thenApply(branchOpt -> {
            if(branchOpt.isPresent()){
                Branch branch =  branchOpt.get();
                return ok(Json.toJson(branch));
            }
            else return notFound();
        });
    }

    public CompletionStage<Result> getAllBranches() {
        Map<String, String[]> params = request().queryString();
        String city = params.get("city")[0];
        String bankName = params.get("bankName")[0];
        return bankService.getAllBranches(city, bankName)
        .thenApply(branches -> {
            return ok(Json.toJson(branches));
        });
    }
}

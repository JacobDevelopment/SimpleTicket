package io.jacobking.simpleticket.gui.controller.proctor.impl;

import io.jacobking.simpleticket.database.Database;
import io.jacobking.simpleticket.database.service.ServiceType;
import io.jacobking.simpleticket.gui.controller.proctor.ProctorImpl;
import io.jacobking.simpleticket.gui.model.CompanyModel;
import io.jacobking.simpleticket.tables.pojos.Company;

import java.util.List;

public class CompanyProctor extends ProctorImpl<Company, CompanyModel> {
    @Override
    public void fetch() {
        final List<Company> departments = Database.fetchAll(ServiceType.COMPANY);
        if (departments.isEmpty())
            return;

        departments.forEach(company -> {
            if (company.getId() == 0) // SKIP PLACEHOLDER COMPANY.
                return;

            final CompanyModel model = new CompanyModel(company);
            modelList.add(model);
        });
    }

    @Override
    public void create(Company company) {
        final Company returningCompany = insert(company);
        if (returningCompany != null) {
            final CompanyModel model = new CompanyModel(returningCompany);
            modelList.add(model);
        }
    }

    @Override
    public Company insert(Company company) {
        return Database.insertReturning(ServiceType.COMPANY, company);
    }

    @Override
    public void delete(int id) {
        if (getModelList().isEmpty())
            return;

        if (Database.delete(ServiceType.COMPANY, id)) {
            getModelList().removeIf(model -> model.getId() == id);
        }
    }


}

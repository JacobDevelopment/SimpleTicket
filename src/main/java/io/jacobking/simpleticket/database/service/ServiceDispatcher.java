package io.jacobking.simpleticket.database.service;

import io.jacobking.simpleticket.database.connector.JOOQConnector;
import io.jacobking.simpleticket.database.service.impl.*;
import io.jacobking.simpleticket.tables.pojos.Settings;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.TableField;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceDispatcher {

    private final Map<ServiceType, Service<?>> serviceMap = new ConcurrentHashMap<>();

    private final DSLContext context;

    public ServiceDispatcher(JOOQConnector jooqConnector) {
        this.context = jooqConnector.getContext();
    }

    public <T> boolean insert(final ServiceType serviceType, final T value) {
        final Service<T> service = getService(serviceType);
        if (service != null) {
            return service.insert(context, value);
        }
        return false;
    }

    public <T> T insertReturning(final ServiceType serviceType, final T value) {
        final Service<T> service = getService(serviceType);
        if (service != null) {
            return service.insertReturning(context, value);
        }
        return null;
    }

    public <T> T fetch(final ServiceType type, final int id) {
        final Service<T> service = getService(type);
        if (service != null) {
            return service.fetch(context, id);
        }
        return null;
    }

    public Settings retrieve() {
        final Service<?> service = getService(ServiceType.SETTINGS);
        if (service instanceof SettingsService) {
            return ((SettingsService) service).retrieve(context);
        }
        return null;
    }

    public <T> T fetchByCondition(final ServiceType type, final Condition condition) {
        final Service<T> service = getService(type);
        if (service != null) {
            return service.fetchByCondition(context, condition);
        }
        return null;
    }

    public <R extends Record, T> boolean update(final ServiceType type, final TableField<R, T> field, final T value, final Condition condition) {
        final Service<T> service = getService(type);
        if (service != null) {
            return service.update(context, field, value, condition);
        }
        return false;
    }

    public <T> boolean update(final ServiceType type, final T value) {
        final Service<T> service = getService(type);
        if (service != null) {
            return service.update(context, value);
        }
        return false;
    }

    public <T> boolean delete(final ServiceType type, final int id) {
        final Service<T> service = getService(type);
        if (service != null) {
            return service.delete(context, id);
        }
        return false;
    }

    public <T> List<T> fetchAll(final ServiceType type, final Condition condition) {
        final Service<T> service = getService(type);
        if (service != null) {
            return service.fetchAll(context, condition);
        }
        return Collections.emptyList();
    }


    public <T> List<T> fetchAll(final ServiceType type) {
        final Service<T> service = getService(type);
        if (service != null) {
            return service.fetchAll(context);
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    private <T> Service<T> getService(final ServiceType type) {
        return (Service<T>) serviceMap.computeIfAbsent(type, this::createService);
    }

    @SuppressWarnings("unchecked")
    private <T> Service<T> createService(final ServiceType type) {
        return switch (type) {
            case TICKET -> (Service<T>) new TicketService();
            case DEPARTMENT -> (Service<T>) new DepartmentService();
            case EMPLOYEE -> (Service<T>) new EmployeeService();
            case COMPANY -> (Service<T>) new CompanyService();
            case TICKET_COMMENTS -> (Service<T>) new CommentService();
            case SETTINGS -> (Service<T>) new SettingsService();
        };
    }


}

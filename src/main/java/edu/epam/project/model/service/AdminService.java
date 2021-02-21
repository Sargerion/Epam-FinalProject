package edu.epam.project.model.service;

import edu.epam.project.model.entity.User;
import edu.epam.project.exception.ServiceException;

import java.util.List;

public interface AdminService extends BaseService<Integer, User> {

    boolean activateHR(User user) throws ServiceException;

    List<User> findNotActiveHRList(int start, int end) throws ServiceException;

    int countUsers() throws ServiceException;

    int countNotActiveHRs() throws ServiceException;
}
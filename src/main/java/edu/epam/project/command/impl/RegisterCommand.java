package edu.epam.project.command.impl;

import edu.epam.project.command.*;
import edu.epam.project.entity.User;
import edu.epam.project.exception.CommandException;

import edu.epam.project.exception.ExceptionMessage;
import edu.epam.project.exception.MailSendException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.service.UserService;
import edu.epam.project.service.impl.UserServiceImpl;
import edu.epam.project.service.impl.mail.MailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class RegisterCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_SIGN_UP_PARAMETERS = "Empty sign up parameters";
    private static final boolean IS_HR = true;
    private static final boolean NOT_HR = false;

    @Override
    public CommandResult execute(SessionRequestContext requestContext) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        Optional<String> login = requestContext.getRequestParameter(RequestParameter.LOGIN);
        Optional<String> password = requestContext.getRequestParameter(RequestParameter.PASSWORD);
        Optional<String> repeatPassword = requestContext.getRequestParameter(RequestParameter.REPEAT_PASSWORD);
        Optional<String> email = requestContext.getRequestParameter(RequestParameter.EMAIL);
        Optional<String> isHR = requestContext.getRequestParameter(RequestParameter.HR_OPTION_CHECK);
        String getLogin = "";
        String getPassword = "";
        String getRepeatPassword = "";
        String getEmail = "";
        CommandResult commandResult = null;
        if (login.isEmpty() || password.isEmpty() || repeatPassword.isEmpty() || email.isEmpty()) {
            requestContext.setRequestAttribute(RequestAttribute.ERROR_MESSAGE, EMPTY_SIGN_UP_PARAMETERS);
            commandResult = new CommandResult(PathJsp.REGISTER_PAGE, TransitionType.FORWARD);
        } else {
            getLogin = login.get();
            getPassword = password.get();
            getRepeatPassword = repeatPassword.get();
            getEmail = email.get();
        }
        Optional<User> optionalUser = Optional.empty();
        List<String> errorMessages = new ArrayList<>();
        Map<String, String> correctFields = new HashMap<>();
        Map<Optional<User>, Map<List<String>, Map<String, String>>> registerResult;
        try {
            if (!getLogin.isEmpty() && !getPassword.isEmpty() && !getRepeatPassword.isEmpty() && !getEmail.isEmpty()) {
                if (isHR.isPresent()) {
                    registerResult = userService.registerUser(getLogin, getPassword, getRepeatPassword, getEmail, IS_HR);
                } else {
                    registerResult = userService.registerUser(getLogin, getPassword, getRepeatPassword, getEmail, NOT_HR);
                }
                for (Map.Entry<Optional<User>, Map<List<String>, Map<String, String>>> entry : registerResult.entrySet()) {
                    optionalUser = entry.getKey();
                    for (Map.Entry<List<String>, Map<String, String>> listListEntry : entry.getValue().entrySet()) {
                        errorMessages = listListEntry.getKey();
                        correctFields = listListEntry.getValue();
                    }
                }
                if (correctFields.containsKey(RequestAttribute.CORRECT_LOGIN)) {
                    requestContext.setRequestAttribute(RequestAttribute.CORRECT_LOGIN, correctFields.get(RequestAttribute.CORRECT_LOGIN));
                }
                if (correctFields.containsKey(RequestAttribute.CORRECT_PASSWORD)) {
                    requestContext.setRequestAttribute(RequestAttribute.CORRECT_PASSWORD, correctFields.get(RequestAttribute.CORRECT_PASSWORD));
                }
                if (correctFields.containsKey(RequestAttribute.CORRECT_REPEAT_PASSWORD)) {
                    requestContext.setRequestAttribute(RequestAttribute.CORRECT_REPEAT_PASSWORD, correctFields.get(RequestAttribute.CORRECT_REPEAT_PASSWORD));
                }
                if (correctFields.containsKey(RequestAttribute.CORRECT_EMAIL)) {
                    requestContext.setRequestAttribute(RequestAttribute.CORRECT_EMAIL, correctFields.get(RequestAttribute.CORRECT_EMAIL));
                }
                if (correctFields.containsKey(RequestAttribute.HR_CHECK)) {
                    requestContext.setRequestAttribute(RequestAttribute.HR_CHECK, correctFields.get(RequestAttribute.HR_CHECK));
                }
                if (optionalUser.isPresent()) {
                    MailSender mailSender = MailSender.getInstance();
                    if (isHR.isEmpty()) {
                        mailSender.sendActivationFinder(optionalUser.get());
                        requestContext.setRequestAttribute(RequestAttribute.CONFIRM_MESSAGE, FriendlyMessage.CONFIRM_REGISTER_MESSAGE_FINDER);
                    } else {
                        mailSender.sendNotificationToHR(optionalUser.get());
                        requestContext.setRequestAttribute(RequestAttribute.CONFIRM_MESSAGE, FriendlyMessage.REGISTER_MESSAGE_HR);
                    }
                    commandResult = new CommandResult(PathJsp.HOME_PAGE, TransitionType.FORWARD);
                }
                if (!errorMessages.isEmpty()) {
                    for (int i = 0; i < errorMessages.size(); ) {
                        if (errorMessages.contains(ExceptionMessage.REGISTER_FAIL_INPUT)) {
                            requestContext.setRequestAttribute(RequestAttribute.ERROR_REG_FAIL, errorMessages.get(i));
                            i++;
                        }
                        if (errorMessages.contains(ExceptionMessage.LOGIN_ALREADY_EXISTS)) {
                            requestContext.setRequestAttribute(RequestAttribute.ERROR_LOGIN, errorMessages.get(i));
                            i++;
                        }
                        if (errorMessages.contains((ExceptionMessage.REGISTER_DIFFERENT_PASSWORDS))) {
                            requestContext.setRequestAttribute(RequestAttribute.ERROR_DIFFERENT_PASSWORDS, errorMessages.get(i));
                            i++;
                        }
                    }
                    commandResult = new CommandResult(PathJsp.REGISTER_PAGE, TransitionType.FORWARD);
                }
            }
        } catch (ServiceException | MailSendException e) {
            logger.error(e);
            throw new CommandException(e);
        }
        return commandResult;
    }
}
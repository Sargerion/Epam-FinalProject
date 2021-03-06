package edu.epam.project.controller.command.impl.common;

import edu.epam.project.controller.command.*;
import edu.epam.project.model.entity.User;
import edu.epam.project.exception.CommandException;
import edu.epam.project.exception.MailSendException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.service.UserService;
import edu.epam.project.model.service.impl.UserServiceImpl;
import edu.epam.project.model.util.mail.MailSender;
import edu.epam.project.model.util.message.FriendlyMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * The implementation of Command interface for registering by common user types.
 * @author Sargerion.
 */
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
        CommandResult commandResult = null;
        if (login.isEmpty() || password.isEmpty() || repeatPassword.isEmpty() || email.isEmpty()) {
            requestContext.setSessionAttribute(SessionAttribute.ERROR_MESSAGE, EMPTY_SIGN_UP_PARAMETERS);
            commandResult = new CommandResult(PathJsp.REGISTER_PAGE, TransitionType.REDIRECT);
        } else {
            Optional<User> optionalUser = Optional.empty();
            List<String> errorMessages = new ArrayList<>();
            Map<String, String> correctFields = new HashMap<>();
            Map<Optional<User>, Map<List<String>, Map<String, String>>> registerResult;
            try {
                if (isHR.isPresent()) {
                    registerResult = userService.registerUser(login.get(), password.get(), repeatPassword.get(), email.get(), IS_HR);
                } else {
                    registerResult = userService.registerUser(login.get(), password.get(), repeatPassword.get(), email.get(), NOT_HR);
                }
                for (Map.Entry<Optional<User>, Map<List<String>, Map<String, String>>> entry : registerResult.entrySet()) {
                    optionalUser = entry.getKey();
                    for (Map.Entry<List<String>, Map<String, String>> listMapEntry : entry.getValue().entrySet()) {
                        errorMessages = listMapEntry.getKey();
                        correctFields = listMapEntry.getValue();
                    }
                }
                if (!errorMessages.isEmpty()) {
                    requestContext.setSessionAttribute(SessionAttribute.CORRECT_LOGIN, correctFields.get(SessionAttribute.CORRECT_LOGIN));
                    requestContext.setSessionAttribute(SessionAttribute.CORRECT_PASSWORD, correctFields.get(SessionAttribute.CORRECT_PASSWORD));
                    requestContext.setSessionAttribute(SessionAttribute.CORRECT_REPEAT_PASSWORD, correctFields.get(SessionAttribute.CORRECT_REPEAT_PASSWORD));
                    requestContext.setSessionAttribute(SessionAttribute.CORRECT_EMAIL, correctFields.get(SessionAttribute.CORRECT_EMAIL));
                    requestContext.setSessionAttribute(SessionAttribute.HR_CHECK, correctFields.get(SessionAttribute.HR_CHECK));
                    requestContext.setSessionAttribute(SessionAttribute.ERROR_REGISTER_LIST, errorMessages);
                    commandResult = new CommandResult(PathJsp.REGISTER_PAGE, TransitionType.REDIRECT);
                } else {
                    if (optionalUser.isPresent()) {
                        MailSender mailSender = MailSender.getInstance();
                        mailSender.sendActivationUser(optionalUser.get());
                        if (isHR.isEmpty()) {
                            requestContext.setSessionAttribute(SessionAttribute.CONFIRM_MESSAGE, FriendlyMessage.CONFIRM_REGISTER_MESSAGE_FINDER);
                        } else {
                            requestContext.setSessionAttribute(SessionAttribute.CONFIRM_MESSAGE, FriendlyMessage.REGISTER_MESSAGE_HR);
                        }
                        commandResult = new CommandResult(PathJsp.HOME_PAGE, TransitionType.REDIRECT);
                    }
                }
            } catch (ServiceException | MailSendException e) {
                logger.error(e);
                throw new CommandException(e);
            }
        }
        return commandResult;
    }
}
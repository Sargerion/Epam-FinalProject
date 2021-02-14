package edu.epam.project.command;

import edu.epam.project.command.impl.*;
import edu.epam.project.command.impl.admin.ActivateHRCommand;
import edu.epam.project.command.impl.admin.FindUserListCommand;
import edu.epam.project.command.impl.admin.ForwardToUserListCommand;

public enum CommandName {

    LOG_IN(new LogInCommand()),
    LOG_OUT(new LogOutCommand()),
    CHANGE_LANGUAGE(new LanguageCommand()),
    REGISTER(new RegisterCommand()),
    ACTIVATE(new ActivateCommand()),
    ACTIVATE_HR(new ActivateHRCommand()),
    FORWARD_TO_USER_LIST(new ForwardToUserListCommand()),
    USER_LIST(new FindUserListCommand());

    private Command command;

    CommandName(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}

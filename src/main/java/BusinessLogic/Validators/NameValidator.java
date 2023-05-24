package BusinessLogic.Validators;

import Model.Client;

import java.util.regex.Pattern;

public class NameValidator implements Validators<Client>{
    private static final String Name_PATTERN ="([A-Z]\\w+) ([A-Z]\\w+)";
    @Override
    public void validate(Client client) {
        Pattern p = Pattern.compile(Name_PATTERN);
        if(!p.matcher(client.getName()).matches())
        {
            throw new IllegalArgumentException("Name is not a valid name!");
        }

    }
}

package BusinessLogic.Validators;

import Model.Client;

import java.util.regex.Pattern;

public class AddressValidator implements Validators<Client>{
    private static final String Address_PATTERN ="([A-Z])\\w+ (\\d*)";
    @Override
    public void validate(Client client) {
        Pattern p = Pattern.compile(Address_PATTERN);
        if(!p.matcher(client.getAddress()).matches())
        {
            throw new IllegalArgumentException("Not a valid address!");
        }
    }
}

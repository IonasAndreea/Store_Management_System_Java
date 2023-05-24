package BusinessLogic.Validators;

import Model.Client;
import Model.Product;

import java.util.regex.Pattern;

public class NameVProduct implements Validators<Product>{
    private static final String Name_PATTERN ="([A-Z]\\w+) ([A-Z]\\w+)";
    @Override
    public void validate(Product p) {
        Pattern patt = Pattern.compile(Name_PATTERN);
        if(!patt.matcher(p.getName()).matches())
        {
            throw new IllegalArgumentException("Name is not a valid name!");
        }

    }
}

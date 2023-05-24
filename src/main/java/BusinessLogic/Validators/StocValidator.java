package BusinessLogic.Validators;

import Model.Product;

public class StocValidator implements Validators<Product>{
    private static final int MIN_STOC = 0;
    private static final int MAX_STOC = 1000;
    @Override
    public void validate(Product product) {
        if (product.getStoc() < MIN_STOC || product.getStoc() > MAX_STOC)
        {
            throw new IllegalArgumentException("The stoc limit is not respected!");
        }

    }
}

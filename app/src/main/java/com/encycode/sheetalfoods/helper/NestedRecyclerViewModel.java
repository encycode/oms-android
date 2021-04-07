package com.encycode.sheetalfoods.helper;

import com.encycode.sheetalfoods.entity.Products;

import java.util.List;

public class NestedRecyclerViewModel {

    String productType;
    List<Products> selectedProducts;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public List<Products> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(List<Products> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }
}

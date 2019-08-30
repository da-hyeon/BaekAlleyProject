package com.hdh.baekalleyproject.data.model;

import java.util.ArrayList;
import java.util.Arrays;

public class FilterSelectedItem {


    private int selectedAlleyCount;                //선택된 골목 개수
    private int selectedFoodTypeCount;             //선택된 음식종류 개수
    private int selectedPriceRangeCount;           //선택된 가격대 개수
    private int selectedCategoriesCount;

    private ArrayList<String> selectedAlley;
    private ArrayList<String> selectedFoodType;
    private ArrayList<String> selectedPriceRange;

    public FilterSelectedItem() {
        selectedAlley = new ArrayList<>();
        selectedFoodType = new ArrayList<>();
        selectedPriceRange = new ArrayList<>();
    }

    public FilterSelectedItem(int selectedAlleyCount, int selectedFoodTypeCount, int selectedPriceRangeCount, int selectedCategoriesCount, ArrayList<String> selectedAlley, ArrayList<String> selectedFoodType, ArrayList<String> selectedPriceRange) {
        this.selectedAlleyCount = selectedAlleyCount;
        this.selectedFoodTypeCount = selectedFoodTypeCount;
        this.selectedPriceRangeCount = selectedPriceRangeCount;
        this.selectedCategoriesCount = selectedCategoriesCount;
        this.selectedAlley = selectedAlley;
        this.selectedFoodType = selectedFoodType;
        this.selectedPriceRange = selectedPriceRange;
    }

    public int getSelectedAlleyCount() {
        return selectedAlleyCount;
    }

    public int getSelectedFoodTypeCount() {
        return selectedFoodTypeCount;
    }

    public int getSelectedPriceRangeCount() {
        return selectedPriceRangeCount;
    }

    public int getSelectedCategoriesCount() {
        return selectedCategoriesCount;
    }

    public ArrayList<String> getSelectedAlley() {
        return selectedAlley;
    }

    public ArrayList<String> getSelectedFoodType() {
        return selectedFoodType;
    }

    public ArrayList<String> getSelectedPriceRange() {
        return selectedPriceRange;
    }

    @Override
    public String toString() {
        return "FilterSelectedItem{" +
                "selectedAlleyCount=" + selectedAlleyCount +
                ", selectedFoodTypeCount=" + selectedFoodTypeCount +
                ", selectedPriceRangeCount=" + selectedPriceRangeCount +
                ", selectedAlley=" + Arrays.toString(selectedAlley.toArray()) +
                ", selectedFoodType=" + Arrays.toString(selectedFoodType.toArray()) +
                ", selectedPriceRange=" + Arrays.toString(selectedPriceRange.toArray()) +
                '}';
    }
}

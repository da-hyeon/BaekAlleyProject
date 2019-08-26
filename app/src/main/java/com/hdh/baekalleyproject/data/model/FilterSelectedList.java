package com.hdh.baekalleyproject.data.model;

import java.util.ArrayList;

public class FilterSelectedList {
    private ArrayList<FilterSelectedAlley> filterSelectedAlleyArrayList;
    private ArrayList<FilterSelectedFoodType> filterSelectedFoodTypeArrayList;
    private ArrayList<FilterSelectedPriceRange> filterSelectedPriceRangeArrayList;

    public FilterSelectedList(ArrayList<FilterSelectedAlley> filterSelectedAlleyArrayList, ArrayList<FilterSelectedFoodType> filterSelectedFoodTypeArrayList, ArrayList<FilterSelectedPriceRange> filterSelectedPriceRangeArrayList) {
        this.filterSelectedAlleyArrayList = filterSelectedAlleyArrayList;
        this.filterSelectedFoodTypeArrayList = filterSelectedFoodTypeArrayList;
        this.filterSelectedPriceRangeArrayList = filterSelectedPriceRangeArrayList;
    }

    public ArrayList<FilterSelectedAlley> getFilterSelectedAlleyArrayList() {
        return filterSelectedAlleyArrayList;
    }

    public ArrayList<FilterSelectedFoodType> getFilterSelectedFoodTypeArrayList() {
        return filterSelectedFoodTypeArrayList;
    }

    public ArrayList<FilterSelectedPriceRange> getFilterSelectedPriceRangeArrayList() {
        return filterSelectedPriceRangeArrayList;
    }
}

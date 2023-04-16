package com.example.androdoctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataItems {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableDetailList = new HashMap<String, List<String>>();

        // As we are populating List of fruits, vegetables and nuts, using them here
        // We can modify them as per our choice.
        // And also choice of fruits/vegetables/nuts can be changed
        List<String> fruits = new ArrayList<String>();
        fruits.add("This test shows your average blood sugar level for the past three months. The test measures the percentage of blood sugar attached to the oxygen-carrying protein in red blood cells called hemoglobin. The higher your blood sugar levels, the more hemoglobin you'll have with sugar attached.");
        fruits.add("1. An A1C level below 5.7% is considered normal");
        fruits.add("2. An A1C level between 5.7% and 6.4% is considered prediabetes");
        fruits.add("3. An A1C level of 6.5% or higher on two separate tests indicates type 2 diabetes");
        fruits.add("Certain conditions can make the A1C test inaccurate — such as if you're pregnant or have an uncommon form of hemoglobin.");

        List<String> vegetables = new ArrayList<String>();
        vegetables.add("A blood sample is taken after you fast for at least eight hours or overnight.");
        vegetables.add("1. A fasting blood sugar level below 100 milligrams per deciliter (mg/dL) — 5.6 millimoles per liter (mmol/L) — is considered normal.");
        vegetables.add("2. A fasting blood sugar level from 100 to 125 mg/dL (5.6 to 7.0 mmol/L) is considered prediabetes. This result is sometimes called impaired fasting glucose");
        vegetables.add("3. A fasting blood sugar level of 126 mg/dL (7.0 mmol/L) or higher indicates type 2 diabetes.");

        List<String> nuts = new ArrayList<String>();
        nuts.add("This test is usually used to diagnose diabetes only during pregnancy. A blood sample is taken after you fast for at least eight hours or overnight. Then you'll drink a sugary solution, and your blood sugar level will be measured again after two hours.");
        nuts.add("1. A blood sugar level less than 140 mg/dL (7.8 mmol/L) is considered normal");
        nuts.add("2. A blood sugar level from 140 to 199 mg/dL (7.8 to 11.0 mmol/L) is considered prediabetes. This is sometimes referred to as impaired glucose tolerance.");
        nuts.add("3. A blood sugar level of 200 mg/dL (11.1 mmol/L) or higher indicates type 2 diabetes.");


        // Fruits are grouped under Fruits Items. Similarly the rest two are under
        // Vegetable Items and Nuts Items respecitively.
        // i.e. expandableDetailList object is used to map the group header strings to
        // their respective children using an ArrayList of Strings.
        expandableDetailList.put("Glycated hemoglobin (A1C) test", fruits);
        expandableDetailList.put("Fasting blood sugar test", vegetables);
        expandableDetailList.put("Oral glucose tolerance test", nuts);
        return expandableDetailList;
    }
}
package com.olehprukhnytskyi.macrotrackergoalservice.service;

import com.olehprukhnytskyi.macrotrackergoalservice.dto.GoalResponseDto;
import com.olehprukhnytskyi.macrotrackergoalservice.dto.UserDetailsRequestDto;
import com.olehprukhnytskyi.util.ActivityLevel;
import com.olehprukhnytskyi.util.BodyType;
import com.olehprukhnytskyi.util.Gender;
import com.olehprukhnytskyi.util.Goal;

public class CalorieCalculatorService {
    private static final int CALORIES_PER_GRAM_CARBS = 4;
    private static final int CALORIES_PER_GRAM_FAT = 9;
    private static final int CALORIES_PER_GRAM_PROTEIN = 4;
    private static final int MIN_CALORIES_FEMALE = 1200;
    private static final int MIN_CALORIES_MALE = 1500;

    public static GoalResponseDto calculateGoal(UserDetailsRequestDto data) {
        double bodyFatPercentage = estimateBodyFat(data.getGender(),
                data.getBodyType(), data.getAge());
        double leanBodyMass = data.getWeight() * (1 - bodyFatPercentage);

        double bmr = 370 + (21.6 * leanBodyMass);

        double tdee = bmr * getActivityMultiplier(data.getActivityLevel());
        int targetCalories = adjustCaloriesForGoal(tdee, data.getGoal());

        targetCalories = applySafetyFloor(targetCalories, data.getGender());

        double proteinMultiplier = getProteinMultiplier(data.getBodyType(), data.getGoal());
        int protein = (int) (leanBodyMass * proteinMultiplier);

        double fatRatio = (data.getBodyType() == BodyType.HIGH_BODY_FAT) ? 0.25 : 0.30;
        int fat = (int) ((targetCalories * fatRatio) / CALORIES_PER_GRAM_FAT);

        int caloriesFromProteinAndFat = (protein * CALORIES_PER_GRAM_PROTEIN)
                + (fat * CALORIES_PER_GRAM_FAT);
        int remainingCalories = targetCalories - caloriesFromProteinAndFat;
        if (remainingCalories < 0) {
            return new GoalResponseDto(caloriesFromProteinAndFat, protein, fat, 0);
        }
        int carbs = remainingCalories / CALORIES_PER_GRAM_CARBS;
        return new GoalResponseDto(targetCalories, protein, fat, carbs);
    }

    private static int applySafetyFloor(int targetCalories, Gender gender) {
        int min = (gender == Gender.MALE) ? MIN_CALORIES_MALE : MIN_CALORIES_FEMALE;
        return Math.max(targetCalories, min);
    }

    private static double getProteinMultiplier(BodyType bodyType, Goal goal) {
        if (bodyType == BodyType.HIGH_BODY_FAT) {
            return (goal == Goal.MAINTAIN) ? 1.4 : 1.6;
        } else if (bodyType == BodyType.LEAN) {
            return (goal == Goal.MAINTAIN) ? 2.0 : 2.4;
        } else {
            return (goal == Goal.MAINTAIN) ? 1.8 : 2.0;
        }
    }

    private static double estimateBodyFat(Gender gender, BodyType bodyType, int age) {
        double baseFat = 0.0;
        if (gender == Gender.MALE) {
            if (bodyType == BodyType.LEAN) {
                baseFat = 0.10;
            } else if (bodyType == BodyType.NORMAL) {
                baseFat = 0.15;
            } else {
                baseFat = 0.25;
            }
        } else {
            if (bodyType == BodyType.LEAN) {
                baseFat = 0.18;
            } else if (bodyType == BodyType.NORMAL) {
                baseFat = 0.25;
            } else {
                baseFat = 0.35;
            }
        }
        if (age > 30) {
            baseFat += (age - 30) * 0.001;
        }
        return baseFat;
    }

    private static double getActivityMultiplier(ActivityLevel level) {
        if (level == ActivityLevel.SEDENTARY) {
            return 1.2;
        } else if (level == ActivityLevel.LIGHTLY_ACTIVE) {
            return 1.375;
        } else if (level == ActivityLevel.MODERATELY_ACTIVE) {
            return 1.550;
        } else if (level == ActivityLevel.VERY_ACTIVE) {
            return 1.725;
        } else {
            return 1.9;
        }
    }

    private static int adjustCaloriesForGoal(double tdee, Goal goal) {
        if (goal == Goal.LOSE) {
            return (int) (tdee * 0.80);
        } else if (goal == Goal.MAINTAIN) {
            return (int) tdee;
        } else {
            return (int) (tdee + 300);
        }
    }
}

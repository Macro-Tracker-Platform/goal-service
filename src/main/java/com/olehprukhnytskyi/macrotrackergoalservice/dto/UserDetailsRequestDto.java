package com.olehprukhnytskyi.macrotrackergoalservice.dto;

import com.olehprukhnytskyi.util.ActivityLevel;
import com.olehprukhnytskyi.util.BodyType;
import com.olehprukhnytskyi.util.Gender;
import com.olehprukhnytskyi.util.Goal;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(description = "User profile details for goal calculation")
public class UserDetailsRequestDto {
    @Schema(
            description = "User age in years",
            example = "28",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minimum = "1"
    )
    @NotNull
    @Positive
    private int age;

    @Schema(
            description = "User weight in kilograms",
            example = "75",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minimum = "1"
    )
    @NotNull
    @Positive
    private int weight;

    @Schema(
            description = "User height in centimeters",
            example = "180",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minimum = "1"
    )
    @NotNull
    @Positive
    private int height;

    @Schema(description = "User fitness goal", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Goal goal;

    @Schema(description = "User gender", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Gender gender;

    @Schema(description = "User activity level", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private ActivityLevel activityLevel;

    @Schema(description = "User body type", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private BodyType bodyType;
}

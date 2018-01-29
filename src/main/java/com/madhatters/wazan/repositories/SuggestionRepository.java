package com.madhatters.wazan.repositories;

import com.madhatters.wazan.model.Suggestion;

public interface SuggestionRepository extends ResourceRepository<Suggestion,String>{
    Suggestion findFirstByMinCaloriesGreaterThanEqual(double kcal);
}

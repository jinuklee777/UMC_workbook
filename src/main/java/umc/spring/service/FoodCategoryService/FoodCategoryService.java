package umc.spring.service.FoodCategoryService;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.repository.FoodCategoryRepository;

@Service
@RequiredArgsConstructor
public class FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;

    public boolean areAllCategoriesExist(List<Long> categoryIds) {
        return categoryIds.stream().allMatch(foodCategoryRepository::existsById);
    }

}
